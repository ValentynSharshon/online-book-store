package com.gmail.woosay333.onlinebookstore.service.shopping.cart;

import com.gmail.woosay333.onlinebookstore.dto.cart.item.CartItemResponseDto;
import com.gmail.woosay333.onlinebookstore.dto.cart.item.CreateCartItemRequestDto;
import com.gmail.woosay333.onlinebookstore.dto.cart.item.UpdateCartItemRequestDto;
import com.gmail.woosay333.onlinebookstore.dto.shopping.cart.ShoppingCartDto;
import com.gmail.woosay333.onlinebookstore.entity.User;

public interface ShoppingCartService {
    ShoppingCartDto getShoppingCart(Long userId);

    CartItemResponseDto addToCart(CreateCartItemRequestDto request,
                                  User user);

    CartItemResponseDto updateCartItem(Long cartItemId,
                                       UpdateCartItemRequestDto request,
                                       Long userId);

    void deleteCartItem(Long userId,
                        Long cartItemId);
}
