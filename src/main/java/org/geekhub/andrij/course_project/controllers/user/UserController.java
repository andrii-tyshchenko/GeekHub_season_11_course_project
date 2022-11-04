package org.geekhub.andrij.course_project.controllers.user;

import org.geekhub.andrij.course_project.entities.*;
import org.geekhub.andrij.course_project.repositories.AccrualRepository;
import org.geekhub.andrij.course_project.repositories.BalanceRepository;
import org.geekhub.andrij.course_project.repositories.PaymentRepository;
import org.geekhub.andrij.course_project.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    private final AccrualRepository accrualRepository;
    private final BalanceRepository balanceRepository;
    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;

    @Autowired
    public UserController(AccrualRepository accrualRepository,
                          BalanceRepository balanceRepository,
                          PaymentRepository paymentRepository,
                          UserRepository userRepository) {
        this.accrualRepository = accrualRepository;
        this.balanceRepository = balanceRepository;
        this.paymentRepository = paymentRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/cabinet")
    public ModelAndView getCabinetPage() {
        int userId = getUserId();

        List<Accrual> lastAccrualDetails = accrualRepository.getLastAccrualForUserId(userId);

        Double lastAccruedTotalAmount = accrualRepository.getLastAccrualAmountForUserId(userId);

        Double userDebt = balanceRepository.getUserDebtByUserId(userId);

        return new ModelAndView("user/cabinet")
                .addObject("last_accrual_details", lastAccrualDetails)
                .addObject("last_accrued_total_amount", lastAccruedTotalAmount)
                .addObject("user_debt", userDebt);
    }

    @PostMapping("/cabinet")
    public ModelAndView makePayment(@RequestParam Double payment, RedirectAttributes redirectAttributes) {
        if (payment <= 0) {
            String error = "Amount of payment should be grater than 0";

            redirectAttributes.addFlashAttribute("error", error);
        } else {
            int userId = getUserId();

            paymentRepository.makePaymentForUser(userId, payment);

            String messageForSuccessfulPayment = "Successful payment";

            redirectAttributes.addFlashAttribute("successful_payment_message", messageForSuccessfulPayment);
        }

        return new ModelAndView("redirect:/user/cabinet");
    }

    @GetMapping("/accruals_history")
    public ModelAndView getAccrualsHistory() {
        int userId = getUserId();

        List<Accrual> accruals = accrualRepository.getAllAccrualsForUserId(userId);

        return new ModelAndView("user/accruals_history")
                .addObject("accruals", accruals);
    }

    @GetMapping("/payment_history")
    public ModelAndView getPaymentHistory() {
        int userId = getUserId();

        List<Payment> payments = paymentRepository.getAllForUserId(userId);

        return new ModelAndView("user/payment_history")
                .addObject("payments", payments);
    }

    @GetMapping("/settings")
    public ModelAndView getSettingsPage() {
        int userId = getUserId();

        UserInfo userInfo = userRepository.getUserInfoById(userId);

        return new ModelAndView("common/settings")
                .addObject("user_info", userInfo);
    }

    @PostMapping("/settings")
    public ModelAndView changeSettings(@Valid User user,
                                       BindingResult bindingResult,
                                       UserInfo userInfo,
                                       RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errors", bindingResult.getFieldErrors());
        } else {
            int userId = getUserId();

            userRepository.updateUserEmailAndName(userId, user.getEmail(), userInfo);

            String messageForSuccessfulChange = "Settings successfully changed";

            redirectAttributes.addFlashAttribute("successful_change_message", messageForSuccessfulChange);
        }

        return new ModelAndView("redirect:/user/settings");
    }

    @PostMapping("/password")
    public ModelAndView changePassword(@Valid User user, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errors", bindingResult.getFieldErrors());
        } else {
            int userId = getUserId();

            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String hashedPassword = passwordEncoder.encode(user.getPassword());

            userRepository.updateUserPassword(userId, hashedPassword);

            String messageForSuccessfulChange = "Password successfully changed";

            redirectAttributes.addFlashAttribute("successful_change_message", messageForSuccessfulChange);
        }

        return new ModelAndView("redirect:/user/settings");
    }

    @ModelAttribute("user_sidebar_info")
    public UserSidebarInfo getUserSidebarInfo() {
        int userId = getUserId();

        return userRepository.getUserSidebarInfo(userId);
    }

    private int getUserId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return ((CurrentUser) principal).getId();
    }
}