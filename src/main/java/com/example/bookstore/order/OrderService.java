package com.example.bookstore.order;

import com.example.bookstore.book.Book;
import com.example.bookstore.book.BookService;
import com.example.bookstore.common.ResourceNotFoundException;
import java.math.BigDecimal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrderService {
    private final OrderRepository orders;
    private final BookService books;

    public OrderService(OrderRepository orders, BookService books) {
        this.orders = orders;
        this.books = books;
    }

    public OrderResponse checkout(CheckoutRequest request) {
        PurchaseOrder order = new PurchaseOrder(request.customerName(), request.customerEmail());
        BigDecimal total = BigDecimal.ZERO;

        for (CheckoutRequest.Item requestedItem : request.items()) {
            Book book = books.requireBook(requestedItem.bookId());
            if (book.getStock() < requestedItem.quantity()) {
                throw new IllegalArgumentException("Insufficient stock for book: " + book.getTitle());
            }
            book.reduceStock(requestedItem.quantity());
            order.addItem(new OrderItem(order, book, requestedItem.quantity()));
            total = total.add(book.getPrice().multiply(BigDecimal.valueOf(requestedItem.quantity())));
        }

        order.setTotalAmount(total);
        return OrderResponse.from(orders.save(order));
    }

    @Transactional(readOnly = true)
    public OrderResponse findById(Long id) {
        PurchaseOrder order = orders.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order", id));
        return OrderResponse.from(order);
    }
}
