package com.gmail.woosay333.onlinebookstore.service.orderitem;

import com.gmail.woosay333.onlinebookstore.dto.orderitem.OrderItemResponseDto;
import com.gmail.woosay333.onlinebookstore.entity.Book;
import com.gmail.woosay333.onlinebookstore.entity.Order;
import com.gmail.woosay333.onlinebookstore.entity.OrderItem;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface OrderItemService {
    Set<OrderItem> save(Order order, Map<Book, Integer> bookFromCartItem);

    List<OrderItemResponseDto> getAllOrderItems(Long orderId);

    OrderItemResponseDto getItem(Long orderId, Long itemId);
}
