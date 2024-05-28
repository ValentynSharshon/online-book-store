package com.gmail.woosay333.onlinebookstore.repository.orderitem;

import com.gmail.woosay333.onlinebookstore.entity.OrderItem;
import com.gmail.woosay333.onlinebookstore.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    @EntityGraph(attributePaths = {"book"})
    List<OrderItem> findAllByOrder_IdAndOrder_User(Long orderId, User user, Pageable pageable);

    @EntityGraph(attributePaths = {"book"})
    Optional<OrderItem> findByIdAndOrder_IdAndOrder_User(Long id, Long orderId, User user);
}
