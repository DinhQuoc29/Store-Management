package com.example.nhungtrinhstore.order.enums;

/**
 * Trạng thái nhặt hàng (pick up) của đơn hàng.
 * - NOT_PICKED: Chưa nhặt bất kỳ sản phẩm nào (tổng picked_quantity = 0)
 * - PARTIALLY_PICKED: Đã nhặt một phần (0 < tổng picked < tổng quantity)
 * - FULLY_PICKED: Đã nhặt đủ tất cả sản phẩm (tổng picked >= tổng quantity)
 */
public enum PickupStatus {
    NOT_PICKED,
    PARTIALLY_PICKED,
    FULLY_PICKED
}
