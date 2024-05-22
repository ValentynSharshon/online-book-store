package com.gmail.woosay333.onlinebookstore.service.shopping.cart.impl;

import com.gmail.woosay333.onlinebookstore.dto.cart.item.CartItemResponseDto;
import com.gmail.woosay333.onlinebookstore.dto.cart.item.CreateCartItemRequestDto;
import com.gmail.woosay333.onlinebookstore.dto.cart.item.UpdateCartItemRequestDto;
import com.gmail.woosay333.onlinebookstore.dto.shopping.cart.ShoppingCartDto;
import com.gmail.woosay333.onlinebookstore.entity.CartItem;
import com.gmail.woosay333.onlinebookstore.entity.ShoppingCart;
import com.gmail.woosay333.onlinebookstore.entity.User;
import com.gmail.woosay333.onlinebookstore.exception.EntityNotFoundException;
import com.gmail.woosay333.onlinebookstore.mapper.CartItemMapper;
import com.gmail.woosay333.onlinebookstore.mapper.ShoppingCartMapper;
import com.gmail.woosay333.onlinebookstore.repository.shopping.cart.ShoppingCartRepository;
import com.gmail.woosay333.onlinebookstore.service.cart.item.CartItemService;
import com.gmail.woosay333.onlinebookstore.service.shopping.cart.ShoppingCartService;
import jakarta.transaction.Transactional;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartMapper shoppingCartMapper;
    private final CartItemMapper cartItemMapper;
    private final CartItemService cartItemService;

    @Override
    public ShoppingCartDto getShoppingCart(Long userId) {
        return shoppingCartMapper.toDto(getShoppingCartIfExist(userId)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                String.format("Can`t find shopping cart for user with id: %d",
                                        userId))));
    }

    @Override
    @Transactional
    public CartItemResponseDto addToCart(CreateCartItemRequestDto cartItemRequestDto, User user) {
        ShoppingCart shoppingCart = getShoppingCartIfExist(user.getId())
                .orElseGet(() ->
                        shoppingCartRepository.save(createNewShoppingCart(user)));

        CartItem cartItem = cartItemService.save(cartItemRequestDto, shoppingCart);
        return update(shoppingCart, cartItem);
    }

    @Override
    @Transactional
    public CartItemResponseDto updateCartItem(Long cartItemId,
                                              UpdateCartItemRequestDto requestDto,
                                              Long userId) {
        ShoppingCart shoppingCart = getUserShoppingCart(userId);
        return shoppingCart.getCartItems().stream()
                .filter(cartItem -> cartItem.getId().equals(cartItemId))
                .findFirst()
                .map(cartItem -> {
                    cartItem.setQuantity(requestDto.getQuantity());
                    cartItemService.update(cartItem);
                    return cartItemMapper.toDto(cartItem);
                })
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                String.format("Can`t find cart item with id: %d", cartItemId)));
    }

    @Override
    @Transactional
    public void deleteCartItem(Long userId, Long cartItemId) {
        ShoppingCart userShoppingCart = getUserShoppingCart(userId);
        userShoppingCart.getCartItems().stream()
                .filter(cartItem -> cartItem.getId().equals(cartItemId))
                .findFirst()
                .ifPresentOrElse(
                        cartItemService::delete,
                        () -> {
                            throw new EntityNotFoundException(
                                    String.format("Can't find cart item with id: %d", cartItemId));
                        });
    }

    @Transactional
    public CartItemResponseDto update(ShoppingCart shoppingCart, CartItem cartItem) {
        shoppingCart.getCartItems().add(cartItem);
        shoppingCartRepository.save(shoppingCart);
        return cartItemMapper.toDto(cartItem);
    }

    private Optional<ShoppingCart> getShoppingCartIfExist(Long userId) {
        return shoppingCartRepository.findByUserId(userId);
    }

    private ShoppingCart createNewShoppingCart(User user) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        return shoppingCart;
    }

    private ShoppingCart getUserShoppingCart(Long userId) {
        return shoppingCartRepository.findByUserId(userId)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                String.format("Can`t find shopping cart by user id: %d", userId)));
    }
}
