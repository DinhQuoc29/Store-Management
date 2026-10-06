package com.example.nhungtrinhstore.customer.dto;

import java.time.LocalDateTime;

/**
 * DTO trả về thông tin khách hàng cho client.
 */
public record CustomerResponse(
    Long id,
    String fullName,
    String facebookUrl,
    String phoneNumber,
    String address,
    LocalDateTime createdAt
) {}
