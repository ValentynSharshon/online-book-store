package com.gmail.woosay333.onlinebookstore.repository.cart.item;

import com.gmail.woosay333.onlinebookstore.entity.CartItem;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    @Query("SELECT c FROM CartItem c WHERE c.book.id = :bookId "
            + "AND c.shoppingCart.id = :shoppingCartId")
    Optional<CartItem> findBookInCart(Long bookId, Long shoppingCartId);
}
