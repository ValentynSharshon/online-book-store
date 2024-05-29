package com.gmail.woosay333.onlinebookstore.service.cart.item;

import com.gmail.woosay333.onlinebookstore.dto.cart.item.CartItemRequestDto;
import com.gmail.woosay333.onlinebookstore.dto.cart.item.CartItemResponseDto;
import com.gmail.woosay333.onlinebookstore.dto.quantity.QuantityDto;
import com.gmail.woosay333.onlinebookstore.entity.ShoppingCart;

public interface CartItemService {
    CartItemResponseDto add(CartItemRequestDto cartItemRequestDto, ShoppingCart shoppingCart);

    QuantityDto changeQuantity(Long id, QuantityDto quantityDto, ShoppingCart shoppingCart);

    void remove(Long id, ShoppingCart shoppingCart);
}
