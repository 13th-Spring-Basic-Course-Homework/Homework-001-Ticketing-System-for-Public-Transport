package com.kshrd.homework001ticketingsystemforpublictransport.model.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePaymentStatusRequest {

    @NotEmpty
    private List<@Positive Long> ticketIds;

    @NotNull
    private Boolean paymentStatus;
}
