package org.geekhub.andrij.course_project.services;

import org.geekhub.andrij.course_project.entities.AutoAccrualStatus;
import org.geekhub.andrij.course_project.repositories.AccrualRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@EnableScheduling
public class AutoAccrualsService {
    private final AccrualRepository accrualRepository;

    @Autowired
    public AutoAccrualsService(AccrualRepository accrualRepository) {
        this.accrualRepository = accrualRepository;
    }

    @Scheduled(cron = "0 0 7 1 * ?")
    public void makeMonthlyAutoAccrual() {
        AutoAccrualStatus currentStatus = accrualRepository.getLastAutoAccrualStatus();

        if (currentStatus != null && currentStatus.getEnabled()) {
            accrualRepository.addAccrualsAndAccrualsInfo(currentStatus.getServiceName(), currentStatus.getTariff());
        }
    }
}