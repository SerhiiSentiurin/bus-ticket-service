package com.my.demo.communicator;

import com.my.demo.dto.ClientDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class RestCommunicator {
    private final RestTemplate restTemplate;

    private static final String CREATE_PAYMENT_POST_URL = "http://localhost:8080/payments/create";
    private static final String FIND_PAYMENT_STATUS_GET_URL = "http://localhost:8080/payments/statuses?id=";
    private static final String FIND_FAILED_AND_NEW_STATUSES_GET_URL = "http://localhost:8080/payments/statuses/bad";
    private static final String UPDATE_STATUSES_PUT_URL = "http://localhost:8080/payments/statuses";

    public Long createPayment(ClientDto dto){
        return restTemplate.postForObject(CREATE_PAYMENT_POST_URL, dto, Long.class);
    }

    public String getPaymentStatusById(Long paymentId){
        return restTemplate.getForObject(FIND_PAYMENT_STATUS_GET_URL + paymentId, String.class);
    }


}
