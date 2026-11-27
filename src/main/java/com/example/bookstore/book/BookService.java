package com.example.bookstore.book;

import com.example.bookstore.common.ResourceNotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BookService {
    private final BookRepository books;

    public BookService(BookRepository books) {
        this.books = books;
    }

    @Transactional(readOnly = true)
    public List<BookResponse> findAll() {
        return books.findAll().stream().map(BookResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public BookResponse findById(Long id) {
        return BookResponse.from(requireBook(id));
    }

    public BookResponse create(BookRequest request) {
        ensureIsbnAvailable(request.isbn(), null);
        Book book = new Book(request.title(), request.author(), request.isbn(), request.description(),
                request.price(), request.stock());
        return BookResponse.from(books.save(book));
    }

    public BookResponse update(Long id, BookRequest request) {
        Book book = requireBook(id);
        ensureIsbnAvailable(request.isbn(), id);
        book.update(request.title(), request.author(), request.isbn(), request.description(),
                request.price(), request.stock());
        return BookResponse.from(book);
    }

    public void delete(Long id) {
        books.delete(requireBook(id));
    }

    public Book requireBook(Long id) {
        return books.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book", id));
    }

    private void ensureIsbnAvailable(String isbn, Long currentBookId) {
        books.findAll().stream()
                .filter(book -> book.getIsbn().equals(isbn))
                .filter(book -> !book.getId().equals(currentBookId))
                .findFirst()
                .ifPresent(book -> { throw new IllegalArgumentException("ISBN is already in use: " + isbn); });
    }
}
