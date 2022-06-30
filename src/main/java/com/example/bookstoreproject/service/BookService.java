package com.example.bookstoreproject.service;

import com.example.bookstoreproject.dto.BookDTO;
import com.example.bookstoreproject.entity.BookData;
import com.example.bookstoreproject.exception.BookStoreException;
import com.example.bookstoreproject.repository.BookRepository;
import com.example.bookstoreproject.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService implements IBookService{
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private TokenUtil util;


    @Override
    public String insert(BookDTO bookDTO) {
        BookData bookData=new BookData(bookDTO);
        bookRepository.save(bookData);
        String token = util.createToken(bookData.getBookId());
        return token;
    }


    @Override
    public List<BookData> getAllBooks() {
//
//        Optional<BookData> bookData=bookRepository.findById(id);
//        if (bookData.isPresent()){
            List<BookData> listOfBooks=bookRepository.findAll();
            return listOfBooks;
//        }else {
//            System.out.println("Exception ...Token not found!");
//            return null;
//        }
    }

//    @Override
//    public Integer getBooksById() {
//        return null;
//    }


    @Override
    public Optional<BookData> getBooksById(int id) {
//        int id=util.decodeToken(token);
        Optional<BookData> bookData = bookRepository.findById(id);
        if (bookData.isPresent()){
            return bookData;
        }else {
            throw new BookStoreException("Exception with id"+id+"does not exist!!");
        }
    }


    @Override
    public List<BookData> getBooksByName(String bookName) {
        List<BookData> findBook=bookRepository.findBookByName(bookName);
        if(findBook.isEmpty()){
            throw new BookStoreException(" Details for provided Book is not found");
        }
        return findBook;
    }


    @Override
    public List<BookData> getBooksByAutherName(String autherName) {
        List<BookData> findBook=bookRepository.findBookByAutherName(autherName);
        if(findBook.isEmpty()){
            throw new BookStoreException(" Book auther name is not found");
        }
        return findBook;
    }


    @Override
    public BookData updateBooksById(int id, BookDTO bookDTO) {
//        int id=util.decodeToken(id);
        Optional<BookData> bookData=bookRepository.findById(id);
        if (bookData.isPresent()){
            BookData updateData=new BookData(id,bookDTO);
            bookRepository.save(updateData);
            return updateData;
        }else {
            throw new BookStoreException("Bookdata record does not found");
        }
    }


    @Override
    public BookData updataBooksByQuantity(String token, int quantity) {
        int id=util.decodeToken(token);
        Optional<BookData> bookData=bookRepository.findById(id);
        if (bookData.isPresent()){
            BookData bookData1=new BookData();
            bookData1.setQuantity(quantity);
            bookRepository.save(bookData1);
            return bookData1;
        }else {
            throw new BookStoreException("Bookdata record does not found");
        }
    }


    @Override
    public List<BookData> sortBookDataAsc() {
        return bookRepository.sortBookDataAsc();
    }


    @Override
    public List<BookData> sortBookDataDesc() {
        return bookRepository.sortBookDataDesc();
    }

//    @Override
//    public void deletebookData(String token) {
//
//    }


    @Override
    public void deletebookData(int id) {
//        int id=util.decodeToken(token);
        Optional<BookData> bookData =bookRepository.findById(id);
        if (bookData.isPresent()) {
            bookRepository.deleteById(id);
        } else {
            throw new BookStoreException("Book record does not found");
        }
    }
}
