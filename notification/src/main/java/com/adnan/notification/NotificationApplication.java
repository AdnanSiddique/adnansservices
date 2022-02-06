package com.adnan.notification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication(
        scanBasePackages = {"com.adnan.notification", "com.adnan.amqp"}
)
@EnableEurekaClient
public class NotificationApplication {
    public static void main(String[] args) {
        SpringApplication.run(NotificationApplication.class, args);
    }

    /**
     * Used for testing rabbitmq message producer
     * @Bean
    public CommandLineRunner commandLineRunner(RabbitMqMessageProducer rabbitMqMessageProducer,
                                               NotificationConfig notificationConfig){
        return args -> {
                rabbitMqMessageProducer.publish(
                        new Person("Adnan", "test@test.com"),
                        notificationConfig.getInternalExchange(),
                        notificationConfig.getInternalNotificationRoutingKey());
        };
    }

    record Person(String name, String email){
    }*/
}

