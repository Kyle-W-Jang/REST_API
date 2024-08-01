package com.RESTAPI.KJ.controllers;


import com.RESTAPI.KJ.models.Order;
import com.RESTAPI.KJ.models.TransactionType;

import com.RESTAPI.KJ.services.OrderServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    private MockMvc mockMvc;
    
    @MockBean
    private OrderServiceImpl orderServiceImpl;

    @InjectMocks
    private OrderController orderController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
    }

    @Test
    void createOrder() throws Exception {
        Order order = new Order(1L, LocalDateTime.now().toLocalDate(),
                100.0, "USD", TransactionType.Sale);

        when(orderServiceImpl.createOrder(any(Order.class))).thenReturn(order);

        mockMvc.perform(post("/v1/order/create")
                        .contentType(MediaType.APPLICATION_JSON)
                .content("{\"date\": \"2024-07-10\", \"amount\": 100.0, \"currencyCode\": \"USD\", \"transactionType\": \"Sale\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.amount").value(100.0));
    }

    @Test
    void testGetOrderById() throws Exception {

        Order order = new Order(1L, LocalDateTime.now().toLocalDate(),
                100.0, "USD", TransactionType.Sale);

        Mockito.when(orderServiceImpl.getOrderById(anyLong())).thenReturn(order);

        mockMvc.perform(get("/v1/order/1/get"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(order.getId()))
                .andExpect(jsonPath("$.amount").value(order.getAmount()));
    }

    @Test
    void testUpdateOrder() throws Exception {
        Order order = new Order(1L, LocalDateTime.now().toLocalDate(),
                150.0, "USD", TransactionType.Sale);

        Mockito.when(orderServiceImpl.updateOrder(anyLong(), any(Order.class))).thenReturn(order);

        mockMvc.perform(put("/v1/order/1/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"date\":\"2024-07-10\",\"amount\":150.00,\"currencyCode\":\"USD\",\"transactionType\":\"Sale\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.amount").value(150.0));

    }

    @Test
    void deleteOrder() throws Exception {

        mockMvc.perform(delete("/v1/order/1/delete"))
                .andExpect(status().isNoContent());
    }

    @Test
    void getAllOrders() throws Exception {

        Order order1 = new Order(1L, LocalDateTime.now().toLocalDate(),
                100.0, "USD", TransactionType.Sale);

        Order order2 = new Order(2L, LocalDateTime.now().toLocalDate(),
                150.0, "CAD", TransactionType.Sale);

        List<Order> orders = Arrays.asList(order1, order2);

        when(orderServiceImpl.getAllOrders()).thenReturn(orders);

        mockMvc.perform(get("/v1/order/listAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[0].currencyCode").value("USD"))
                .andExpect(jsonPath("$[1].currencyCode").value("CAD"));

    }


}
