package com.example.bookstoreproject.service;

import com.example.bookstoreproject.dto.CartDTO;
import com.example.bookstoreproject.entity.BookData;
import com.example.bookstoreproject.entity.CartData;
import com.example.bookstoreproject.entity.UserRegistrationData;
import com.example.bookstoreproject.exception.BookStoreException;
import com.example.bookstoreproject.repository.BookRepository;
import com.example.bookstoreproject.repository.CartRepository;
import com.example.bookstoreproject.repository.UserRegistrationRepository;
import com.example.bookstoreproject.util.EmailSenderService;
import com.example.bookstoreproject.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CartService implements ICartService{
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private UserRegistrationRepository userRegistrationRepository;
    @Autowired
    private TokenUtil util;
    @Autowired
    private EmailSenderService mailService;


    @Override
    public String insert(CartDTO cartDTO) {
        Optional<BookData> bookData = bookRepository.findById(cartDTO.getBookId());
        Optional<UserRegistrationData> userRegistrationData = userRegistrationRepository.findById(cartDTO.getUserId());
        if (bookData.isPresent() && userRegistrationData.isPresent()) {
            CartData cartData = new CartData(userRegistrationData.get(), bookData.get(), cartDTO.getQuantity());
            cartRepository.save(cartData);
            String token = util.createToken(cartData.getCartId());
            return token;
        } else {
            throw new BookStoreException("Bookdata or userregistrationdata not found");
        }
    }


    @Override
    public List<CartData> getAllCart(String token) {
        int id = util.decodeToken(token);
        Optional<CartData> cartData = cartRepository.findById(id);
        if (cartData.isPresent()) {
            List<CartData> listOfCartdata = cartRepository.findAll();
            log.info("ALL cart records retrieved successfully");
            return listOfCartdata;
        } else {
            System.out.println("Exception ...Token not found!");
            return null;
        }
    }


    @Override
    public CartData getCartById(String token) {
        int id = util.decodeToken(token);
        Optional<CartData> cartData=cartRepository.findById(id);
        if (cartData.isPresent()){
            return cartData.get();
        }
        else {
            throw new BookStoreException(" Didn't find any record for this particular cartId");
        }
    }


    @Override
    public CartData updateCartById(String token, CartDTO cartDTO) {
        int id = util.decodeToken(token);
        Optional<CartData> cart = cartRepository.findById(id);
        Optional<BookData>  book = bookRepository.findById(cartDTO.getBookId());
        Optional<UserRegistrationData> user = userRegistrationRepository.findById(cartDTO.getUserId());
        if(cart.isPresent()) {
            if(book.isPresent() && user.isPresent()) {
                CartData cartData = new CartData(id, user.get(),book.get(), cartDTO.getQuantity());
                cartRepository.save(cartData);
                log.info("Cart record updated successfully for id "+id);
                return cartData;
            }
            else {
                throw new BookStoreException("Book or User doesn't exists");
            }
        }
        else {
            throw new BookStoreException("Cart Record doesn't exists");
        }
    }


    @Override
    public void deleteCartData(String token) {
        int id = util.decodeToken(token);
        Optional<CartData> deleteData=cartRepository.findById(id);
        if (deleteData.isPresent()){
            cartRepository.deleteById(id);
        }
        else {
            throw new BookStoreException(" Did not get any cart for specific cart id ");
        }
    }
}