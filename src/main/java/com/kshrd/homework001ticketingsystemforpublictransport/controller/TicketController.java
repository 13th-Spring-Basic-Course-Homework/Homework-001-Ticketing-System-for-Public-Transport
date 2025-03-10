package com.kshrd.homework001ticketingsystemforpublictransport.controller;

import com.kshrd.homework001ticketingsystemforpublictransport.model.Ticket;
import com.kshrd.homework001ticketingsystemforpublictransport.model.dto.request.TicketRequest;
import com.kshrd.homework001ticketingsystemforpublictransport.model.dto.request.UpdatePaymentStatusRequest;
import com.kshrd.homework001ticketingsystemforpublictransport.model.dto.response.APIResponse;
import com.kshrd.homework001ticketingsystemforpublictransport.model.dto.response.PagedResponse;
import com.kshrd.homework001ticketingsystemforpublictransport.model.enums.TicketStatus;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

import static com.kshrd.homework001ticketingsystemforpublictransport.model.utils.ResponseUtil.buildResponse;
import static com.kshrd.homework001ticketingsystemforpublictransport.model.utils.ResponseUtil.pageResponse;

@RestController
@RequestMapping("api/v1/tickets")
public class TicketController {

    private static final List<Ticket> TICKETS = new ArrayList<>();
    private static final AtomicLong TICKET_ID = new AtomicLong(4L);

    public TicketController() {
        TICKETS.add(new Ticket(1L, "John Doe", LocalDate.of(2024, 12, 20), "Station A", "Station B", 120.0, false, TicketStatus.BOOKED, "A1"));
        TICKETS.add(new Ticket(2L, "Jane Smith", LocalDate.of(2024, 12, 21), "Station C", "Station D", 80.0, false, TicketStatus.BOOKED, "B2"));
        TICKETS.add(new Ticket(3L, "Alice Johnson", LocalDate.of(2024, 12, 22), "Station E", "Station F", 150.0, false, TicketStatus.CANCELLED, "C3"));
        TICKETS.add(new Ticket(4L, "Bob Brown", LocalDate.of(2024, 12, 23), "Station G", "Station H", 200.0, true, TicketStatus.COMPLETED, "D4"));
    }

    @Operation(summary = "Create a new ticket")
    @PostMapping
    public ResponseEntity<APIResponse<Ticket>> createTicket(@RequestBody @Valid TicketRequest ticketRequest) {
        Ticket ticket = new Ticket(
                TICKET_ID.incrementAndGet(),
                ticketRequest.getPassengerName(),
                ticketRequest.getTravelDate(),
                ticketRequest.getSourceStation(),
                ticketRequest.getDestinationStation(),
                ticketRequest.getPrice(),
                ticketRequest.getPaymentStatus(),
                ticketRequest.getTicketStatus(),
                ticketRequest.getSeatNumber()
        );
        TICKETS.add(ticket);
        return buildResponse(true, "Ticket created successfully.", HttpStatus.CREATED, ticket);
    }

    @Operation(summary = "Get a ticket by ID")
    @GetMapping("/{ticket-id}")
    public ResponseEntity<APIResponse<Ticket>> getTicket(@PathVariable("ticket-id") @Positive Long ticketId) {
        return TICKETS.stream()
                .filter(ticket -> ticket.getTicketId().equals(ticketId))
                .findFirst()
                .map(ticket -> buildResponse(true, "Ticket retrieved successfully.", HttpStatus.OK, ticket))
                .orElse(buildResponse(false, "No ticket found with ID: " + ticketId, HttpStatus.NOT_FOUND, null));
    }

    @Operation(summary = "Update an existing ticket by ID")
    @PutMapping("/{ticket-id}")
    public ResponseEntity<APIResponse<Ticket>> updateTicket(@PathVariable("ticket-id") @Positive Long ticketId, @RequestBody @Valid TicketRequest ticketRequest) {
        for (Ticket ticket : TICKETS) {
            if (ticket.getTicketId().equals(ticketId)) {
                ticket.setPassengerName(ticketRequest.getPassengerName());
                ticket.setTravelDate(ticketRequest.getTravelDate());
                ticket.setSourceStation(ticketRequest.getSourceStation());
                ticket.setDestinationStation(ticketRequest.getDestinationStation());
                ticket.setPrice(ticketRequest.getPrice());
                ticket.setPaymentStatus(ticketRequest.getPaymentStatus());
                ticket.setTicketStatus(ticketRequest.getTicketStatus());
                ticket.setSeatNumber(ticketRequest.getSeatNumber());
                return buildResponse(true, "Ticket updated successfully.", HttpStatus.OK, ticket);
            }
        }
        return buildResponse(false, "No ticket found with ID: " + ticketId, HttpStatus.NOT_FOUND, null);
    }

