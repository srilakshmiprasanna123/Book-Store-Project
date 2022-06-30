package com.example.bookstoreproject.service;

import com.example.bookstoreproject.dto.BookDTO;
import com.example.bookstoreproject.entity.BookData;

import java.util.List;
import java.util.Optional;

public interface IBookService {
    String insert(BookDTO bookDTO);
    List<BookData> getAllBooks();
    Optional<BookData> getBooksById(int id);
    List<BookData> getBooksByName(String bookName);
    List<BookData> getBooksByAutherName(String autherName);
    BookData updateBooksById(int id,BookDTO bookDTO);
    BookData updataBooksByQuantity(String token,int quantity);
    List<BookData> sortBookDataAsc();
    List<BookData> sortBookDataDesc();
    void deletebookData(int id);
}
