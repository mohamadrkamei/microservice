package com.microservice.fraud;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/fraud")
@Slf4j
public record FraudController(FraudService fraudService) {

    @GetMapping(path = "/{custoemerId}")
    public FraudCheckResponse getfraud(@PathVariable("custoemerId") Integer customerId){
        var  isFraudulentCustomer =fraudService.isFraudulentCustomer(customerId);
        log.info("fraud check for customer {}" , customerId);

        return new FraudCheckResponse(isFraudulentCustomer);

    }

}
