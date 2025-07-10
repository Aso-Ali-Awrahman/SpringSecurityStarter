package com.aso.springsecuritystarter.repositories;

import com.aso.springsecuritystarter.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {

}
