package com.example.nhungtrinhstore.order.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * DTO để cập nhật số lượng đã nhặt (picked_quantity) cho một OrderItem.
 * Được dùng trong màn hình Pickup Checklist khi nhấn [+] / [-].
 */
public record UpdatePickedQuantityRequest(

    @NotNull(message = "Số lượng đã nhặt không được để trống")
    @Min(value = 0, message = "Số lượng đã nhặt không được âm")
    Integer pickedQuantity
) {}
