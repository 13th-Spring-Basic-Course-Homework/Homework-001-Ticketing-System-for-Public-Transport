package com.kshrd.homework001ticketingsystemforpublictransport.model.dto.request;

import java.util.List;

public class UpdatePaymentStatusRequest {
    private List<Long> ticketIds;
    private Boolean paymentStatus;

    public UpdatePaymentStatusRequest() {
    }

    public UpdatePaymentStatusRequest(List<Long> ticketIds, Boolean paymentStatus) {
        this.ticketIds = ticketIds;
        this.paymentStatus = paymentStatus;
    }

    public List<Long> getTicketIds() {
        return ticketIds;
    }

    public void setTicketIds(List<Long> ticketIds) {
        this.ticketIds = ticketIds;
    }

    public Boolean getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(Boolean paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
