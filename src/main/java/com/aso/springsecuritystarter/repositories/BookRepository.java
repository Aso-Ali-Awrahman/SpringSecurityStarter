package com.aso.springsecuritystarter.repositories;

import com.aso.springsecuritystarter.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    @Query("SELECT b FROM Book b WHERE b.title LIKE %:title")
    List<Book> findBookByTitle(@Param("title") String title);

    @Query("select b from Book b where b.isVip = true")
    List<Book> findVipBooks();

    @Query("select b from Book b where b.isVip != true")
    List<Book> findNonVipBooks();
}
