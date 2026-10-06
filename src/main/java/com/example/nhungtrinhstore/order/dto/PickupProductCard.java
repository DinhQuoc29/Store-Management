package com.example.nhungtrinhstore.order.dto;

import java.util.List;

/**
 * Card sản phẩm trong màn hình Pickup Checklist.
 */
public record PickupProductCard(
    Long productId,
    String productCode,
    String productName,
    String imageUrl,

    Integer totalNeeded,
    Integer totalPicked,

    List<PickupAllocationItem> allocations
) {}
