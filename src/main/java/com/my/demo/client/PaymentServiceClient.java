package com.my.demo.client;

import com.my.demo.dto.TicketBookingDto;
import com.my.demo.entity.Payment;
import com.my.demo.entity.PaymentStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PaymentServiceClient {
    private final RestTemplate restTemplate;

    private static final String CREATE_PAYMENT_POST_URL = "/create";
    private static final String FIND_PAYMENT_STATUS_GET_URL = "/statuses/";
    private static final String GET_PAYMENTS_BY_STATUS = "/statuses?status=";
    private static final String UPDATE_PAYMENTS_STATUSES = "/statuses";


    public Long createPayment(TicketBookingDto dto) {
        return restTemplate.postForObject(CREATE_PAYMENT_POST_URL, dto, Long.class);
    }

    public PaymentStatus getPaymentStatusById(Long paymentId) {
        return restTemplate.getForObject(FIND_PAYMENT_STATUS_GET_URL + paymentId, PaymentStatus.class);
    }

    public List<Payment> getPaymentsByStatus(PaymentStatus status) {
        Payment[] payments = restTemplate.getForObject(GET_PAYMENTS_BY_STATUS + status, Payment[].class);
        if (payments != null) {
            return Arrays.asList(payments);
        }
        return Collections.emptyList();
    }

    public void updateStatuses(List<Payment> payments) {
        restTemplate.put(UPDATE_PAYMENTS_STATUSES, payments);
    }
}
