package com.example.nhungtrinhstore.order.entity;

import com.example.nhungtrinhstore.product.entity.Product;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

/**
 * Chi tiết đơn hàng.
 *
 * <p>Giá bán (unit_price) được ghi nhận trực tiếp tại thời điểm tạo đơn,
 * không phụ thuộc vào bảng Product — phù hợp với mô hình giá biến động theo đợt sale.</p>
 *
 * <p>picked_quantity: Số lượng thực tế đã nhặt tại store.
 * Được cập nhật qua endpoint PATCH /api/orders/items/{id}/picked-quantity.</p>
 *
 * <p>shipped_quantity: Số lượng đã gửi cho khách hàng.
 * Được cập nhật qua endpoint PATCH /api/orders/items/{id}/shipped-quantity.
 * Cho phép gửi hàng từng phần khi hàng chưa về đủ.</p>
 */
@Entity
@Table(name = "order_items", indexes = {
    @Index(name = "idx_order_item_order_id", columnList = "order_id"),
    @Index(name = "idx_order_item_product_id", columnList = "product_id")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    /** Số lượng đặt trong đơn hàng. */
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    /**
     * Số lượng đã nhặt được tại store (pick up).
     * Mặc định = 0 khi tạo đơn, tăng dần trong quá trình gom hàng.
     */
    @Column(name = "picked_quantity", nullable = false)
    @Builder.Default
    private Integer pickedQuantity = 0;

    /**
     * Số lượng đã gửi cho khách hàng.
     * Cho phép gửi từng phần khi hàng chưa về đủ hoặc khách yêu cầu giao trước.
     * Mặc định = 0 khi tạo đơn.
     */
    @Column(name = "shipped_quantity", nullable = false)
    @Builder.Default
    private Integer shippedQuantity = 0;

    /**
     * Đơn giá bán tại thời điểm tạo đơn (VND).
     * Lưu riêng để phòng trường hợp giá thay đổi sau đó.
     */
    @Column(name = "unit_price", nullable = false, precision = 15, scale = 0)
    private BigDecimal unitPrice;

    // ---------------------------------------------------------------
    // Computed helper (không map vào DB)
    // ---------------------------------------------------------------

    /** Thành tiền của dòng này = quantity * unitPrice. */
    @Transient
    public BigDecimal getLineTotal() {
        if (quantity == null || unitPrice == null) return BigDecimal.ZERO;
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }
}
