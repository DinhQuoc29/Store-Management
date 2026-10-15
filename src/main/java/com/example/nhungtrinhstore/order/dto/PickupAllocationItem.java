package com.example.nhungtrinhstore.order.dto;

public record PickupAllocationItem(
    Long orderItemId,
    Long orderId,
    Long customerId,
    String customerName,
    String customerPhone,
    Integer quantity,
    Integer pickedQuantity
) {}