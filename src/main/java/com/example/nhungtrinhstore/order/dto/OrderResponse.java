package com.example.nhungtrinhstore.order.dto;

import com.example.nhungtrinhstore.order.enums.PaymentStatus;
import com.example.nhungtrinhstore.order.enums.PickupStatus;
import com.example.nhungtrinhstore.order.enums.ShippingStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse(
    Long id,

    // --- Customer info (inline, không cần gọi thêm API) ---
    Long customerId,
    String customerName,
    String customerPhone,
    String customerAddress,
    String customerFacebookUrl,

    LocalDate orderDate,
    String note,

    // --- Financials ---
    BigDecimal totalAmount,
    BigDecimal depositAmount,
    BigDecimal remainingAmount,

    // --- Statuses ---
    PickupStatus pickupStatus,
    PaymentStatus paymentStatus,
    ShippingStatus shippingStatus,

    LocalDateTime createdAt,

    // --- Items ---
    List<OrderItemResponse> items
) {}
