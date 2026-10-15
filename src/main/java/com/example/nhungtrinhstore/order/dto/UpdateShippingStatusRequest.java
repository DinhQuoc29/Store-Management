package com.example.nhungtrinhstore.order.dto;

import com.example.nhungtrinhstore.order.enums.ShippingStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateShippingStatusRequest(

    @NotNull(message = "Trạng thái vận chuyển không được để trống")
    ShippingStatus shippingStatus
) {}
