package com.gmail.woosay333.onlinebookstore.repository.cart.item;

import com.gmail.woosay333.onlinebookstore.entity.Book;
import com.gmail.woosay333.onlinebookstore.entity.CartItem;
import com.gmail.woosay333.onlinebookstore.entity.ShoppingCart;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByBookAndShoppingCart(Book book, ShoppingCart shoppingCart);

    boolean existsByIdAndShoppingCart(Long cartItemId, ShoppingCart shoppingCart);

    void deleteByIdAndShoppingCart(Long cartItemId, ShoppingCart shoppingCart);
}
