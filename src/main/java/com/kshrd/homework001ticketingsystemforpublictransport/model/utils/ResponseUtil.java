package com.kshrd.homework001ticketingsystemforpublictransport.model.utils;

import com.kshrd.homework001ticketingsystemforpublictransport.model.dto.response.APIResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

public class ResponseUtil {

    public static <T> ResponseEntity<APIResponse<T>> buildResponse(Boolean success, String message, HttpStatus status, T payload) {
        APIResponse<T> response = new APIResponse<>(success, message, status, payload, LocalDateTime.now());
        return ResponseEntity.status(status).body(response);
    }

}
