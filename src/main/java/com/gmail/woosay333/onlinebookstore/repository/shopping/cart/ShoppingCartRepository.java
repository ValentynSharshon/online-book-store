package com.gmail.woosay333.onlinebookstore.repository.shopping.cart;

import com.gmail.woosay333.onlinebookstore.entity.ShoppingCart;
import com.gmail.woosay333.onlinebookstore.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    Optional<ShoppingCart> findByUser(User user);

    @Query("FROM ShoppingCart sc JOIN FETCH sc.cartItems WHERE sc.user = :user")
    Optional<ShoppingCart> findByUserWithCartItems(User user);
}
