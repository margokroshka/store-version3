package com.example.petorderservice;

import com.example.petorderservice.controller.OrderController;
import com.example.petorderservice.model.Order;
import com.example.petorderservice.services.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class OrderControllerTest {
    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAllOrders() {
        List<Order> orders = new ArrayList<>();
        orders.add(new Order());
        orders.add(new Order());
        when(orderService.getAllOrders()).thenReturn(orders);
        ResponseEntity<List<Order>> responseEntity = orderController.getAllOrders();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(orders, responseEntity.getBody());
        verify(orderService, times(1)).getAllOrders();
    }

    @Test
    void testGetOrderById_OrderExists() {
        int id = 1;
        Order order = new Order();
        when(orderService.getOrderById(id)).thenReturn(order);
        ResponseEntity<Order> responseEntity = orderController.getOrderById(id);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(order, responseEntity.getBody());
        verify(orderService, times(1)).getOrderById(id);
    }

    @Test
    void testGetOrderById_OrderDoesNotExist() {
        int id = 1;
        when(orderService.getOrderById(id)).thenReturn(null);
        ResponseEntity<Order> responseEntity = orderController.getOrderById(id);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals(null, responseEntity.getBody());
        verify(orderService, times(1)).getOrderById(id);
    }

    @Test
    void testDeleteOrder_OrderExists() {
        int id = 1;
        when(orderService.deleteOrder(id)).thenReturn(true);
        ResponseEntity<Void> responseEntity = orderController.deleteOrder(id);
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(orderService, times(1)).deleteOrder(id);
    }

    @Test
    void testDeleteOrder_OrderDoesNotExist() {
        int id = 1;
        when(orderService.deleteOrder(id)).thenReturn(false);
        ResponseEntity<Void> responseEntity = orderController.deleteOrder(id);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        verify(orderService, times(1)).deleteOrder(id);
    }
}
