package com.kshrd.homework001ticketingsystemforpublictransport.controller;

import com.kshrd.homework001ticketingsystemforpublictransport.model.Ticket;
import com.kshrd.homework001ticketingsystemforpublictransport.model.dto.request.TicketRequest;
import com.kshrd.homework001ticketingsystemforpublictransport.model.dto.request.UpdatePaymentStatusRequest;
import com.kshrd.homework001ticketingsystemforpublictransport.model.dto.response.APIResponse;
import com.kshrd.homework001ticketingsystemforpublictransport.model.enums.TicketStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

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

    @PostMapping
    public ResponseEntity<APIResponse<Ticket>> createTicket(@RequestBody TicketRequest ticketRequest) {
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
        APIResponse<Ticket> response = new APIResponse<>(
                "Ticket successfully created.",
                HttpStatus.CREATED,
                ticket,
                LocalDateTime.now()
        );
        TICKETS.add(ticket);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{ticketId}")
    public ResponseEntity<APIResponse<Ticket>> getTicket(@PathVariable Long ticketId) {
        APIResponse<Ticket> response;
        for (Ticket ticket : TICKETS) {
            if (ticket.getTicketId().equals(ticketId)) {
                response = new APIResponse<>(
                        "Ticket successfully found.",
                        HttpStatus.OK,
                        ticket,
                        LocalDateTime.now()
                );
                return ResponseEntity.status(HttpStatus.OK).body(response);
            }
        }
        response = new APIResponse<>(
                "Ticket not found with ID: " + ticketId + ".",
                HttpStatus.OK,
                null,
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @PutMapping("/{ticketId}")
    public ResponseEntity<APIResponse<Ticket>> updateTicket(@PathVariable Long ticketId, @RequestBody TicketRequest ticketRequest) {
        APIResponse<Ticket> response;
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
                response = new APIResponse<>(
                        "Ticket successfully updated.",
                        HttpStatus.OK,
                        ticket,
                        LocalDateTime.now()
                );
                return ResponseEntity.status(HttpStatus.OK).body(response);
            }
        }
        response = new APIResponse<>(
                "Ticket not found with ID: " + ticketId + ".",
                HttpStatus.OK,
                null,
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @DeleteMapping("/{ticketId}")
    public ResponseEntity<APIResponse<Ticket>> deleteTicket(@PathVariable Long ticketId) {
        APIResponse<Ticket> response;
        for (Ticket ticket : TICKETS) {
            if (ticket.getTicketId().equals(ticketId)) {
                TICKETS.remove(ticket);
                response = new APIResponse<>(
                        "Ticket successfully deleted.",
                        HttpStatus.OK,
                        ticket,
                        LocalDateTime.now()
                );
                return ResponseEntity.status(HttpStatus.OK).body(response);
            }
        }
        response = new APIResponse<>(
                "Ticket not found with ID: " + ticketId + ".",
                HttpStatus.OK,
                null,
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @GetMapping
    public ResponseEntity<APIResponse<List<Ticket>>> getAllTickets() {
        APIResponse<List<Ticket>> response = new APIResponse<>(
                "Ticket successfully created.",
                HttpStatus.OK,
                TICKETS,
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/filter")
    public ResponseEntity<APIResponse<List<Ticket>>> filteredTicketsByTickStatusOrTravelDate(@RequestParam TicketStatus ticketStatus, @RequestParam LocalDate travelDate) {
        List<Ticket> filteredTickets = new ArrayList<>();
        for (Ticket ticket : TICKETS) {
            if (ticket.getTicketStatus().equals(ticketStatus) || ticket.getTravelDate().equals(travelDate)) {
                filteredTickets.add(ticket);
            }
        }
        APIResponse<List<Ticket>> response = new APIResponse<>(
                "Ticket successfully filtered.",
                HttpStatus.OK,
                filteredTickets,
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/search")
    public ResponseEntity<APIResponse<List<Ticket>>> searchByPassengerName(@RequestParam String passengerName) {
        List<Ticket> searchedTickets = new ArrayList<>();
        for (Ticket ticket : TICKETS) {
            if (ticket.getPassengerName().equals(passengerName)) {
                searchedTickets.add(ticket);
            }
        }
        APIResponse<List<Ticket>> response = new APIResponse<>(
                "Ticket successfully searched.",
                HttpStatus.OK,
                searchedTickets,
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping
    public ResponseEntity<APIResponse<List<Ticket>>> updatePaymentStatus(@RequestBody UpdatePaymentStatusRequest updatePaymentStatusRequest) {
        List<Ticket> tickets = new ArrayList<>();
        for (Ticket ticket : TICKETS) {
            for (Long ticketId : updatePaymentStatusRequest.getTicketIds()) {
                if (ticket.getTicketId().equals(ticketId)) {
                    ticket.setPaymentStatus(updatePaymentStatusRequest.getPaymentStatus());
                    tickets.add(ticket);
                }
            }
        }
        APIResponse<List<Ticket>> response = new APIResponse<>(
                "Ticket successfully updated payment status.",
                HttpStatus.OK,
                tickets,
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/bulk")
    public ResponseEntity<APIResponse<List<Ticket>>> bulkTickets(@RequestBody List<TicketRequest> ticketRequests) {
        List<Ticket> tickets = new ArrayList<>();
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
            tickets.add(ticket);
        }

        APIResponse<List<Ticket>> response = new APIResponse<>(
                "Ticket successfully created.",
                HttpStatus.CREATED,
                tickets,
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
