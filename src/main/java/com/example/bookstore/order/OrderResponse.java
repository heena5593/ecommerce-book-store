package com.example.bookstore.order;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record OrderResponse(Long id, String customerName, String customerEmail, Instant createdAt,
                            BigDecimal totalAmount, List<Item> items) {
    public record Item(String title, int quantity, BigDecimal unitPrice, BigDecimal lineTotal) {
        static Item from(OrderItem item) {
            BigDecimal lineTotal = item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
            return new Item(item.getBookTitle(), item.getQuantity(), item.getUnitPrice(), lineTotal);
        }
    }

    public static OrderResponse from(PurchaseOrder order) {
        return new OrderResponse(order.getId(), order.getCustomerName(), order.getCustomerEmail(),
                order.getCreatedAt(), order.getTotalAmount(), order.getItems().stream().map(Item::from).toList());
    }
}
