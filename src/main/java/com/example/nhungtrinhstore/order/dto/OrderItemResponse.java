package com.example.nhungtrinhstore.order.dto;

import java.math.BigDecimal;

/**
 * DTO trả về thông tin chi tiết từng dòng đơn hàng.
 */
public record OrderItemResponse(
    Long id,
    Long productId,
    String productCode,
    String productName,
    String productImageUrl,
    Integer quantity,
    Integer pickedQuantity,
    Integer shippedQuantity,
    BigDecimal unitPrice,
    BigDecimal lineTotal
) {}
