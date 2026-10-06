package com.example.nhungtrinhstore.order.dto;

import com.example.nhungtrinhstore.order.enums.PaymentStatus;
import com.example.nhungtrinhstore.order.enums.PickupStatus;
import com.example.nhungtrinhstore.order.enums.ShippingStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO dùng cho danh sách đơn hàng (list view).
 * Không bao gồm danh sách items để tối ưu performance khi load nhiều đơn.
 */
public record OrderSummaryResponse(
    Long id,
    Long customerId,
    String customerName,
    String customerPhone,
    LocalDate orderDate,
    String note,
    BigDecimal totalAmount,
    BigDecimal depositAmount,
    BigDecimal remainingAmount,
    PickupStatus pickupStatus,
    PaymentStatus paymentStatus,
    ShippingStatus shippingStatus,
    int itemCount,             // Tổng số dòng sản phẩm
    int totalQuantity,         // Tổng SL đặt (sum of quantity)
    int totalPickedQuantity    // Tổng SL đã lấy (sum of pickedQuantity)
) {}
