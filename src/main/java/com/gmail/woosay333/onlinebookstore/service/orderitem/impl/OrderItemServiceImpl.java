package com.gmail.woosay333.onlinebookstore.service.orderitem.impl;

import com.gmail.woosay333.onlinebookstore.dto.orderitem.OrderItemResponseDto;
import com.gmail.woosay333.onlinebookstore.entity.CartItem;
import com.gmail.woosay333.onlinebookstore.entity.OrderItem;
import com.gmail.woosay333.onlinebookstore.entity.User;
import com.gmail.woosay333.onlinebookstore.exception.EntityNotFoundException;
import com.gmail.woosay333.onlinebookstore.mapper.OrderItemMapper;
import com.gmail.woosay333.onlinebookstore.repository.orderitem.OrderItemRepository;
import com.gmail.woosay333.onlinebookstore.service.orderitem.OrderItemService;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final OrderItemMapper orderItemMapper;

    @Override
    public Set<OrderItem> convertFrom(Set<CartItem> cartItems) {
        return cartItems.stream()
                .map(orderItemMapper::toModel)
                .collect(Collectors.toSet());
    }

    @Override
    public List<OrderItemResponseDto> findAllByOrder(Long orderId, User user, Pageable pageable) {
        List<OrderItem> orderItems = orderItemRepository
                .findAllByOrder_IdAndOrder_User(orderId, user, pageable);
        if (orderItems.isEmpty()) {
            throw new EntityNotFoundException(
                    String.format("Can`t find order by id: %d",
                            orderId));
        }
        return orderItems.stream()
                .map(orderItemMapper::toDto)
                .toList();
    }

    @Override
    public OrderItemResponseDto getById(Long id, Long orderId, User user) {
        OrderItem orderItem = orderItemRepository
                .findByIdAndOrder_IdAndOrder_User(id, orderId, user)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Can`t find order by id: %d and order item by id: %d",
                                orderId,
                                id)));
        return orderItemMapper.toDto(orderItem);
    }
}
