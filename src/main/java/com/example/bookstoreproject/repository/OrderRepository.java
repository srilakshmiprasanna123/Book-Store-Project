package com.example.bookstoreproject.repository;

import com.example.bookstoreproject.entity.OrderData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderData,Integer> {
}