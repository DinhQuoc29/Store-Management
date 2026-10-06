package com.example.nhungtrinhstore.order.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

/**
 * DTO để cập nhật tiền đặt cọc của đơn hàng.
 * Dùng khi khách thanh toán thêm mà không thay đổi sản phẩm.
 */
public record UpdateDepositRequest(

    @NotNull(message = "Tiền đặt cọc không được để trống")
    @PositiveOrZero(message = "Tiền đặt cọc không được âm")
    BigDecimal depositAmount
) {}
