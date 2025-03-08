package com.kshrd.homework001ticketingsystemforpublictransport.model.utils;

import com.kshrd.homework001ticketingsystemforpublictransport.model.dto.response.APIResponse;
import com.kshrd.homework001ticketingsystemforpublictransport.model.dto.response.PagedResponse;
import com.kshrd.homework001ticketingsystemforpublictransport.model.dto.response.PaginationInfo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

public class ResponseUtil {

    public static <T> ResponseEntity<APIResponse<T>> buildResponse(Boolean success, String message, HttpStatus status, T payload) {
        APIResponse<T> response = new APIResponse<>(success, message, status, payload, LocalDateTime.now());
        return ResponseEntity.status(status).body(response);
    }

    public static <T> PagedResponse<T> pageResponse(T content, Integer totalCount, Integer page, Integer size) {
        int totalPages = (int) Math.ceil((double) totalCount / size);
        PaginationInfo paginationInfo = new PaginationInfo(totalCount, page, size, totalPages);
        return new PagedResponse<>(content, paginationInfo);
    }

}
