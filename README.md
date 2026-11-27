# E-commerce Book Store

A Java 21 and Spring Boot REST API for managing a bookstore catalog and placing orders. The application uses Spring Data JPA and an in-memory H2 database for local development.

## Requirements

- JDK 21
- Maven 3.6+ (or use the Maven wrapper if one is added)

## Run

```bash
mvn spring-boot:run
```

The API starts at `http://localhost:8080`. Run tests with `mvn test`.

## API

| Method | Endpoint | Purpose |
| --- | --- | --- |
| GET | `/api/books` | List books |
| GET | `/api/books/{id}` | Get a book |
| POST | `/api/books` | Add a book |
| PUT | `/api/books/{id}` | Update a book |
| DELETE | `/api/books/{id}` | Delete a book |
| POST | `/api/orders` | Place an order and decrement stock |
| GET | `/api/orders/{id}` | Retrieve an order |

Create a book with JSON such as:

```json
{
  "title": "The Pragmatic Programmer",
  "author": "David Thomas and Andrew Hunt",
  "isbn": "978-0135957059",
  "description": "A guide to pragmatic software development.",
  "price": 42.00,
  "stock": 12
}
```

Place an order with JSON such as:

```json
{
  "customerName": "Alex Reader",
  "customerEmail": "alex@example.com",
  "items": [
    { "bookId": 1, "quantity": 2 }
  ]
}
```

H2 is in-memory, so data resets when the application stops. The H2 console is available at `/h2-console` during local development.
