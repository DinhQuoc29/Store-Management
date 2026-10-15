package com.example.nhungtrinhstore.order.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record UpdatePickedQuantityRequest(

    @NotNull(message = "Số lượng đã nhặt không được để trống")
    @Min(value = 0, message = "Số lượng đã nhặt không được âm")
    Integer pickedQuantity
) {}
