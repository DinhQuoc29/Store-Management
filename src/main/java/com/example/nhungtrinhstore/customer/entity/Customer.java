package com.example.nhungtrinhstore.customer.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Thực thể Khách hàng.
 * Lưu trữ thông tin liên lạc của người đặt hàng xách tay Hàn Quốc.
 */
@Entity
@Table(name = "customers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name", nullable = false, length = 150)
    private String fullName;

    /** Link trang Facebook cá nhân, dùng để liên hệ & nhận đơn qua mạng xã hội. */
    @Column(name = "facebook_url", length = 500)
    private String facebookUrl;

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @Column(name = "address", columnDefinition = "TEXT")
    private String address;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // ---------------------------------------------------------------
    // Relationships
    // ---------------------------------------------------------------
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<com.example.nhungtrinhstore.order.entity.Order> orders = new ArrayList<>();
}
