package com.adnan.customer;

import com.adnan.clients.fraud.FraudCheckResponse;
import com.adnan.clients.fraud.FraudClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public record CustomerService(CustomerRepository customerRepository, RestTemplate restTemplate, FraudClient fraudClient) {
    public void registerCustomer(CustomerRegistrationRequest customerRegistrationRequest) {
      Customer customer = Customer.builder()
              .firstName(customerRegistrationRequest.firstName())
              .lastName(customerRegistrationRequest.lastName())
              .email(customerRegistrationRequest.email()).build();

        customerRepository.saveAndFlush(customer);
        /*FraudCheckResponse fraudCheckResponse = restTemplate.getForObject("http://FRAUD/api/v1/fraud-check/{customerId}",
                FraudCheckResponse.class,
                +customer.getId());*/
        FraudCheckResponse fraudCheckResponse = fraudClient.isFraudster(customer.getId());
        if(fraudCheckResponse.isFraudster()) {
            throw new IllegalArgumentException("Customer Fraudster");
        }
    }
}
