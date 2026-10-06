package com.example.nhungtrinhstore.order.dto;

/**
 * Thông tin phân bổ số lượng nhặt theo từng khách hàng cụ thể,
 * dùng trong màn hình Pickup Checklist khi mở rộng card sản phẩm.
 *
 * <p>Ví dụ: Sản phẩm "Áo hoodie đen" được đặt bởi 3 khách:
 *   - Khách Nguyễn Thị A: cần 2, đã nhặt 1
 *   - Khách Trần Văn B: cần 1, đã nhặt 0
 *   ...
 * </p>
 */
public record PickupAllocationItem(
    Long orderItemId,       // ID của OrderItem — dùng để gọi PATCH cập nhật
    Long orderId,
    Long customerId,
    String customerName,
    String customerPhone,
    Integer quantity,       // Số lượng cần nhặt cho khách này
    Integer pickedQuantity  // Số lượng đã nhặt được cho khách này
) {}
