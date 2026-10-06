package com.example.nhungtrinhstore.order.dto;

import com.example.nhungtrinhstore.order.enums.ShippingStatus;
import jakarta.validation.constraints.NotNull;

/**
 * DTO để cập nhật trạng thái vận chuyển của đơn hàng.
 */
public record UpdateShippingStatusRequest(

    @NotNull(message = "Trạng thái vận chuyển không được để trống")
    ShippingStatus shippingStatus
) {}
