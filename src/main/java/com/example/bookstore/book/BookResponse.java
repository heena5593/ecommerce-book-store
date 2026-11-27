package com.example.bookstore.book;

import java.math.BigDecimal;

public record BookResponse(Long id, String title, String author, String isbn,
                           String description, BigDecimal price, int stock) {
    public static BookResponse from(Book book) {
        return new BookResponse(book.getId(), book.getTitle(), book.getAuthor(), book.getIsbn(),
                book.getDescription(), book.getPrice(), book.getStock());
    }
}
