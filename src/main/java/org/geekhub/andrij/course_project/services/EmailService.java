package org.geekhub.andrij.course_project.services;

import org.geekhub.andrij.course_project.entities.DebtorInfo;
import org.geekhub.andrij.course_project.repositories.BalanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@EnableScheduling
public class EmailService {
    private final JavaMailSender emailSender;

    private final BalanceRepository balanceRepository;

    @Autowired
    public EmailService(JavaMailSender emailSender, BalanceRepository balanceRepository) {
        this.emailSender = emailSender;
        this.balanceRepository = balanceRepository;
    }

    public void sendSimpleMessage(String toAddress, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(toAddress);
        message.setSubject(subject);
        message.setText(text);

        emailSender.send(message);
    }

    @Scheduled(cron = "0 0 7 21 * ?")
    public void sendRemindingForDebtors() throws InterruptedException {
        List<DebtorInfo> debtors = balanceRepository.getTopDebtors(null);

        if (!debtors.isEmpty()) {
            String subject = "Reminding about your debt";

            for (DebtorInfo debtor : debtors) {
                String message =
                        "Dear customer!\n\n" +
                        "You have unpaid debt: " + debtor.getDebt() + " UAH.\n\n" +
                        "Please pay your bills in time";

                sendSimpleMessage(debtor.getEmail(), subject, message);

                Thread.sleep(10000);
            }
        }
    }
}