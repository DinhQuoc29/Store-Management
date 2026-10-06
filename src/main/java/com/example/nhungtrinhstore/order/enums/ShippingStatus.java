package com.example.nhungtrinhstore.order.enums;

/**
 * Trạng thái vận chuyển của đơn hàng.
 * - NOT_SHIPPED: Chưa gửi
 * - PARTIALLY_SHIPPED: Đã gửi một phần
 * - SHIPPED: Đã gửi toàn bộ
 */
public enum ShippingStatus {
    NOT_SHIPPED,
    PARTIALLY_SHIPPED,
    SHIPPED
}
