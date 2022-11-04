package org.geekhub.andrij.course_project.controllers.admin;

import com.lowagie.text.DocumentException;
import org.geekhub.andrij.course_project.entities.*;
import org.geekhub.andrij.course_project.repositories.*;
import org.geekhub.andrij.course_project.services.exportToFiles.AmountByBuildingToDocxService;
import org.geekhub.andrij.course_project.services.exportToFiles.AmountBySectionToXlsxService;
import org.geekhub.andrij.course_project.services.exportToFiles.DebtorInfoToPdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Stream;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final AccrualRepository accrualRepository;
    private final ApartmentRepository apartmentRepository;
    private final BalanceRepository balanceRepository;
    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;

    private final DebtorInfoToPdfService debtorInfoToPdfService;
    private final AmountByBuildingToDocxService amountByBuildingToDocxService;
    private final AmountBySectionToXlsxService amountBySectionToXlsxService;

    @Autowired
    public AdminController(AccrualRepository accrualRepository,
                           ApartmentRepository apartmentRepository,
                           BalanceRepository balanceRepository,
                           PaymentRepository paymentRepository,
                           UserRepository userRepository,
                           DebtorInfoToPdfService debtorInfoPDFExporterService,
                           AmountByBuildingToDocxService amountByBuildingToDocxService,
                           AmountBySectionToXlsxService amountBySectionToXlsxService) {
        this.accrualRepository = accrualRepository;
        this.apartmentRepository = apartmentRepository;
        this.balanceRepository = balanceRepository;
        this.paymentRepository = paymentRepository;
        this.userRepository = userRepository;
        this.debtorInfoToPdfService = debtorInfoPDFExporterService;
        this.amountByBuildingToDocxService = amountByBuildingToDocxService;
        this.amountBySectionToXlsxService = amountBySectionToXlsxService;
    }

    @GetMapping("/cabinet")
    public ModelAndView getCabinetPage() {
        Integer apartmentsAmount = apartmentRepository.getApartmentsCount();

        Integer customersAmount = userRepository.getCustomersCount();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        String lastAccrualDate = accrualRepository.getLastAccrualDate().format(formatter);

        Double balance = balanceRepository.getOSBBBalance();

        String defaultServiceNameValue = "Monthly payment for the building maintenance";

        Double lastTariffValue = accrualRepository.getLastTariffValue();

        AutoAccrualStatus currentAutoAccrualStatus = accrualRepository.getLastAutoAccrualStatus();

        final int quantity = 5;
        List<DebtorInfo> debtorsInfo = balanceRepository.getTopDebtors(quantity);

        return new ModelAndView("admin/cabinet")
                .addObject("apartments_amount", apartmentsAmount)
                .addObject("customers_amount", customersAmount)
                .addObject("last_accrual_date", lastAccrualDate)
                .addObject("balance", balance)
                .addObject("default_service_name_value", defaultServiceNameValue)
                .addObject("last_tariff_value", lastTariffValue)
                .addObject("current_autoaccrual_status", currentAutoAccrualStatus)
                .addObject("debtors_info", debtorsInfo);
    }

    @PostMapping("/cabinet")
    public ModelAndView addAccrual(@Valid Accrual accrual,
                                   BindingResult bindingResult,
                                   RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errors", bindingResult.getFieldErrors());
        } else {
            accrualRepository.addAccrualsAndAccrualsInfo(accrual.getServiceName(), accrual.getTariff());

            String messageForSuccessfulAccrual = "Successful accrual!";

            redirectAttributes.addFlashAttribute("successful_accrual_message", messageForSuccessfulAccrual);
        }

        return new ModelAndView("redirect:/admin/cabinet");
    }

    @PostMapping("/autoaccrual")
    public ModelAndView updateAutoAccrualStatus(@Valid AutoAccrualStatus autoAccrualStatus,
                                                BindingResult bindingResult,
                                                RedirectAttributes redirectAttributes) {
        if (autoAccrualStatus.getEnabled()) {
            if (bindingResult.hasErrors()) {
                redirectAttributes.addFlashAttribute("errors", bindingResult.getFieldErrors());
            } else {
                accrualRepository.addAutoAccrualStatus(autoAccrualStatus);

                String messageForAutoAccrualStatusUpdate = "Autoaccruals enabled";

                redirectAttributes.addFlashAttribute("autoaccrual_status_update_message", messageForAutoAccrualStatusUpdate);
            }
        } else {
            accrualRepository.addAutoAccrualStatus(autoAccrualStatus);

            String messageForAutoAccrualStatusUpdate = "Autoaccruals disabled";

            redirectAttributes.addFlashAttribute("autoaccrual_status_update_message", messageForAutoAccrualStatusUpdate);
        }

        return new ModelAndView("redirect:/admin/cabinet");
    }

    @GetMapping("/cabinet/debtors")
    public ModelAndView getDebtors(Integer quantity) {
        List<DebtorInfo> debtorsInfo = balanceRepository.getTopDebtors(quantity);

        return new ModelAndView("admin/debtors")
                .addObject("debtors_info", debtorsInfo);
    }

    @GetMapping("/cabinet/debtors/pdf")
    public void exportDebtorsToPDF(HttpServletResponse response, Integer quantity) throws DocumentException, IOException {
        List<DebtorInfo> debtors = balanceRepository.getTopDebtors(quantity);

        debtorInfoToPdfService.export(response, debtors);
    }

    @GetMapping("/apartments")
    public ModelAndView getApartments() {
        List<Apartment> apartments = apartmentRepository.getAll();

        return new ModelAndView("admin/apartments")
                .addObject("apartments", apartments);
    }

    @PostMapping("/apartments")
    public ModelAndView changeSquare(@Valid Apartment apartment,
                                     BindingResult bindingResult,
                                     RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errors", bindingResult.getFieldErrors());
        } else {
            apartmentRepository.updateSquare(apartment.getId(), apartment.getSquare());

            String messageForSuccessfulUpdate = "Square updated";

            redirectAttributes.addFlashAttribute("successful_update_message", messageForSuccessfulUpdate);
        }

        return new ModelAndView("redirect:/admin/apartments");
    }

    @GetMapping("/customers")
    public ModelAndView getCustomers() {
        List<UserInfo> customers = userRepository.getAll();

        return new ModelAndView("admin/customers")
                .addObject("customers", customers);
    }

    @GetMapping("/customers/{id}")
    public ModelAndView getCustomerById(@PathVariable Integer id) {
        UserInfo userInfo = userRepository.getUserInfoById(id);

        List<Accrual> accruals = accrualRepository.getAllAccrualsForUserId(id);

        List<Payment> payments = paymentRepository.getAllForUserId(id);

        return new ModelAndView("admin/customer_info")
                .addObject("user_info", userInfo)
                .addObject("accruals", accruals)
                .addObject("payments", payments);
    }

    @GetMapping("/accruals_history")
    public ModelAndView getAccrualsHistory() {
        List<Accrual> accruals = accrualRepository.getAllAccruals();

        LocalDate firstAccrualDate = accrualRepository.getFirstAccrualDate().toLocalDate();

        Map<String, String> htmlSelectValues = new LinkedHashMap<>();

        Double monthlyAccrualOfBuilding = null;

        List<AmountBySection> monthlyAccrualsBySection = Collections.emptyList();

        if (firstAccrualDate != null) {
            YearMonth currentYearMonth = YearMonth.now();
            YearMonth firstAccrualYearMonth = YearMonth.from(firstAccrualDate);

            monthlyAccrualOfBuilding = accrualRepository.getMonthlyAccrualOfBuilding(currentYearMonth.atDay(1));

            monthlyAccrualsBySection = accrualRepository.getMonthlyAccrualsBySection(currentYearMonth.atDay(1));

            DateTimeFormatter keyFormatter = DateTimeFormatter.ofPattern("yyyy-MM");
            DateTimeFormatter valueFormatter = DateTimeFormatter.ofPattern("MMMM yyyy", Locale.ENGLISH);

            while (currentYearMonth.isAfter(firstAccrualYearMonth) || currentYearMonth.equals(firstAccrualYearMonth)) {
                htmlSelectValues.put(currentYearMonth.format(keyFormatter), currentYearMonth.format(valueFormatter));

                currentYearMonth = currentYearMonth.minusMonths(1);
            }
        }

        return new ModelAndView("admin/accruals_history")
                .addObject("accruals", accruals)
                .addObject("selectValues", htmlSelectValues)
                .addObject("building_total", monthlyAccrualOfBuilding)
                .addObject("sections_total", monthlyAccrualsBySection);
    }

    @GetMapping("/accruals_history/by_building")
    public ModelAndView getMonthlyAccrualOfBuilding(String yearMonth) {
        YearMonth neededYearMonth = YearMonth.parse(yearMonth);

        Double monthlyAccrualOfBuilding = accrualRepository.getMonthlyAccrualOfBuilding(neededYearMonth.atDay(1));

        return new ModelAndView("admin/accrual_of_building")
                .addObject("building_total", monthlyAccrualOfBuilding);
    }

    @GetMapping("/accruals_history/by_building/docx")
    public void exportAccrualsByBuildingToDocx(HttpServletResponse response, String yearMonth) throws IOException {
        YearMonth neededYearMonth = YearMonth.parse(yearMonth);

        String title = "Accruals by building " + yearMonth;

        Double monthlyAccrualOfBuilding = accrualRepository.getMonthlyAccrualOfBuilding(neededYearMonth.atDay(1));

        amountByBuildingToDocxService.export(response, title, monthlyAccrualOfBuilding);
    }

    @GetMapping("/accruals_history/by_section")
    public ModelAndView getMonthlyAccrualsBySections(String yearMonth) {
        YearMonth neededYearMonth = YearMonth.parse(yearMonth);

        List<AmountBySection> monthlyAccrualsBySection = accrualRepository.getMonthlyAccrualsBySection(neededYearMonth.atDay(1));

        return new ModelAndView("admin/accruals_by_section")
                .addObject("sections_total", monthlyAccrualsBySection);
    }

    @GetMapping("/accruals_history/by_section/xlsx")
    public void exportAccrualsBySectionToXlsx(HttpServletResponse response, String yearMonth) throws IOException {
        YearMonth neededYearMonth = YearMonth.parse(yearMonth);

        String title = "accruals_" + yearMonth;

        List<AmountBySection> monthlyAccrualsBySection = accrualRepository.getMonthlyAccrualsBySection(neededYearMonth.atDay(1));

        amountBySectionToXlsxService.export(response, title, monthlyAccrualsBySection);
    }

    @GetMapping("/payment_history")
    public ModelAndView getPaymentHistory() {
        List<Payment> payments = paymentRepository.getAll();

        LocalDate firstPaymentDate = paymentRepository.getFirstPaymentDate().toLocalDate();

        Map<String, String> htmlSelectValues = new LinkedHashMap<>();

        Double monthlyPaymentOfBuilding = null;

        List<AmountBySection> monthlyPaymentsBySection = Collections.emptyList();

        if (firstPaymentDate != null) {
            YearMonth currentYearMonth = YearMonth.now();
            YearMonth firstPaymentYearMonth = YearMonth.from(firstPaymentDate);

            monthlyPaymentOfBuilding = paymentRepository.getMonthlyPaymentOfBuilding(currentYearMonth.atDay(1));

            monthlyPaymentsBySection = paymentRepository.getMonthlyPaymentsBySection(currentYearMonth.atDay(1));

            DateTimeFormatter keyFormatter = DateTimeFormatter.ofPattern("yyyy-MM");
            DateTimeFormatter valueFormatter = DateTimeFormatter.ofPattern("MMMM yyyy", Locale.ENGLISH);

            while (currentYearMonth.isAfter(firstPaymentYearMonth) || currentYearMonth.equals(firstPaymentYearMonth)) {
                htmlSelectValues.put(currentYearMonth.format(keyFormatter), currentYearMonth.format(valueFormatter));

                currentYearMonth = currentYearMonth.minusMonths(1);
            }
        }

        return new ModelAndView("admin/payment_history")
                .addObject("payments", payments)
                .addObject("selectValues", htmlSelectValues)
                .addObject("building_total", monthlyPaymentOfBuilding)
                .addObject("sections_total", monthlyPaymentsBySection);
    }

    @GetMapping("/payment_history/by_building")
    public ModelAndView getMonthlyPaymentOfBuilding(String yearMonth) {
        YearMonth neededYearMonth = YearMonth.parse(yearMonth);

        Double monthlyPaymentOfBuilding = paymentRepository.getMonthlyPaymentOfBuilding(neededYearMonth.atDay(1));

        return new ModelAndView("admin/payment_of_building")
                .addObject("building_total", monthlyPaymentOfBuilding);
    }

    @GetMapping("/payment_history/by_building/docx")
    public void exportPaymentsByBuildingToDocx(HttpServletResponse response, String yearMonth) throws IOException {
        YearMonth neededYearMonth = YearMonth.parse(yearMonth);

        String title = "Payments by building " + yearMonth;

        Double monthlyPaymentOfBuilding = paymentRepository.getMonthlyPaymentOfBuilding(neededYearMonth.atDay(1));

        amountByBuildingToDocxService.export(response, title, monthlyPaymentOfBuilding);
    }

    @GetMapping("/payment_history/by_section")
    public ModelAndView getMonthlyPaymentsBySections(String yearMonth) {
        YearMonth neededYearMonth = YearMonth.parse(yearMonth);

        List<AmountBySection> monthlyPaymentsBySection = paymentRepository.getMonthlyPaymentsBySection(neededYearMonth.atDay(1));

        return new ModelAndView("admin/payments_by_section")
                .addObject("sections_total", monthlyPaymentsBySection);
    }

    @GetMapping("/payment_history/by_section/xlsx")
    public void exportPaymentsBySectionToXlsx(HttpServletResponse response, String yearMonth) throws IOException {
        YearMonth neededYearMonth = YearMonth.parse(yearMonth);

        String title = "payments_" + yearMonth;

        List<AmountBySection> monthlyPaymentsBySection = paymentRepository.getMonthlyPaymentsBySection(neededYearMonth.atDay(1));

        amountBySectionToXlsxService.export(response, title, monthlyPaymentsBySection);
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

        return new ModelAndView("redirect:/admin/settings");
    }

    @GetMapping("/settings/customers")
    public ModelAndView getCustomersSettings() {
        List<UserInfo> customers = userRepository.getAll();

        return new ModelAndView("admin/customers_settings")
                .addObject("customers", customers);
    }

    @PostMapping("/settings/customers")
    public ModelAndView changeCustomerSettings(@Valid User user,
                                               BindingResult userBindingResult,
                                               @Valid UserInfo userInfo,
                                               BindingResult userInfoBindingResult,
                                               RedirectAttributes redirectAttributes) {
        if (userBindingResult.hasErrors() || userInfoBindingResult.hasErrors()) {
            List<FieldError> errors = new ArrayList<>();
            Stream.of(userBindingResult.getFieldErrors(), userInfoBindingResult.getFieldErrors())
                    .forEach(errors::addAll);

            redirectAttributes.addFlashAttribute("errors", errors);
        } else {
            userInfo.setUser(user);

            userRepository.updateUserAndUserInfo(userInfo);

            String messageForSuccessfulUpdate = "User info updated";

            redirectAttributes.addFlashAttribute("successful_update_message", messageForSuccessfulUpdate);
        }

        return new ModelAndView("redirect:/admin/settings/customers");
    }

    @PostMapping("/password")
    public ModelAndView changePassword(@Valid User user,
                                       BindingResult bindingResult,
                                       RedirectAttributes redirectAttributes) {
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

        return new ModelAndView("redirect:/admin/settings");
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