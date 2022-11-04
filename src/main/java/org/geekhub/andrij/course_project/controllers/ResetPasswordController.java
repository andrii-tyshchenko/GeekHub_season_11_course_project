package org.geekhub.andrij.course_project.controllers;

import org.geekhub.andrij.course_project.entities.User;
import org.geekhub.andrij.course_project.repositories.UserRepository;
import org.geekhub.andrij.course_project.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Random;

@Controller
@RequestMapping("/reset_password")
public class ResetPasswordController {
    private final EmailService emailService;
    private final UserRepository userRepository;

    @Autowired
    public ResetPasswordController(EmailService emailService, UserRepository userRepository) {
        this.emailService = emailService;
        this.userRepository = userRepository;
    }

    @GetMapping
    public ModelAndView getResetPasswordPage() {
        return new ModelAndView("common/reset_password");
    }

    @PostMapping
    public ModelAndView resetPassword(@RequestParam String email, RedirectAttributes redirectAttributes) {
        User user = userRepository.findByEmail(email);

        if (user != null) {
            String newPassword = generateRandomPassword();

            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String hashedNewPassword = passwordEncoder.encode(newPassword);

            userRepository.updateUserPassword(user.getId(), hashedNewPassword);

            String subject = "Successful password reset";
            String message =
                    "Password changed.\nYour new password:\n\n" + newPassword + "\n\n" +
                    "Please change it in personal cabinet to your own";

            Thread thread = new Thread(() -> emailService.sendSimpleMessage(email, subject, message));
            thread.start();

            String successfulResetMessage = "We've sent you new password to email";

            redirectAttributes.addFlashAttribute("successful_reset_message", successfulResetMessage);

            return new ModelAndView("redirect:/signin");
        } else {
            String error = "User with this email doesn't exist or email is incorrect";

            redirectAttributes.addFlashAttribute("error", error);

            return new ModelAndView("redirect:/reset_password");
        }
    }

    private String generateRandomPassword() {
        String password = "";

        int length = 8;

        Random random = new Random();

        for (int i = 0; i < length; i ++) {
            int digit0Character1 = random.nextInt(2);

            if (digit0Character1 == 0) {
                password += random.nextInt(10);
            } else {
                int upper0Lower1 = random.nextInt(2);
                int offset = (upper0Lower1 == 0) ? 65 : 97;

                int alphabetLength = 26;

                password += (char) (random.nextInt(alphabetLength) + offset);
            }
        }

        return password;
    }
}