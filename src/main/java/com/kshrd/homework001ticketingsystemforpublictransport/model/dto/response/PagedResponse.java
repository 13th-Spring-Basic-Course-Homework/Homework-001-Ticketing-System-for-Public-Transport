package com.kshrd.homework001ticketingsystemforpublictransport.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PagedResponse<T> {
    private T items;
    private PaginationInfo pagination;
}

