package com.gmail.woosay333.onlinebookstore.service.shopping.cart;

import com.gmail.woosay333.onlinebookstore.dto.shopping.cart.CartItemDto;
import com.gmail.woosay333.onlinebookstore.dto.shopping.cart.UpdateCartItemRequestDto;
import com.gmail.woosay333.onlinebookstore.dto.shopping.cart.CreateCartItemRequestDto;
import com.gmail.woosay333.onlinebookstore.dto.shopping.cart.ShoppingCartDto;
import com.gmail.woosay333.onlinebookstore.entity.ShoppingCart;
import com.gmail.woosay333.onlinebookstore.entity.User;

import java.util.Optional;

public interface ShoppingCartService {
    ShoppingCartDto getShoppingCart();

    ShoppingCartDto addToCart(CreateCartItemRequestDto request, User user);

    CartItemDto updateCartItem(Long cartItemId, UpdateCartItemRequestDto request, Long userId);

    void deleteCartItem(Long userId, Long cartItemId);

    Optional<ShoppingCart> findCartByCurrentUser(Long userId);

    void cleanUp(ShoppingCart shoppingCart);
}
