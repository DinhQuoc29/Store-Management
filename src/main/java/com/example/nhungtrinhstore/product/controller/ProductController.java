package com.example.nhungtrinhstore.product.controller;

import com.example.nhungtrinhstore.common.dto.PageResponse;
import com.example.nhungtrinhstore.product.dto.ProductRequest;
import com.example.nhungtrinhstore.product.dto.ProductResponse;
import com.example.nhungtrinhstore.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST API cho quản lý Sản phẩm.
 *
 * Base URL: /api/products
 */
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ProductController {

    private final ProductService productService;

    /** POST /api/products — Tạo mới sản phẩm. */
    @PostMapping
    public ResponseEntity<ProductResponse> create(@Valid @RequestBody ProductRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.create(request));
    }

    /** GET /api/products/{id} — Lấy thông tin một sản phẩm. */
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getById(id));
    }

    /** GET /api/products?keyword=...&page=0&size=20 — Tìm kiếm sản phẩm có phân trang. */
    @GetMapping
    public ResponseEntity<PageResponse<ProductResponse>> search(
        @RequestParam(required = false) String keyword,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "20") int size
    ) {
        return ResponseEntity.ok(productService.search(keyword, page, size));
    }

    /** GET /api/products/all — Lấy toàn bộ danh sách (dùng cho dropdown). */
    @GetMapping("/all")
    public ResponseEntity<List<ProductResponse>> getAll() {
        return ResponseEntity.ok(productService.getAll());
    }

    /** PUT /api/products/{id} — Cập nhật sản phẩm. */
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> update(
        @PathVariable Long id,
        @Valid @RequestBody ProductRequest request
    ) {
        return ResponseEntity.ok(productService.update(id, request));
    }

    /** DELETE /api/products/{id} — Xóa sản phẩm. */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
