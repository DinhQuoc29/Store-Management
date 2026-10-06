package com.example.nhungtrinhstore.order.mapper;

import com.example.nhungtrinhstore.order.dto.*;
import com.example.nhungtrinhstore.order.entity.Order;
import com.example.nhungtrinhstore.order.entity.OrderItem;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Mapper thủ công cho Order và OrderItem.
 */
@Component
public class OrderMapper {

    // ---------------------------------------------------------------
    // OrderItem mapping
    // ---------------------------------------------------------------

    /** OrderItem entity -> OrderItemResponse DTO */
    public OrderItemResponse toItemResponse(OrderItem item) {
        var product = item.getProduct();
        return new OrderItemResponse(
            item.getId(),
            product.getId(),
            product.getProductCode(),
            product.getProductName(),
            product.getImageUrl(),
            item.getQuantity(),
            item.getPickedQuantity(),
            item.getShippedQuantity(),
            item.getUnitPrice(),
            item.getLineTotal()
        );
    }

    // ---------------------------------------------------------------
    // Order mapping
    // ---------------------------------------------------------------

    /** Order entity -> OrderResponse DTO (dùng cho detail view, bao gồm items) */
    public OrderResponse toResponse(Order order) {
        List<OrderItemResponse> itemResponses = order.getItems().stream()
            .map(this::toItemResponse)
            .toList();

        var customer = order.getCustomer();
        return new OrderResponse(
            order.getId(),
            customer.getId(),
            customer.getFullName(),
            customer.getPhoneNumber(),
            customer.getAddress(),
            customer.getFacebookUrl(),
            order.getOrderDate(),
            order.getNote(),
            order.getTotalAmount(),
            order.getDepositAmount(),
            order.getRemainingAmount(),
            order.getPickupStatus(),
            order.getPaymentStatus(),
            order.getShippingStatus(),
            order.getCreatedAt(),
            itemResponses
        );
    }

    /** Order entity -> OrderSummaryResponse DTO (dùng cho list view, không có items) */
    public OrderSummaryResponse toSummaryResponse(Order order) {
        var customer = order.getCustomer();
        var items = order.getItems();
        int totalQuantity      = items.stream().mapToInt(i -> i.getQuantity() != null ? i.getQuantity() : 0).sum();
        int totalPickedQuantity = items.stream().mapToInt(i -> i.getPickedQuantity() != null ? i.getPickedQuantity() : 0).sum();
        return new OrderSummaryResponse(
            order.getId(),
            customer.getId(),
            customer.getFullName(),
            customer.getPhoneNumber(),
            order.getOrderDate(),
            order.getNote(),
            order.getTotalAmount(),
            order.getDepositAmount(),
            order.getRemainingAmount(),
            order.getPickupStatus(),
            order.getPaymentStatus(),
            order.getShippingStatus(),
            items.size(),
            totalQuantity,
            totalPickedQuantity
        );
    }
}
