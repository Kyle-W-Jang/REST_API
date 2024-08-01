package com.RESTAPI.KJ.services;

import java.util.List;

public interface CrudService<T> {

    T createOrder(T order);

    T getOrderById(Long id);

    T updateOrder(Long id, T orderDetails);

    void deleteOrder(Long id);

    List<T> getAllOrders();
}
