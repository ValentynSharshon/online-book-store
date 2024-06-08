package com.gmail.woosay333.onlinebookstore.service.order;

import com.gmail.woosay333.onlinebookstore.dto.order.OrderRequestDto;
import com.gmail.woosay333.onlinebookstore.dto.order.OrderResponseDto;
import com.gmail.woosay333.onlinebookstore.dto.orderitem.OrderItemResponseDto;
import com.gmail.woosay333.onlinebookstore.dto.status.StatusDto;
import com.gmail.woosay333.onlinebookstore.entity.User;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    OrderResponseDto createOrder(OrderRequestDto requestDto, User user);

    List<OrderResponseDto> getOrderHistory(User user, Pageable pageable);

    void updateStatus(Long id, StatusDto statusDto);

    List<OrderItemResponseDto> getAllOrderItems(Long orderId, User user, Pageable pageable);

    OrderItemResponseDto getOrderItem(Long id, Long orderId, User user);
}
