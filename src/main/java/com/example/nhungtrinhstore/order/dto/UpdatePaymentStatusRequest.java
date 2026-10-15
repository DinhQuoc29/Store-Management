package com.example.nhungtrinhstore.order.dto;

import com.example.nhungtrinhstore.order.enums.PaymentStatus;
import jakarta.validation.constraints.NotNull;

public record UpdatePaymentStatusRequest(
    @NotNull(message = "Trạng thái thanh toán không được để trống")
    PaymentStatus paymentStatus
) {}
