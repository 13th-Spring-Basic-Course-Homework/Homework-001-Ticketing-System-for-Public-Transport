package com.kshrd.homework001ticketingsystemforpublictransport.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaginationInfo {
    private long totalElements;
    private int currentPage;
    private int pageSize;
    private int totalPages;
}
