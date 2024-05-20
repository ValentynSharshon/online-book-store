package com.gmail.woosay333.onlinebookstore.repository.shopping.cart;

import com.gmail.woosay333.onlinebookstore.entity.CartItem;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    @Query("FROM CartItem ci WHERE ci.id = :cartItemId AND ci.shoppingCart.id = :shoppingCartId")
    Optional<CartItem> findByIdAndShoppingCartId(Long cartItemId, Long shoppingCartId);
}
