package com.example.petorderservice.services;

import com.example.petorderservice.model.Order;
import com.example.petorderservice.model.OrderLineItems;
import com.example.petorderservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final RestTemplate restTemplate;

    @Autowired
    public OrderService(OrderRepository orderRepository, RestTemplate restTemplate) {
        this.orderRepository = orderRepository;
        this.restTemplate = restTemplate;
    }

   /* public Order createOrder(List<OrderLineItems> orderLineItemsList) {
        Order order = new Order();
        order.setOrder_number(UUID.randomUUID().toString());
        order.setOrder_line_items_list(orderLineItemsList);

        if (checkInventory(orderLineItemsList)) {
            return saveOrder(order);
        } else {
            throw new IllegalArgumentException("Not enough inventory for one or more products.");
        }
    }*/
    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Integer id) {
        Optional<Order> order = orderRepository.findById(id);
        return order.orElse(null);
    }

    /*public Order updateOrder(Integer id, List<OrderLineItems> orderLineItemsList) {
        Optional<Order> existingOrderOptional = orderRepository.findById(id);
        if (existingOrderOptional.isPresent()) {
            Order existingOrder = existingOrderOptional.get();
            existingOrder.setOrder_line_items_list(orderLineItemsList);
            return saveOrder(existingOrder);
        } else {
            throw new IllegalArgumentException("Order not found");
        }
    }*/

    public boolean deleteOrder(Integer id) {
        Optional<Order> existingOrderOptional = orderRepository.findById(id);
        if (existingOrderOptional.isPresent()) {
            orderRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

   /* public boolean checkInventory(List<OrderLineItems> orderLineItemsList) {
        for (OrderLineItems item : orderLineItemsList) {
            Boolean isAvailable = restTemplate.getForObject(
                    "http://inventory-service/api/inventory/check/{skuCode}",
                    Boolean.class,
                    item.getSkuCode()
            );

            if (isAvailable == null || !isAvailable) {
                return false;
            }
        }
        return true;
    }*/
}
