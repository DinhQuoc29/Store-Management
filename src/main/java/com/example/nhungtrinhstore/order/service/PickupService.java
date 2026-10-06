package com.example.nhungtrinhstore.order.service;

import com.example.nhungtrinhstore.order.dto.PickupAllocationItem;
import com.example.nhungtrinhstore.order.dto.PickupProductCard;
import com.example.nhungtrinhstore.order.entity.OrderItem;
import com.example.nhungtrinhstore.order.repository.OrderItemRepository;
import com.example.nhungtrinhstore.product.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Service chuyên biệt cho màn hình Pickup Checklist.
 *
 * <p>Nghiệp vụ chính: Tổng hợp danh sách sản phẩm cần nhặt từ tất cả các đơn
 * chưa hoàn thành (FULLY_PICKED), nhóm theo sản phẩm để hiển thị accordion.</p>
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PickupService {

    private final OrderItemRepository orderItemRepository;

    /**
     * Trả về danh sách các PickupProductCard để hiển thị trên màn hình gom hàng.
     *
     * <p>Quy trình xử lý (tất cả trong Java, chỉ 1 DB query):
     * <ol>
     *   <li>Query tất cả OrderItem của đơn chưa FULLY_PICKED (kèm Product, Order, Customer)</li>
     *   <li>Nhóm theo productId bằng LinkedHashMap (giữ nguyên thứ tự từ DB)</li>
     *   <li>Với mỗi nhóm: tính tổng needed/picked và tạo danh sách allocations</li>
     * </ol>
     * </p>
     */
    public List<PickupProductCard> getPickupChecklist() {
        // 1. Một query duy nhất — xem OrderItemRepository.findAllPendingPickupItemsWithDetails()
        List<OrderItem> pendingItems = orderItemRepository.findAllPendingPickupItemsWithDetails();

        // 2. Nhóm theo productId, dùng LinkedHashMap để giữ thứ tự (đã sort theo category, code)
        Map<Long, List<OrderItem>> groupedByProduct = new LinkedHashMap<>();
        for (OrderItem item : pendingItems) {
            Long productId = item.getProduct().getId();
            groupedByProduct.computeIfAbsent(productId, k -> new ArrayList<>()).add(item);
        }

        // 3. Chuyển đổi mỗi nhóm thành PickupProductCard
        List<PickupProductCard> cards = new ArrayList<>();
        for (Map.Entry<Long, List<OrderItem>> entry : groupedByProduct.entrySet()) {
            List<OrderItem> items = entry.getValue();
            Product product = items.getFirst().getProduct(); // Lấy product từ item đầu tiên

            // Tổng hợp số liệu cấp sản phẩm
            int totalNeeded = items.stream().mapToInt(OrderItem::getQuantity).sum();
            int totalPicked = items.stream().mapToInt(OrderItem::getPickedQuantity).sum();

            // Tạo danh sách allocation theo từng khách hàng
            List<PickupAllocationItem> allocations = items.stream()
                .map(item -> new PickupAllocationItem(
                    item.getId(),
                    item.getOrder().getId(),
                    item.getOrder().getCustomer().getId(),
                    item.getOrder().getCustomer().getFullName(),
                    item.getOrder().getCustomer().getPhoneNumber(),
                    item.getQuantity(),
                    item.getPickedQuantity()
                ))
                .toList();

            cards.add(new PickupProductCard(
                product.getId(),
                product.getProductCode(),
                product.getProductName(),
                product.getImageUrl(),
                totalNeeded,
                totalPicked,
                allocations
            ));
        }

        return cards;
    }
}
