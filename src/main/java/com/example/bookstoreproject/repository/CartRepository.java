package com.example.bookstoreproject.repository;

import com.example.bookstoreproject.entity.CartData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<CartData,Integer> {
}