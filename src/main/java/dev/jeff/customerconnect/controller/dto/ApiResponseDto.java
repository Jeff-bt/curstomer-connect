package dev.jeff.customerconnect.controller.dto;

import java.util.List;

public record ApiResponseDto<T>(List<T> data, PaginationResponseDto pagination) {
}
