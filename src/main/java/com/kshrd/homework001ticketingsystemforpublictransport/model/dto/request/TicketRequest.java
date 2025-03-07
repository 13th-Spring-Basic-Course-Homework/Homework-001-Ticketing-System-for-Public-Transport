package com.kshrd.homework001ticketingsystemforpublictransport.model.dto.request;

import com.kshrd.homework001ticketingsystemforpublictransport.model.enums.TicketStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
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

    @PastOrPresent
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

