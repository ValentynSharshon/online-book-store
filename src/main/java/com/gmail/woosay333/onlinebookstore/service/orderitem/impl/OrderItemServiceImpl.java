package com.gmail.woosay333.onlinebookstore.service.orderitem.impl;

import com.gmail.woosay333.onlinebookstore.dto.orderitem.OrderItemResponseDto;
import com.gmail.woosay333.onlinebookstore.entity.Book;
import com.gmail.woosay333.onlinebookstore.entity.Order;
import com.gmail.woosay333.onlinebookstore.entity.OrderItem;
import com.gmail.woosay333.onlinebookstore.exception.EntityNotFoundException;
import com.gmail.woosay333.onlinebookstore.mapper.OrderItemMapper;
import com.gmail.woosay333.onlinebookstore.repository.order.OrderRepository;
import com.gmail.woosay333.onlinebookstore.repository.orderitem.OrderItemRepository;
import com.gmail.woosay333.onlinebookstore.service.orderitem.OrderItemService;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final OrderItemMapper orderItemMapper;
    private final OrderRepository orderRepository;

    @Override
    public Set<OrderItem> save(Order order, Map<Book, Integer> bookFromCartItem) {
        return createOrderItems(order, bookFromCartItem);
    }

    @Override
    public List<OrderItemResponseDto> getAllOrderItems(Long orderId) {
        List<OrderItem> orderItemsFromDb = orderItemRepository.findByOrderId(orderId);
        if (orderItemsFromDb.isEmpty()) {
            throw new EntityNotFoundException(
                    String.format("Ca`t find order items for order with id: %d", orderId));
        }
        return orderItemsFromDb.stream()
                .map(orderItemMapper::toDto)
                .toList();
    }

    @Override
    public OrderItemResponseDto getItem(Long orderId, Long itemId) {
        boolean isOrderExists = orderRepository.existsById(orderId);
        if (!isOrderExists) {
            throw new EntityNotFoundException(
                    String.format("Can`t find order with id: %d", orderId));
        }
        Optional<OrderItem> orderItem = orderItemRepository
                .findByIdAndOrderId(itemId, orderId);
        return orderItem.map(orderItemMapper::toDto)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                String.format("Can`t find order item with id: %d", itemId)));
    }

    private Set<OrderItem> createOrderItems(Order order, Map<Book, Integer> bookFromCartItem) {
        return bookFromCartItem.entrySet().stream()
                .map(entry -> {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setBook(entry.getKey());
                    orderItem.setQuantity(entry.getValue());
                    orderItem.setOrder(order);
                    orderItem.setPrice(entry.getKey().getPrice());
                    return orderItemRepository.save(orderItem);
                })
                .collect(Collectors.toSet());
    }
}
