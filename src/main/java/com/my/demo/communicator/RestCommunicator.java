package com.my.demo.communicator;

import com.my.demo.dto.TicketPurchaseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class RestCommunicator {
    private final RestTemplate restTemplate;

    private static final String CREATE_PAYMENT_POST_URL = "/create";
    private static final String FIND_PAYMENT_STATUS_GET_URL = "/statuses?id=";


    public Long createPayment(TicketPurchaseDto dto){
        return restTemplate.postForObject(CREATE_PAYMENT_POST_URL, dto, Long.class);
    }

    public String getPaymentStatusById(Long paymentId){
        return restTemplate.getForObject(FIND_PAYMENT_STATUS_GET_URL + paymentId, String.class);
    }


}
