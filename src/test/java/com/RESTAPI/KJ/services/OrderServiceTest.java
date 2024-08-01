package com.RESTAPI.KJ.services;

import com.RESTAPI.KJ.configs.CurrencyConfig;
import com.RESTAPI.KJ.models.Order;
import com.RESTAPI.KJ.models.TransactionType;
import com.RESTAPI.KJ.repositories.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private CurrencyConfig currencyConfig;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void createOrder() {
        Order order = new Order(1L, LocalDateTime.now().toLocalDate(),
                100.0, "USD", TransactionType.Sale);

        when(orderRepository.save(any(Order.class))).thenReturn(order);
        when(orderRepository.findByDateAndAmountAndCurrencyCodeAndTransactionType(
                any(LocalDate.class), anyDouble(), anyString(), any(TransactionType.class))).thenReturn(Optional.empty());

        when(currencyConfig.getValidCurrencies()).thenReturn(Collections.singleton("USD"));

        Order createdOrder = orderService.createOrder(order);

        assertNotNull(createdOrder);
        assertEquals(1L, createdOrder.getId());
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void getOrderById() {
        Order order = new Order(1L, LocalDateTime.now().toLocalDate(),
                100.0, "USD", TransactionType.Sale);

        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));

        Order foundOrder = orderService.getOrderById(1L);

        assertNotNull(foundOrder);
        assertEquals(1L, foundOrder.getId());
    }

    @Test
    void updateOrder() {
        Order existingOrder = new Order(1L, LocalDateTime.now().toLocalDate(),
                100.0, "USD", TransactionType.Sale);

        Order updatedOrder = new Order(1L, LocalDateTime.now().toLocalDate(),
                200.0, "USD", TransactionType.Sale);

        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(existingOrder));
        when(orderRepository.save(any(Order.class))).thenReturn(updatedOrder);

        when(currencyConfig.getValidCurrencies()).thenReturn(Collections.singleton("USD"));

        Order result = orderService.updateOrder(1L, updatedOrder);

        assertNotNull(result);
        assertEquals(200.0, result.getAmount());
    }

    @Test
    void deleteOrder() {
        Order order = new Order(1L, LocalDateTime.now().toLocalDate(),
                100.0, "USD", TransactionType.Sale);

        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));

        orderService.deleteOrder(1L);

        verify(orderRepository, times(1)).delete(any(Order.class));
    }

    @Test
    void getAllOrders() {
        Order order1 = new Order(1L, LocalDateTime.now().toLocalDate(),
                100.0, "USD", TransactionType.Sale);

        Order order2 = new Order(2L, LocalDateTime.now().toLocalDate(),
                200.0, "CAD", TransactionType.Sale);

        List<Order> orders = Arrays.asList(order1, order2);

        when(orderRepository.findAll()).thenReturn(orders);

        List<Order> foundOrders = orderService.getAllOrders();

        assertNotNull(foundOrders);
        assertEquals(2, foundOrders.size());
        assertEquals(1L, foundOrders.get(0).getId());
        assertEquals(2L, foundOrders.get(1).getId());
        assertEquals("USD", foundOrders.get(0).getCurrencyCode());
        assertEquals("CAD", foundOrders.get(1).getCurrencyCode());
    }

}
