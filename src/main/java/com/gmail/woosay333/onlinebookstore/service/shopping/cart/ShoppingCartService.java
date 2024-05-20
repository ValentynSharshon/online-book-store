package com.gmail.woosay333.onlinebookstore.service.shopping.cart;

import com.gmail.woosay333.onlinebookstore.dto.shopping.cart.CartItemDto;
import com.gmail.woosay333.onlinebookstore.dto.shopping.cart.CartItemUpdateRequestDto;
import com.gmail.woosay333.onlinebookstore.dto.shopping.cart.CreateCartItemRequestDto;
import com.gmail.woosay333.onlinebookstore.dto.shopping.cart.ShoppingCartDto;
import com.gmail.woosay333.onlinebookstore.entity.ShoppingCart;
import java.util.Optional;

public interface ShoppingCartService {
    ShoppingCartDto getShoppingCart();

    ShoppingCartDto addToCart(CreateCartItemRequestDto request);

    CartItemDto updateCartItem(Long cartItemId, CartItemUpdateRequestDto request);

    void deleteCartItem(Long cartItemId);

    Optional<ShoppingCart> findCartByCurrentUser(Long userId);

    void cleanUp(ShoppingCart shoppingCart);
}
