package com.RESTAPI.KJ.services;

import com.RESTAPI.KJ.configs.CurrencyConfig;
import com.RESTAPI.KJ.exceptions.*;
import com.RESTAPI.KJ.models.Order;
import com.RESTAPI.KJ.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements CrudService<Order> {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    CurrencyConfig currencyConfig;

    @Override
    @Transactional
    public Order createOrder(Order order) {
        //Making sure Order uses the proper amount
        order.setAmount(validateAndRoundAmount(order.getAmount()));
        //Making sure Currency code is a valid ISO-4217 currency code
        validateCurrencyCode(order.getCurrencyCode());

        //If I see an order that happened at the same date with the same amount, currency_code, and transaction_type
        //I don't process an order
        Optional<Order> existingOrder = orderRepository.findByDateAndAmountAndCurrencyCodeAndTransactionType(
                order.getDate(), order.getAmount(), order.getCurrencyCode(), order.getTransactionType());

        if (existingOrder.isPresent()) {
            throw new OrderAlreadyExistsException("Order already exists with the same information");
        }

        return orderRepository.save(order);
    }

    @Override
    @Transactional(readOnly = true)
    public Order getOrderById(Long id) {

        if(id == null || id.compareTo(0L) <= 0){
            throw new InvalidIdException("Invalid Id: " + id);
        }

        Optional<Order> order = orderRepository.findById(id);
        if (order.isPresent()) {
            return order.get();
        } else {
            throw new OrderNotFoundException("Order not found with id " + id);
        }
    }

    @Override
    @Transactional
    public Order updateOrder(Long id, Order updatedOrderDetails) {
        //Check if order exists first or in controller
        Order order = getOrderById(id);

        //Making sure Order updates to the proper amount
        updatedOrderDetails.setAmount(validateAndRoundAmount(updatedOrderDetails.getAmount()));

        //Making sure Currency code is a valid ISO-4217 currency code
        validateCurrencyCode(updatedOrderDetails.getCurrencyCode());

        order.setDate(updatedOrderDetails.getDate());
        order.setAmount(updatedOrderDetails.getAmount());
        order.setCurrencyCode(updatedOrderDetails.getCurrencyCode());
        order.setTransactionType(updatedOrderDetails.getTransactionType());
        return orderRepository.save(order);
    }

    @Override
    @Transactional
    public void deleteOrder(Long id) {
        //Check if order exists first
        Order order = getOrderById(id);
        orderRepository.delete(order);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    private Double validateAndRoundAmount(Double amount) {
        if (amount == null || amount.compareTo(0.0) <= 0) {
            throw new InvalidAmountException("Invalid Amount To Process " + amount);
        }

        BigDecimal bigDecimalAmount = BigDecimal.valueOf(amount);
        bigDecimalAmount = bigDecimalAmount.setScale(2, RoundingMode.UP);
        return bigDecimalAmount.doubleValue();
    }

    private void validateCurrencyCode(String currencyCode) {
        if (!currencyConfig.getValidCurrencies().contains(currencyCode)) {
            throw new InvalidCurrencyCodeException("Invalid ISO_4217 currency code: " + currencyCode);
        }
    }

}
