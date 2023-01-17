package com.microservice.customer;

import com.microservice.clients.fraud.FraudClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service

public record CustomerService(CustomerRepository customerRepository, RestTemplate restTemplate , FraudClient fraudClient) {


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
      var fraudResponse = fraudClient.getfraud(customer.getId());
        if (fraudResponse.isFlauder()) {

            throw new IllegalStateException("fraudster");
        }

        //todo : send notification
    }
}
