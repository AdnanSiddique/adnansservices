package com.adnan.customer;

import com.adnan.amqp.RabbitMqMessageProducer;
import com.adnan.clients.fraud.FraudCheckResponse;
import com.adnan.clients.fraud.FraudClient;
import com.adnan.clients.notification.NotificationClient;
import com.adnan.clients.notification.NotificationRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Service
@Slf4j
public record CustomerService(CustomerRepository customerRepository,
                              RestTemplate restTemplate,
                              FraudClient fraudClient,
                              NotificationClient notificationClient,
                              RabbitMqMessageProducer rabbitMqMessageProducer) {
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
        } else {
            NotificationRequest notificationRequest =
                    new NotificationRequest(
                            customer.getId(),
                            customer.getEmail(),
                            customer.getFirstName(),
                            "Customer not a fraudster",
                            null);
            //notificationClient.sendNotification(notificationRequest);
            rabbitMqMessageProducer.publish(notificationRequest, "internal.exchange","internal.notification.routing-key");
        }
    }
}
