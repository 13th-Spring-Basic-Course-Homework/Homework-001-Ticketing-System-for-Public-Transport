package com.kshrd.homework001ticketingsystemforpublictransport;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "Ticketing System for Public Transport",
                description = "The Ticketing System for Public Transport is a Spring Boot application for managing ticket bookings, payments, and scheduling.",
                version = "1.0")
)
public class Homework001TicketingSystemForPublicTransportApplication {

    public static void main(String[] args) {
        SpringApplication.run(Homework001TicketingSystemForPublicTransportApplication.class, args);
    }

}
