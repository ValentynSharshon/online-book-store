package com.gmail.woosay333.onlinebookstore.service.shopping.cart.impl;

import com.gmail.woosay333.onlinebookstore.dto.shopping.cart.CartItemDto;
import com.gmail.woosay333.onlinebookstore.dto.shopping.cart.CartItemUpdateRequestDto;
import com.gmail.woosay333.onlinebookstore.dto.shopping.cart.CreateCartItemRequestDto;
import com.gmail.woosay333.onlinebookstore.dto.shopping.cart.ShoppingCartDto;
import com.gmail.woosay333.onlinebookstore.entity.Book;
import com.gmail.woosay333.onlinebookstore.entity.CartItem;
import com.gmail.woosay333.onlinebookstore.entity.ShoppingCart;
import com.gmail.woosay333.onlinebookstore.entity.User;
import com.gmail.woosay333.onlinebookstore.exception.EntityNotFoundException;
import com.gmail.woosay333.onlinebookstore.mapper.CartItemMapper;
import com.gmail.woosay333.onlinebookstore.mapper.ShoppingCartMapper;
import com.gmail.woosay333.onlinebookstore.repository.book.BookRepository;
import com.gmail.woosay333.onlinebookstore.repository.shopping.cart.CartItemRepository;
import com.gmail.woosay333.onlinebookstore.repository.shopping.cart.ShoppingCartRepository;
import com.gmail.woosay333.onlinebookstore.service.shopping.cart.ShoppingCartService;
import com.gmail.woosay333.onlinebookstore.service.user.UserService;
import jakarta.transaction.Transactional;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemRepository cartItemRepository;
    private final ShoppingCartMapper shoppingCartMapper;
    private final CartItemMapper cartItemMapper;
    private final UserService userService;
    private final BookRepository bookRepository;

    @Override
    public ShoppingCartDto getShoppingCart() {
        User user = userService.getAuthenticatedUser();
        return shoppingCartMapper.toDto(shoppingCartRepository.findByUserId(user.getId())
                .orElseGet(() -> createNewShoppingCart(user)));
    }

    @Override
    @Transactional
    public ShoppingCartDto addToCart(CreateCartItemRequestDto cartItemRequestDto) {
        User user = userService.getAuthenticatedUser();
        Book book = bookRepository.findById(cartItemRequestDto.getBookId())
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Book with id: %d not found",
                                cartItemRequestDto.getBookId())));
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(user.getId())
                .orElseGet(() -> createNewShoppingCart(user));
        CartItem cartItem = createCartItem(book, shoppingCart, cartItemRequestDto.getQuantity());
        cartItemRepository.save(cartItem);
        shoppingCart.getCartItems().add(cartItem);
        return shoppingCartMapper.toDto(shoppingCart);
    }

    @Override
    @Transactional
    public CartItemDto updateCartItem(Long cartItemId, CartItemUpdateRequestDto requestDto) {
        ShoppingCart shoppingCart = shoppingCartRepository
                .findByUserId(userService.getAuthenticatedUser().getId())
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("There is no cart belonging to user with id: %d",
                                userService.getAuthenticatedUser().getId())));
        CartItem cartItem = cartItemRepository.findByIdAndShoppingCartId(cartItemId,
                shoppingCart.getId())
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Can't find cart item with id: %d", cartItemId)));
        cartItem.setQuantity(requestDto.getQuantity());
        return cartItemMapper.toDto(cartItemRepository.save(cartItem));
    }

    @Override
    public void deleteCartItem(Long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }

    @Override
    public Optional<ShoppingCart> findCartByCurrentUser(Long userId) {
        return shoppingCartRepository.findByUserId(userId);
    }

    @Override
    public void cleanUp(ShoppingCart shoppingCart) {
        cartItemRepository.deleteAll(shoppingCart.getCartItems());
        shoppingCart.getCartItems().clear();
    }

    private ShoppingCart createNewShoppingCart(User user) {
        ShoppingCart newShoppingCart = new ShoppingCart();
        newShoppingCart.setUser(user);
        return shoppingCartRepository.save(newShoppingCart);
    }

    private CartItem createCartItem(Book book, ShoppingCart shoppingCart, int quantity) {
        CartItem cartItem = new CartItem();
        cartItem.setBook(book);
        cartItem.setShoppingCart(shoppingCart);
        cartItem.setQuantity(quantity);
        return cartItem;
    }
}
