package com.gmail.woosay333.onlinebookstore.service.order.impl;

import com.gmail.woosay333.onlinebookstore.dto.order.OrderRequestDto;
import com.gmail.woosay333.onlinebookstore.dto.order.OrderResponseDto;
import com.gmail.woosay333.onlinebookstore.dto.orderitem.OrderItemResponseDto;
import com.gmail.woosay333.onlinebookstore.dto.status.StatusDto;
import com.gmail.woosay333.onlinebookstore.entity.CartItem;
import com.gmail.woosay333.onlinebookstore.entity.Order;
import com.gmail.woosay333.onlinebookstore.entity.ShoppingCart;
import com.gmail.woosay333.onlinebookstore.entity.User;
import com.gmail.woosay333.onlinebookstore.exception.EntityNotFoundException;
import com.gmail.woosay333.onlinebookstore.mapper.OrderMapper;
import com.gmail.woosay333.onlinebookstore.repository.order.OrderRepository;
import com.gmail.woosay333.onlinebookstore.repository.shopping.cart.ShoppingCartRepository;
import com.gmail.woosay333.onlinebookstore.service.order.OrderService;
import com.gmail.woosay333.onlinebookstore.service.orderitem.OrderItemService;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final OrderMapper orderMapper;
    private final OrderItemService orderItemService;

    @Override
    @Transactional
    public OrderResponseDto saveOrder(OrderRequestDto requestDto, User user) {
        ShoppingCart shoppingCart =
                shoppingCartRepository.findByUserWithCartItems(user).orElseThrow(
                        () -> new EntityNotFoundException(
                                "Can't find shopping cart from user: " + user.getEmail()));
        Set<CartItem> cartItems = shoppingCart.getCartItems();
        if (cartItems.isEmpty()) {
            throw new EntityNotFoundException("Can't find cartItems from user: " + user.getEmail());
        }

        shoppingCartRepository.deleteById(shoppingCart.getId());

        Order order =
                orderMapper.toModel(requestDto, user, orderItemService.convertFrom(cartItems));
        Order savedOrder = orderRepository.save(order);
        return orderMapper.toDto(savedOrder);
    }

    @Override
    @Transactional
    public List<OrderResponseDto> getAllOrders(User user, Pageable pageable) {
        return orderRepository.findAllByUser(user, pageable).stream()
                .map(orderMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public void updateStatus(Long id, StatusDto statusDto) {
        if (orderRepository.updateStatusById(id, statusDto.status()) < 1) {
            throw new EntityNotFoundException("Can't find order by id: " + id);
        }
    }

    @Override
    @Transactional
    public List<OrderItemResponseDto> getAllCartItems(Long orderId, User user, Pageable pageable) {
        return orderItemService.findAllByOrder(orderId, user, pageable);
    }

    @Override
    @Transactional
    public OrderItemResponseDto getCartItem(Long id, Long orderId, User user) {
        return orderItemService.getById(id, orderId, user);
    }
}
