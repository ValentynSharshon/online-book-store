package com.gmail.woosay333.onlinebookstore.service.cart.item.impl;

import com.gmail.woosay333.onlinebookstore.dto.cart.item.CreateCartItemRequestDto;
import com.gmail.woosay333.onlinebookstore.entity.CartItem;
import com.gmail.woosay333.onlinebookstore.entity.ShoppingCart;
import com.gmail.woosay333.onlinebookstore.exception.EntityAlreadyExistException;
import com.gmail.woosay333.onlinebookstore.exception.EntityNotFoundException;
import com.gmail.woosay333.onlinebookstore.mapper.CartItemMapper;
import com.gmail.woosay333.onlinebookstore.repository.book.BookRepository;
import com.gmail.woosay333.onlinebookstore.repository.cart.item.CartItemRepository;
import com.gmail.woosay333.onlinebookstore.service.cart.item.CartItemService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {
    private final CartItemRepository cartItemRepository;
    private final CartItemMapper cartItemMapper;
    private final BookRepository bookRepository;

    @Override
    @Transactional
    public CartItem save(CreateCartItemRequestDto cartItemRequestDto,
                         ShoppingCart shoppingCart) {
        cartItemRepository.findBookInCart(cartItemRequestDto.getBookId(),
                        shoppingCart.getId())
                .ifPresent(s -> {
                    throw new EntityAlreadyExistException(
                            String.format("Book with id: %d already exist",
                                    cartItemRequestDto.getBookId()));
                });
        CartItem cartItem = cartItemMapper.toModel(cartItemRequestDto);
        cartItem.setBook(bookRepository.findById(cartItemRequestDto.getBookId())
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                String.format("Can`t find book with id: %d",
                                        cartItemRequestDto.getBookId()))));
        cartItem.setShoppingCart(shoppingCart);
        return cartItemRepository.save(cartItem);
    }

    @Override
    @Transactional
    public CartItem update(CartItem cartItem) {
        isCartItemExist(cartItem);
        return cartItemRepository.save(cartItem);
    }

    @Override
    @Transactional
    public void delete(CartItem cartItem) {
        isCartItemExist(cartItem);
        cartItemRepository.delete(cartItem);
    }

    private void isCartItemExist(CartItem cartItem) {
        if (!cartItemRepository.existsById(cartItem.getId())) {
            throw new EntityNotFoundException(
                    String.format("Can`t find cart item with id: %d",
                            cartItem.getId()));

        }
    }
}
