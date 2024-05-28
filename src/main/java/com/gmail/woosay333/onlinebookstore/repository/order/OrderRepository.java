package com.gmail.woosay333.onlinebookstore.repository.order;

import com.gmail.woosay333.onlinebookstore.entity.Order;
import com.gmail.woosay333.onlinebookstore.entity.Status;
import com.gmail.woosay333.onlinebookstore.entity.User;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @EntityGraph(attributePaths = "orderItems.book")
    List<Order> findAllByUser(User user, Pageable pageable);

    @Modifying
    @Query("UPDATE Order o SET o.status = :status WHERE o.id = :id")
    int updateStatusById(Long id, Status status);
}
