package com.aso.springsecuritystarter.services;

import com.aso.springsecuritystarter.entities.Book;
import com.aso.springsecuritystarter.repositories.BookRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getBookByTitle(String title) {
        return bookRepository.findBookByTitleLikeIgnoreCase(title);
    }

    public List<Book> getVipBooks() {
        return bookRepository.findVipBooks();
    }

    public List<Book> getNonVipBooks() {
        return bookRepository.findNonVipBooks();
    }

    public void createBook(Book book, boolean isVip) {
        if (book.getTitle() == null)
            throw new IllegalArgumentException("Title is required");
        if (book.getPrice() <= 0)
            throw new IllegalArgumentException("Price must be positive");

        bookRepository.save(new Book(
                null,
                book.getTitle(),
                book.getPrice(),
                isVip
        ));
    }

}
