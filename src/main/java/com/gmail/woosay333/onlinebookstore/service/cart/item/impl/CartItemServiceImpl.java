package com.gmail.woosay333.onlinebookstore.service.cart.item.impl;

import com.gmail.woosay333.onlinebookstore.dto.cart.item.CartItemRequestDto;
import com.gmail.woosay333.onlinebookstore.dto.cart.item.CartItemResponseDto;
import com.gmail.woosay333.onlinebookstore.dto.quantity.QuantityDto;
import com.gmail.woosay333.onlinebookstore.entity.Book;
import com.gmail.woosay333.onlinebookstore.entity.CartItem;
import com.gmail.woosay333.onlinebookstore.entity.ShoppingCart;
import com.gmail.woosay333.onlinebookstore.exception.EntityNotFoundException;
import com.gmail.woosay333.onlinebookstore.mapper.CartItemMapper;
import com.gmail.woosay333.onlinebookstore.repository.cart.item.CartItemRepository;
import com.gmail.woosay333.onlinebookstore.service.book.BookService;
import com.gmail.woosay333.onlinebookstore.service.cart.item.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {
    private final BookService bookService;
    private final CartItemRepository cartItemRepository;
    private final CartItemMapper cartItemMapper;

    @Override
    public CartItemResponseDto add(CartItemRequestDto requestDto, ShoppingCart shoppingCart) {
        Book book = bookService.getBook(requestDto.bookId());

        CartItem cartItem = cartItemRepository.findByBookAndShoppingCart(book, shoppingCart)
                .map(item -> {
                    item.setQuantity(requestDto.quantity());
                    return item;
                })
                .orElseGet(() ->
                        cartItemMapper.toModel(requestDto, shoppingCart, book));
        return cartItemMapper.toDto(cartItemRepository.save(cartItem));
    }

    @Override
    public QuantityDto changeQuantity(Long id, QuantityDto quantity, ShoppingCart shoppingCart) {
        CartItem cartItem = shoppingCart.getCartItems().stream()
                .filter(ci -> ci.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can't find cart item id%s for user %s"
                                .formatted(id, shoppingCart.getUser().getEmail())));
        cartItem.setQuantity(quantity.quantity());
        CartItem updatedCartItem = cartItemRepository.save(cartItem);
        return new QuantityDto(updatedCartItem.getQuantity());
    }

    @Override
    public void remove(Long id, ShoppingCart shoppingCart) {
        if (!cartItemRepository.existsByIdAndShoppingCart(id, shoppingCart)) {
            throw new EntityNotFoundException("Can't find cart item id%s for user %s".formatted(id,
                    shoppingCart.getUser().getEmail()));
        }
        cartItemRepository.deleteByIdAndShoppingCart(id, shoppingCart);
    }
}
