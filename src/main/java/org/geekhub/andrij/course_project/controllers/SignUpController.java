package org.geekhub.andrij.course_project.controllers;

import org.geekhub.andrij.course_project.entities.CurrentUser;
import org.geekhub.andrij.course_project.entities.User;
import org.geekhub.andrij.course_project.entities.UserInfo;
import org.geekhub.andrij.course_project.repositories.ApartmentRepository;
import org.geekhub.andrij.course_project.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Map;

@Controller
@RequestMapping("/signup")
public class SignUpController {
    private final ApartmentRepository apartmentRepository;
    private final UserRepository userRepository;

    @Autowired
    public SignUpController(ApartmentRepository apartmentRepository, UserRepository userRepository) {
        this.apartmentRepository = apartmentRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public ModelAndView getRegistrationPage() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            String authority = ((CurrentUser) principal).getAuthority();

            if (authority.equals("ROLE_ADMIN")) {
                return new ModelAndView("redirect:/admin/cabinet");
            } else if (authority.equals("ROLE_USER")) {
                return new ModelAndView("redirect:/user/cabinet");
            } else {
                return new ModelAndView("redirect:/exit");
            }
        } else {
            return new ModelAndView("common/signup");
        }
    }

    @PostMapping
    public ModelAndView registerNewUser(@Valid User user,
                                        BindingResult bindingResult,
                                        UserInfo userInfo,
                                        RedirectAttributes redirectAttributes) {
        boolean userExists = userRepository.findByEmail(user.getEmail()) != null;

        if (bindingResult.hasErrors() || userExists) {
            if (userExists) {
                FieldError emailExists = new FieldError("user", "email", "User with this email address ALREADY EXISTS!");

                bindingResult.addError(emailExists);
            }

            redirectAttributes.addFlashAttribute("errors", bindingResult.getFieldErrors());

            return new ModelAndView("redirect:/signup");
        } else {
            userInfo.setUser(user);

            userRepository.addUser(userInfo);

            String successfulSignUpMessage = "Registration successfully. You can login now";

            redirectAttributes.addFlashAttribute("successful_signup_message", successfulSignUpMessage);

            return new ModelAndView("redirect:/signin");
        }
    }

    @GetMapping("/check_account_number")
    @ResponseBody
    public Map<Boolean, Object> checkAccountNumber(@RequestParam Integer accountNumber) {
        Map<Boolean, Object> response = null;

        Boolean accountNumberExist = apartmentRepository.isAccountNumberExist(accountNumber);

        if (accountNumberExist) {
            Boolean accountNumberHasUsers = apartmentRepository.isAccountNumberHasUsers(accountNumber);

            if (accountNumberHasUsers) {
                response = Map.of(false, "Some user is already registered with this account number");
            } else {
                Integer apartmentId = apartmentRepository.getApartmentIdByAccountNumber(accountNumber);

                response = Map.of(true, apartmentId);
            }
        } else {
            response = Map.of(false, "Account number is invalid or doesn't exist");
        }

        return response;
    }
}