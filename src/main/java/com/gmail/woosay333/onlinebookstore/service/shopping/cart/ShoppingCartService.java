package com.gmail.woosay333.onlinebookstore.service.shopping.cart;

import com.gmail.woosay333.onlinebookstore.dto.cart.item.CartItemRequestDto;
import com.gmail.woosay333.onlinebookstore.dto.cart.item.CartItemResponseDto;
import com.gmail.woosay333.onlinebookstore.dto.quantity.QuantityDto;
import com.gmail.woosay333.onlinebookstore.dto.shopping.cart.ShoppingCartDto;
import com.gmail.woosay333.onlinebookstore.entity.User;

public interface ShoppingCartService {
    ShoppingCartDto getShoppingCartWithCartItems(User user);

    QuantityDto updateCartItem(Long cartItemId, QuantityDto quantityDto, User user);

    void removeCartItem(Long cartItemId, User user);

    CartItemResponseDto addCartItem(CartItemRequestDto requestDto, User user);
}