    @Operation(summary = "Delete a ticket by ID")
    @DeleteMapping("/{ticket-id}")
    public ResponseEntity<APIResponse<Ticket>> deleteTicket(@PathVariable("ticket-id") @Positive Long ticketId) {
        for (Ticket ticket : TICKETS) {
            if (ticket.getTicketId().equals(ticketId)) {
                TICKETS.remove(ticket);
                return buildResponse(true, "Ticket deleted successfully.", HttpStatus.OK, null);
            }
        }
        return buildResponse(false, "No ticket found with ID: " + ticketId, HttpStatus.NOT_FOUND, null);
    }

    @Operation(summary = "Get all tickets")
    @GetMapping
    public ResponseEntity<APIResponse<PagedResponse<List<Ticket>>>> getAllTickets(@RequestParam(defaultValue = "1") @Positive Integer page, @RequestParam(defaultValue = "10") @Positive Integer size) {
        int offset = (page - 1) * size;
        List<Ticket> paginatedTickets = TICKETS.stream()
                .skip(offset)
                .limit(size)
                .toList();
        return buildResponse(true, "All tickets retrieved successfully.", HttpStatus.OK, pageResponse(paginatedTickets, TICKETS.size(), page, size));
    }

    @Operation(summary = "Filter tickets by status and travel date")
    @GetMapping("/filter")
    public ResponseEntity<APIResponse<List<Ticket>>> filteredTicketsByTickStatusAndTravelDate(@RequestParam TicketStatus ticketStatus, @RequestParam LocalDate travelDate) {
        List<Ticket> filteredTickets = TICKETS.stream()
                .filter(ticket -> ticket.getTicketStatus().equals(ticketStatus) && ticket.getTravelDate().equals(travelDate))
                .toList();
        return buildResponse(true, "Tickets filtered successfully.", HttpStatus.OK, filteredTickets);
    }

    @Operation(summary = "Search tickets by passenger name")
    @GetMapping("/search")
    public ResponseEntity<APIResponse<List<Ticket>>> searchByPassengerName(@RequestParam String passengerName) {
        List<Ticket> searchedTickets = TICKETS.stream()
                .filter(ticket -> ticket.getPassengerName().equalsIgnoreCase(passengerName))
                .toList();
        return buildResponse(true, "Tickets searched successfully.", HttpStatus.OK, searchedTickets);
    }

    @Operation(summary = "Bulk update payment status for multiple tickets")
    @PutMapping
    public ResponseEntity<APIResponse<List<Ticket>>> updatePaymentStatus(@RequestBody @Valid UpdatePaymentStatusRequest updatePaymentStatusRequest) {
        List<Ticket> updatedTickets = new ArrayList<>();
        for (Ticket ticket : TICKETS) {
            if (updatePaymentStatusRequest.getTicketIds().contains(ticket.getTicketId())) {
                ticket.setPaymentStatus(updatePaymentStatusRequest.getPaymentStatus());
                updatedTickets.add(ticket);
            }
        }
        return buildResponse(true, "Payment status updated successfully.", HttpStatus.OK, updatedTickets);
    }

    @Operation(summary = "Bulk create tickets")
    @PostMapping("/bulk")
    public ResponseEntity<APIResponse<List<Ticket>>> bulkTickets(@RequestBody @Valid List<TicketRequest> ticketRequests) {
        List<Ticket> createdTickets = new ArrayList<>();
        for (TicketRequest ticketRequest : ticketRequests) {
            Ticket ticket = new Ticket(
                    TICKET_ID.incrementAndGet(),
                    ticketRequest.getPassengerName(),
                    ticketRequest.getTravelDate(),
                    ticketRequest.getSourceStation(),
                    ticketRequest.getDestinationStation(),
                    ticketRequest.getPrice(),
                    ticketRequest.getPaymentStatus(),
                    ticketRequest.getTicketStatus(),
                    ticketRequest.getSeatNumber()
            );
            TICKETS.add(ticket);
            createdTickets.add(ticket);
        }
        return buildResponse(true, "Bulk tickets created successfully.", HttpStatus.CREATED, createdTickets);
    }
}
