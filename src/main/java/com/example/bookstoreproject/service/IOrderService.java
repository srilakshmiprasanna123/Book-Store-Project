package com.example.bookstoreproject.service;


import com.example.bookstoreproject.dto.OrderDTO;
import com.example.bookstoreproject.entity.OrderData;

import java.util.List;

public interface IOrderService {
    String insert(OrderDTO orderDTO);
    List<OrderData> getAllOrder(String token);
    OrderData getOrderById(String token);
    OrderData cancelOrderById(String token, int userId);
}