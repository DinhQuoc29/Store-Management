package com.example.nhungtrinhstore.customer.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * DTO dùng để tạo mới hoặc cập nhật khách hàng.
 * Sử dụng Java Record để đảm bảo immutability.
 */
public record CustomerRequest(

    @NotBlank(message = "Họ tên khách hàng không được để trống")
    @Size(max = 150, message = "Họ tên không được vượt quá 150 ký tự")
    String fullName,

    @Size(max = 500, message = "URL Facebook không được vượt quá 500 ký tự")
    String facebookUrl,

    @Pattern(regexp = "^(\\+84|0)[0-9]{8,10}$", message = "Số điện thoại không hợp lệ (VD: 0901234567)")
    String phoneNumber,

    String address
) {}
