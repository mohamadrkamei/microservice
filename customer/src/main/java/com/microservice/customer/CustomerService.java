package com.microservice.customer;

import com.microservice.clients.fraud.FraudClient;
import com.microservice.clients.notification.NotificationClient;
import com.microservice.clients.notification.NotificationRequest;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Slf4j
@Service
public record CustomerService(CustomerRepository customerRepository, RestTemplate restTemplate , FraudClient fraudClient , NotificationClient notificationClient)  implements customerServiceInterface{


    @Override
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

        //todo make it async add to queue
        try {
            notificationClient.sendNotification(new NotificationRequest(customer.getId(),customer.getEmail(),String.format("hi {} , welcome to microservice" , customer.getFirstName())));
            log.info("send notification");
        }catch (Exception e){

            throw new RuntimeException("service Call failed ");
        }



    }


}
