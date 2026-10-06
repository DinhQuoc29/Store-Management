package com.example.nhungtrinhstore.order.entity;

import com.example.nhungtrinhstore.customer.entity.Customer;
import com.example.nhungtrinhstore.order.enums.PaymentStatus;
import com.example.nhungtrinhstore.order.enums.PickupStatus;
import com.example.nhungtrinhstore.order.enums.ShippingStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Thực thể Đơn hàng gom hàng xách tay Hàn Quốc.
 *
 * <p>Quy tắc tính toán (được thực thi tại Service layer):</p>
 * <ul>
 *   <li>total_amount = SUM(orderItem.quantity * orderItem.unitPrice)</li>
 *   <li>remaining_amount = total_amount - deposit_amount</li>
 *   <li>paymentStatus: UNPAID, PAID</li>
 *   <li>pickupStatus: NOT_PICKED (pickedSum=0), PARTIALLY_PICKED (0 < pickedSum < quantitySum), FULLY_PICKED (pickedSum >= quantitySum)</li>
 * </ul>
 */
@Entity
@Table(name = "orders", indexes = {
    @Index(name = "idx_order_customer_id", columnList = "customer_id"),
    @Index(name = "idx_order_pickup_status", columnList = "pickup_status"),
    @Index(name = "idx_order_payment_status", columnList = "payment_status"),
    @Index(name = "idx_order_date", columnList = "order_date")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(name = "order_date", nullable = false)
    private LocalDate orderDate;

    @Column(name = "note", columnDefinition = "TEXT")
    private String note;

    // ---------------------------------------------------------------
    // Financials (đơn vị: VND, không có phần thập phân)
    // ---------------------------------------------------------------

    /** Tổng tiền hàng = SUM(quantity * unit_price). Tự động tính lại khi thay đổi items. */
    @Column(name = "total_amount", nullable = false, precision = 15, scale = 0)
    @Builder.Default
    private BigDecimal totalAmount = BigDecimal.ZERO;

    /** Số tiền đặt cọc khách đã trả. */
    @Column(name = "deposit_amount", nullable = false, precision = 15, scale = 0)
    @Builder.Default
    private BigDecimal depositAmount = BigDecimal.ZERO;

    /** Số tiền còn lại = total_amount - deposit_amount. Tự động tính. */
    @Column(name = "remaining_amount", nullable = false, precision = 15, scale = 0)
    @Builder.Default
    private BigDecimal remainingAmount = BigDecimal.ZERO;

    // ---------------------------------------------------------------
    // Statuses
    // ---------------------------------------------------------------

    @Enumerated(EnumType.STRING)
    @Column(name = "pickup_status", nullable = false, length = 20)
    @Builder.Default
    private PickupStatus pickupStatus = PickupStatus.NOT_PICKED;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", nullable = false, length = 20)
    @Builder.Default
    private PaymentStatus paymentStatus = PaymentStatus.UNPAID;

    @Enumerated(EnumType.STRING)
    @Column(name = "shipping_status", nullable = false, length = 20)
    @Builder.Default
    private ShippingStatus shippingStatus = ShippingStatus.NOT_SHIPPED;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // ---------------------------------------------------------------
    // Relationships
    // ---------------------------------------------------------------

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private List<OrderItem> items = new ArrayList<>();

    // ---------------------------------------------------------------
    // Convenience helpers
    // ---------------------------------------------------------------

    /** Thêm item và cập nhật quan hệ 2 chiều. */
    public void addItem(OrderItem item) {
        items.add(item);
        item.setOrder(this);
    }

    /** Xóa item và cập nhật quan hệ 2 chiều. */
    public void removeItem(OrderItem item) {
        items.remove(item);
        item.setOrder(null);
    }
}
