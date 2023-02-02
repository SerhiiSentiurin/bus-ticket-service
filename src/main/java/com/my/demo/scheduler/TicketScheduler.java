package com.my.demo.scheduler;

import com.my.demo.repository.TicketRepository;
import com.my.demo.dto.PaymentStatusDto;
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
    private final TicketRepository ticketRepository;
    private static final String FIND_FAILED_AND_NEW_STATUSES_GET_URL = "http://localhost:8080/payments/statuses/bad";
    private static final String UPDATE_STATUSES_PUT_URL = "http://localhost:8080/payments/statuses";


    @Scheduled(fixedRate = 10000)
    @Transactional
    public void scheduledChangeTicketPaymentStatus() {
        try {
            ResponseEntity<List<PaymentStatusDto>> responseEntity = restTemplate.exchange(FIND_FAILED_AND_NEW_STATUSES_GET_URL, HttpMethod.GET, null, new ParameterizedTypeReference<>() {});
            List<PaymentStatusDto> listPaymentDto = responseEntity.getBody();

            if (listPaymentDto != null && !listPaymentDto.isEmpty()) {
                List<PaymentStatusDto> failedPayments = listPaymentDto.stream().filter(payment -> payment.getStatus().equals("FAILED")).collect(Collectors.toList());
                List<PaymentStatusDto> newPayments = listPaymentDto.stream().filter(payment -> payment.getStatus().equals("NEW")).collect(Collectors.toList());
                returnFailedTicked(failedPayments);
                updateStatuses(newPayments);
                restTemplate.put(UPDATE_STATUSES_PUT_URL, listPaymentDto);
            }
            log.info("scheduler - no NEW tickets");
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    private void returnFailedTicked(List<PaymentStatusDto> failedPayments) {
        for (PaymentStatusDto paymentDto : failedPayments) {
            ticketRepository.updateAvailableSeatsWithReturnedTicketForUpdate(paymentDto.getId());
            log.info("scheduler - returned one ticket with payment id = " + paymentDto.getId() + " cause: payment status is " + paymentDto.getStatus());
        }
    }

    private void updateStatuses(List<PaymentStatusDto> listPaymentDto) {
        for (PaymentStatusDto paymentDto : listPaymentDto) {
            String oldStatus = paymentDto.getStatus();
            paymentDto.setStatus("DONE");
            log.info("scheduler - payment status changed to DONE (was " + oldStatus + ") in payment with id = " + paymentDto.getId());
        }
    }
}
