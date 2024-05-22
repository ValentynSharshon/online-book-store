package com.gmail.woosay333.onlinebookstore.repository.shopping.cart;

import com.gmail.woosay333.onlinebookstore.entity.ShoppingCart;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    @Query("FROM ShoppingCart sc LEFT JOIN FETCH sc.cartItems ci "
            + "LEFT JOIN FETCH ci.book WHERE sc.user.id = :userId")
    Optional<ShoppingCart> findByUserId(Long userId);
}
