package com.aso.springsecuritystarter.mappers;

import com.aso.springsecuritystarter.dtos.BookDto;
import com.aso.springsecuritystarter.entities.Book;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {

    public BookDto toDto(Book book) {
        return new BookDto(
                book.getTitle(),
                book.getPrice()
        );
    }

    public Book toEntity(BookDto bookDto) {
        return new Book(
                null,
                bookDto.title(),
                bookDto.price(),
                false
        );
    }

}
