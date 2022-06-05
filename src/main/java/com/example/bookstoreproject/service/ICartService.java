package com.example.bookstoreproject.service;

import com.example.bookstoreproject.dto.CartDTO;
import com.example.bookstoreproject.entity.CartData;

import java.util.List;

public interface ICartService {
    String insert(CartDTO cartDTO);
    List<CartData> getAllCart(String token);
    CartData getCartById(String token);
    CartData updateCartById(String token,CartDTO cartDTO);
    void deleteCartData(String token);
}