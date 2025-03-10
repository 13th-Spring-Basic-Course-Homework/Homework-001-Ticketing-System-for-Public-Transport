package com.kshrd.homework001ticketingsystemforpublictransport.model.dto.request;

import com.kshrd.homework001ticketingsystemforpublictransport.model.enums.TicketStatus;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketRequest {

    @NotBlank
    @NotNull
    private String passengerName;

    @FutureOrPresent
    private LocalDate travelDate;

    @NotBlank
    @NotNull
    private String sourceStation;

    @NotBlank
    @NotNull
    private String destinationStation;

    @Positive
    private Double price;

    @NotNull
    private Boolean paymentStatus;

    @NotNull
    private TicketStatus ticketStatus;

    @Size(max = 10)
    private String seatNumber;
}

