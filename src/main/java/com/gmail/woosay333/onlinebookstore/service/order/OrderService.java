package com.gmail.woosay333.onlinebookstore.service.order;

import com.gmail.woosay333.onlinebookstore.dto.order.OrderRequestDto;
import com.gmail.woosay333.onlinebookstore.dto.order.OrderResponseDto;
import com.gmail.woosay333.onlinebookstore.dto.status.OrderStatusRequestDto;
import com.gmail.woosay333.onlinebookstore.entity.User;
import java.util.List;

public interface OrderService {
    void place(OrderRequestDto order, User user);

    List<OrderResponseDto> getAllOrders(User user);

    void updateStatus(OrderStatusRequestDto status, Long orderId, Long userId);
}
