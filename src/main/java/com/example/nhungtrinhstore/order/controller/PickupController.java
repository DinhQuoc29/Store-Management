package com.example.nhungtrinhstore.order.controller;

import com.example.nhungtrinhstore.order.dto.PickupProductCard;
import com.example.nhungtrinhstore.order.service.PickupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST API chuyên biệt cho màn hình Pickup Checklist.
 *
 * Base URL: /api/pickup
 */
@RestController
@RequestMapping("/api/pickup")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PickupController {

    private final PickupService pickupService;

    /**
     * GET /api/pickup/checklist
     * Trả về danh sách sản phẩm cần nhặt, nhóm theo từng sản phẩm.
     *
     * <p>Mỗi card trong response chứa:
     *   - Tổng số cần nhặt vs đã nhặt (hiển thị trên header card)
     *   - Danh sách allocation theo từng khách hàng (hiển thị khi mở accordion)
     * </p>
     *
     * <p>Chỉ bao gồm các đơn chưa FULLY_PICKED.</p>
     */
    @GetMapping("/checklist")
    public ResponseEntity<List<PickupProductCard>> getChecklist() {
        return ResponseEntity.ok(pickupService.getPickupChecklist());
    }
}
