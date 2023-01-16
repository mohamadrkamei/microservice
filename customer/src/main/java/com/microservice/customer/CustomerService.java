package com.microservice.customer;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service

public record CustomerService(CustomerRepository customerRepository, RestTemplate restTemplate) {


    public void registerCustomer(CustomerResgistrationRequest request) {

        Customer customer = Customer.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .build();
        //todo: check if email valid
        //todo: check if email not taken

        customerRepository.saveAndFlush(customer);
        // todo : check if fraudster
        FraudCheckResponse fraudCheckResponse = restTemplate.getForObject(
                "http://localhost:8083/api/v1/fraud/{customerId}",
                FraudCheckResponse.class,
                customer.getId()
        );

        if (fraudCheckResponse.isFlauder()) {

            throw new IllegalStateException("fraudster");
        }

        //todo : send notification
    }
}
