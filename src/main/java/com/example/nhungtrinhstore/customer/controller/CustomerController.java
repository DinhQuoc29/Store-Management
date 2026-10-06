package com.example.nhungtrinhstore.customer.controller;

import com.example.nhungtrinhstore.common.dto.PageResponse;
import com.example.nhungtrinhstore.customer.dto.CustomerRequest;
import com.example.nhungtrinhstore.customer.dto.CustomerResponse;
import com.example.nhungtrinhstore.customer.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST API cho quản lý Khách hàng.
 *
 * Base URL: /api/customers
 */
@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")   // Cho phép Angular dev server gọi (sẽ tinh chỉnh ở prod)
public class CustomerController {

    private final CustomerService customerService;

    /**
     * POST /api/customers
     * Tạo mới khách hàng.
     */
    @PostMapping
    public ResponseEntity<CustomerResponse> create(@Valid @RequestBody CustomerRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.create(request));
    }

    /**
     * GET /api/customers/{id}
     * Lấy thông tin một khách hàng theo ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(customerService.getById(id));
    }

    /**
     * GET /api/customers?keyword=...&page=0&size=20
     * Tìm kiếm khách hàng với phân trang.
     */
    @GetMapping
    public ResponseEntity<PageResponse<CustomerResponse>> search(
        @RequestParam(required = false) String keyword,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "20") int size
    ) {
        return ResponseEntity.ok(customerService.search(keyword, page, size));
    }

    /**
     * GET /api/customers/all
     * Lấy toàn bộ danh sách khách hàng (dùng cho dropdown).
     */
    @GetMapping("/all")
    public ResponseEntity<List<CustomerResponse>> getAll() {
        return ResponseEntity.ok(customerService.getAll());
    }

    /**
     * PUT /api/customers/{id}
     * Cập nhật toàn bộ thông tin khách hàng.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponse> update(
        @PathVariable Long id,
        @Valid @RequestBody CustomerRequest request
    ) {
        return ResponseEntity.ok(customerService.update(id, request));
    }

    /**
     * DELETE /api/customers/{id}
     * Xóa khách hàng.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        customerService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
