package com.gmail.woosay333.onlinebookstore.service.orderitem;

import com.gmail.woosay333.onlinebookstore.dto.orderitem.OrderItemResponseDto;
import com.gmail.woosay333.onlinebookstore.entity.CartItem;
import com.gmail.woosay333.onlinebookstore.entity.OrderItem;
import com.gmail.woosay333.onlinebookstore.entity.User;
import java.util.List;
import java.util.Set;
import org.springframework.data.domain.Pageable;

public interface OrderItemService {
    Set<OrderItem> convertFrom(Set<CartItem> cartItems);

    List<OrderItemResponseDto> findAllByOrder(Long orderId, User user, Pageable pageable);

    OrderItemResponseDto getById(Long id, Long orderId, User user);
}
