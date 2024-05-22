package com.gmail.woosay333.onlinebookstore.service.cart.item;

import com.gmail.woosay333.onlinebookstore.dto.cart.item.CreateCartItemRequestDto;
import com.gmail.woosay333.onlinebookstore.entity.CartItem;
import com.gmail.woosay333.onlinebookstore.entity.ShoppingCart;

public interface CartItemService {
    CartItem save(CreateCartItemRequestDto cartItemRequestDto, ShoppingCart shoppingCart);

    CartItem update(CartItem cartItem);

    void delete(CartItem cartItem);
}
