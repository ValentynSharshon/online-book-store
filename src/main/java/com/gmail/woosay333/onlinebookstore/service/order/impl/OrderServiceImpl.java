package com.gmail.woosay333.onlinebookstore.service.order.impl;

import com.gmail.woosay333.onlinebookstore.dto.order.OrderRequestDto;
import com.gmail.woosay333.onlinebookstore.dto.order.OrderResponseDto;
import com.gmail.woosay333.onlinebookstore.dto.status.OrderStatusRequestDto;
import com.gmail.woosay333.onlinebookstore.entity.Book;
import com.gmail.woosay333.onlinebookstore.entity.CartItem;
import com.gmail.woosay333.onlinebookstore.entity.Order;
import com.gmail.woosay333.onlinebookstore.entity.OrderItem;
import com.gmail.woosay333.onlinebookstore.entity.ShoppingCart;
import com.gmail.woosay333.onlinebookstore.entity.Status;
import com.gmail.woosay333.onlinebookstore.entity.User;
import com.gmail.woosay333.onlinebookstore.exception.EntityNotFoundException;
import com.gmail.woosay333.onlinebookstore.mapper.OrderMapper;
import com.gmail.woosay333.onlinebookstore.repository.order.OrderRepository;
import com.gmail.woosay333.onlinebookstore.repository.shopping.cart.ShoppingCartRepository;
import com.gmail.woosay333.onlinebookstore.service.order.OrderService;
import com.gmail.woosay333.onlinebookstore.service.orderitem.OrderItemService;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final OrderItemService orderItemService;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Transactional
    @Override
    public void place(OrderRequestDto requestDto, User user) {
        ShoppingCart shoppingCart = getUserShoppingCart(user);
        BigDecimal totalPrice = calculateTotalPrice(shoppingCart);
        Map<Book, Integer> booksFromCartItem = getBookToQuantityMap(shoppingCart);
        Order order = createNewOrder(requestDto, user, totalPrice);
        Order savedOrder = orderRepository.save(order);
        Set<OrderItem> orderItems = orderItemService.save(savedOrder, booksFromCartItem);
        savedOrder.setOrderItems(orderItems);
        orderRepository.save(savedOrder);
    }

    @Transactional(readOnly = true)
    @Override
    public List<OrderResponseDto> getAllOrders(User currentUser) {
        return orderRepository.findByUserId(currentUser.getId()).stream()
                .map(orderMapper::toDto).toList();
    }

    @Transactional
    @Override
    public void updateStatus(OrderStatusRequestDto orderStatus, Long orderId, Long userId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException(
                                String.format("Can`t find order with id: %d", orderId)));
        order.setStatus(orderStatus.getStatus());
        orderRepository.save(order);
    }

    private Order createNewOrder(OrderRequestDto order, User currentUser, BigDecimal totalPrice) {
        Order newOrder = new Order();
        newOrder.setUser(currentUser);
        newOrder.setOrderDate(LocalDateTime.now());
        newOrder.setStatus(Status.PENDING);
        newOrder.setShippingAddress(order.getShippingAddress());
        newOrder.setTotal(totalPrice);
        return newOrder;
    }

    private BigDecimal calculateTotalPrice(ShoppingCart shoppingCart) {
        return shoppingCart.getCartItems().stream()
                .map(cartItem -> cartItem.getBook().getPrice()
                        .multiply(BigDecimal.valueOf(cartItem.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private ShoppingCart getUserShoppingCart(User user) {
        return shoppingCartRepository.findByUserId(user.getId())
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Can't find shopping cart for user with id: %d",
                                user.getId())));
    }

    private Map<Book, Integer> getBookToQuantityMap(ShoppingCart shoppingCart) {
        Map<Book, Integer> bookFromCartItem = new HashMap<>();
        for (CartItem cartItem : shoppingCart.getCartItems()) {
            bookFromCartItem.put(cartItem.getBook(), cartItem.getQuantity());
        }
        return bookFromCartItem;
    }
}
