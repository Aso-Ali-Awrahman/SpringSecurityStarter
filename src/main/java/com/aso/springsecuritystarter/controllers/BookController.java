package com.aso.springsecuritystarter.controllers;

import com.aso.springsecuritystarter.dtos.BookDto;
import com.aso.springsecuritystarter.entities.Book;
import com.aso.springsecuritystarter.mappers.BookMapper;
import com.aso.springsecuritystarter.services.BookService;
import org.hibernate.boot.model.naming.IllegalIdentifierException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final BookMapper bookMapper;

    public BookController(BookService bookService, BookMapper bookMapper) {
        this.bookService = bookService;
        this.bookMapper = bookMapper;
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok(bookService.getBooks());
    }

    @GetMapping("/{title}")
    public ResponseEntity<List<BookDto>> searchBook(@PathVariable("title") String title) {
        return ResponseEntity.ok(
                bookService.getBookByTitle(title).stream()
                        .map(bookMapper::toDto).toList()
        );
    }

    @GetMapping("/vip")
    public ResponseEntity<List<BookDto>> getVipBooks() {
        return ResponseEntity.ok(
                bookService.getVipBooks().stream()
                        .map(bookMapper::toDto).toList()
        );
    }

    @GetMapping("/normal")
    public ResponseEntity<List<BookDto>> getNonVipBooks() {
        return ResponseEntity.ok(
                bookService.getNonVipBooks().stream()
                        .map(bookMapper::toDto).toList()
        );
    }

    @PostMapping("/{isVip}")
    public ResponseEntity<Void> addBook(@PathVariable("isVip") boolean isVip, @RequestBody BookDto bookDto) {
        bookService.createBook(bookMapper.toEntity(bookDto), isVip);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @ExceptionHandler(IllegalIdentifierException.class)
    public ResponseEntity<Map<String, String>> handleIllegalIdentifierException(IllegalIdentifierException ex) {
        return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
    }

}
