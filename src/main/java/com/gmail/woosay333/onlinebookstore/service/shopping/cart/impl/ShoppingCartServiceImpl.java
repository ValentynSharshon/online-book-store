package com.gmail.woosay333.onlinebookstore.service.shopping.cart.impl;

import com.gmail.woosay333.onlinebookstore.dto.cart.item.CartItemRequestDto;
import com.gmail.woosay333.onlinebookstore.dto.cart.item.CartItemResponseDto;
import com.gmail.woosay333.onlinebookstore.dto.quantity.QuantityDto;
import com.gmail.woosay333.onlinebookstore.dto.shopping.cart.ShoppingCartDto;
import com.gmail.woosay333.onlinebookstore.entity.ShoppingCart;
import com.gmail.woosay333.onlinebookstore.entity.User;
import com.gmail.woosay333.onlinebookstore.exception.EntityNotFoundException;
import com.gmail.woosay333.onlinebookstore.mapper.ShoppingCartMapper;
import com.gmail.woosay333.onlinebookstore.repository.shopping.cart.ShoppingCartRepository;
import com.gmail.woosay333.onlinebookstore.service.cart.item.CartItemService;
import com.gmail.woosay333.onlinebookstore.service.shopping.cart.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository cartRepository;
    private final ShoppingCartMapper cartMapper;
    private final CartItemService cartItemService;

    @Override
    @org.springframework.transaction.annotation.Transactional
    public ShoppingCartDto getShoppingCart(User user) {
        ShoppingCart shoppingCart =
                cartRepository.findByUserWithCartItems(user)
                        .orElseGet(() -> cartRepository.save(new ShoppingCart(user)));
        return cartMapper.toDto(shoppingCart);
    }

    @Override
    @org.springframework.transaction.annotation.Transactional
    public CartItemResponseDto addCartItem(CartItemRequestDto requestDto, User user) {
        ShoppingCart shoppingCart = getExistedOrNewCart(user);
        return cartItemService.add(requestDto, shoppingCart);
    }

    @Override
    @org.springframework.transaction.annotation.Transactional
    public QuantityDto updateCartItem(Long cartItemId, QuantityDto quantityDto, User user) {
        ShoppingCart shoppingCart = cartRepository.findByUser(user).orElseThrow(
                () -> new EntityNotFoundException("Can't update item by ID: " + cartItemId));
        return cartItemService.changeQuantity(cartItemId, quantityDto, shoppingCart);
    }

    @Override
    @Transactional
    public void deleteCartItem(Long cartItemId, User user) {
        ShoppingCart shoppingCart = cartRepository.findByUser(user).orElseThrow(
                () -> new EntityNotFoundException("Can't remove item by ID: " + cartItemId));
        cartItemService.remove(cartItemId, shoppingCart);
    }

    private ShoppingCart getExistedOrNewCart(User user) {
        return cartRepository.findByUser(user)
                .orElseGet(() -> cartRepository.save(
                        new ShoppingCart(user)));
    }
}
