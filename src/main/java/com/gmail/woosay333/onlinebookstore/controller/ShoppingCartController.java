package com.gmail.woosay333.onlinebookstore.controller;

import com.gmail.woosay333.onlinebookstore.dto.shopping.cart.CartItemDto;
import com.gmail.woosay333.onlinebookstore.dto.shopping.cart.CreateCartItemRequestDto;
import com.gmail.woosay333.onlinebookstore.dto.shopping.cart.ShoppingCartDto;
import com.gmail.woosay333.onlinebookstore.dto.shopping.cart.UpdateCartItemRequestDto;
import com.gmail.woosay333.onlinebookstore.entity.User;
import com.gmail.woosay333.onlinebookstore.service.shopping.cart.ShoppingCartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
@Tag(name = "Shopping Cart management.",
        description = "Endpoints for managing Shopping Carts.")
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get a shopping cart.",
            description = "Retrieve the user's shopping cart from DB.")
    public ShoppingCartDto getShoppingCart() {
        return shoppingCartService.getShoppingCart();
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Add new item to a cart.",
            description = "Save a new item to the user's shopping cart through DB.")
    public ShoppingCartDto addToCart(@RequestBody @Valid CreateCartItemRequestDto request,
                                     @AuthenticationPrincipal User user) {
        return shoppingCartService.addToCart(request, user);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PutMapping("/cart-items/{cartItemId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update a cart item.",
            description = "Update an existing cart item in the user's shopping cart by its ID.")
    public CartItemDto updateCartItem(@PathVariable Long cartItemId,
                                      @RequestBody @Valid UpdateCartItemRequestDto request,
                                      @AuthenticationPrincipal User user) {
        return shoppingCartService.updateCartItem(cartItemId, request, user.getId());
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/cart-items/{cartItemId}")
    @Operation(summary = "Delete a cart item.",
            description = "Delete the item from the user's shopping cart in DB by its ID.")
    public void deleteCartItem(@PathVariable Long cartItemId,
                               @AuthenticationPrincipal User user) {
        shoppingCartService.deleteCartItem(user.getId(), cartItemId);
    }
}
