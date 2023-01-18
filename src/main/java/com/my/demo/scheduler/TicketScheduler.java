package com.my.demo.scheduler;

import com.my.demo.dao.TicketDao;
import com.my.demo.dto.PaymentStatusDto;
import com.my.demo.entity.PaymentStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Log4j2
@Component
@RequiredArgsConstructor
public class TicketScheduler {

    private final RestTemplate restTemplate;
    private final TicketDao ticketDao;
    private static final String FIND_FAILED_AND_NEW_STATUSES_GET_URL = "http://localhost:8080/payments/statuses";
    private static final String UPDATE_STATUSES_PUT_URL = "http://localhost:8080/payments";

    @Scheduled(fixedRate = 10000)
    @Transactional
    public void scheduledChangeTicketPaymentStatus() {
        try {
            ResponseEntity<List<PaymentStatusDto>> responseEntity = restTemplate.exchange(FIND_FAILED_AND_NEW_STATUSES_GET_URL, HttpMethod.GET, null, new ParameterizedTypeReference<>() {
            });
            List<PaymentStatusDto> paymentDtoList = responseEntity.getBody();

            if (paymentDtoList != null && !paymentDtoList.isEmpty()) {
                List<PaymentStatusDto> failedPayments = paymentDtoList.stream().filter(payment -> payment.getStatus().equals(PaymentStatus.FAILED)).collect(Collectors.toList());
                returnFailedTicked(failedPayments);
                updateStatuses(paymentDtoList);
                restTemplate.put(UPDATE_STATUSES_PUT_URL, paymentDtoList);
            }
            log.info("scheduler - no NEW or FAILED tickets");
        } catch (Exception e) {
            log.error(e.getMessage());
        }

    }

    private void returnFailedTicked(List<PaymentStatusDto> failedPayments) {
        for (PaymentStatusDto paymentDto : failedPayments) {
            ticketDao.updateAvailableSeatsWithReturnedTicketForUpdate(paymentDto.getId());
            log.info("scheduler - returned one ticket with payment id = " + paymentDto.getId() + " cause: payment status is " + paymentDto.getStatus());
        }
    }

    private void updateStatuses(List<PaymentStatusDto> paymentDtoList) {
        for (PaymentStatusDto paymentDto : paymentDtoList) {
            PaymentStatus badStatus = paymentDto.getStatus();
            paymentDto.setStatus(PaymentStatus.DONE);
            log.info("scheduler - payment status changed to DONE (was " + badStatus + ") in payment with id = " + paymentDto.getId());
        }
    }
}
