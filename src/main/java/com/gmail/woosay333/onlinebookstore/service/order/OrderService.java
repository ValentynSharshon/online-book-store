package com.gmail.woosay333.onlinebookstore.service.order;

import com.gmail.woosay333.onlinebookstore.dto.order.OrderRequestDto;
import com.gmail.woosay333.onlinebookstore.dto.order.OrderResponseDto;
import com.gmail.woosay333.onlinebookstore.dto.orderitem.OrderItemResponseDto;
import com.gmail.woosay333.onlinebookstore.dto.status.OrderStatusDto;
import com.gmail.woosay333.onlinebookstore.entity.User;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    OrderResponseDto saveOrder(OrderRequestDto requestDto, User user);

    List<OrderResponseDto> getAllOrders(User user, Pageable pageable);

    void updateStatus(Long id, OrderStatusDto statusDto);

    List<OrderItemResponseDto> getAllCartItems(Long orderId, User user, Pageable pageable);

    OrderItemResponseDto getCartItem(Long id, Long orderId, User user);
}
