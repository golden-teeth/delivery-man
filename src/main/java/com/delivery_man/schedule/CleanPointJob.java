package com.delivery_man.schedule;

import com.delivery_man.constant.ExpiredPolish;
import com.delivery_man.repository.PointRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class CleanPointJob {

        private final PointRepository pointRepository;
        @Scheduled(cron = "0 0 0 * * *")
        public void execute() throws JobExecutionException {
            log.info("===scheduling start===");
            log.info("===point===");
            LocalDateTime cutoffDateTime = LocalDateTime.now().minusMonths(ExpiredPolish.POINT.getMonths());
            pointRepository.deletePointByCreatedAtLessThan(cutoffDateTime);
        }
    }