package com.example.nhungtrinhstore.product.dto;

import java.time.LocalDateTime;

/**
 * DTO trả về thông tin sản phẩm cho client.
 */
public record ProductResponse(
    Long id,
    String productCode,
    String productName,
    String imageUrl,
    LocalDateTime createdAt
) {}
