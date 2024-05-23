package com.gmail.woosay333.onlinebookstore.repository.orderitem;

import com.gmail.woosay333.onlinebookstore.entity.OrderItem;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findByOrderId(Long orderId);

    Optional<OrderItem> findByIdAndOrderId(Long itemId, Long orderId);
}
