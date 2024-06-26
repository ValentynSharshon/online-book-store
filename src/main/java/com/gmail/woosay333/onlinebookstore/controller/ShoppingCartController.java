package com.gmail.woosay333.onlinebookstore.controller;

import com.gmail.woosay333.onlinebookstore.dto.cart.item.CartItemRequestDto;
import com.gmail.woosay333.onlinebookstore.dto.cart.item.CartItemResponseDto;
import com.gmail.woosay333.onlinebookstore.dto.quantity.QuantityDto;
import com.gmail.woosay333.onlinebookstore.dto.shopping.cart.ShoppingCartDto;
import com.gmail.woosay333.onlinebookstore.entity.User;
import com.gmail.woosay333.onlinebookstore.service.shopping.cart.ShoppingCartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
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

@Tag(name = "Shopping Cart management.",
        description = "Endpoints for managing Shopping Carts.")
@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    @GetMapping
    @PreAuthorize("hasAnyRole('USER','MANAGER','ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Return shopping cart",
            description = "Return shopping cart authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "401", description = "Required authorization",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    public ShoppingCartDto getShoppingCart(@AuthenticationPrincipal User user) {
        return shoppingCartService.getShoppingCart(user);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('USER','MANAGER','ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Add new cart item to cart",
            description = "Add new cart item to user`s cart")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully added"),
            @ApiResponse(responseCode = "400", description = "Invalid request body",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "401", description = "Required authorization",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    public CartItemResponseDto addCartItem(
            @RequestBody CartItemRequestDto requestDto,
            @AuthenticationPrincipal User user
    ) {
        return shoppingCartService.addCartItem(requestDto, user);
    }

    @PutMapping("/cart-items/{cartItemId}")
    @PreAuthorize("hasAnyRole('USER','MANAGER','ADMIN')")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Operation(summary = "Update cart item quantity by ID",
            description = "Update cart item quantity by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Successfully updated"),
            @ApiResponse(responseCode = "400", description = "Invalid request body",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "401", description = "Required authorization",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    public QuantityDto updateCartItem(
            @PathVariable Long cartItemId,
            @RequestBody QuantityDto quantityDto,
            @AuthenticationPrincipal User user
    ) {
        return shoppingCartService.updateCartItem(cartItemId, quantityDto, user);
    }

    @DeleteMapping("/cart-items/{cartItemId}")
    @PreAuthorize("hasAnyRole('USER','MANAGER','ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a cart item by ID",
            description = "Delete a cart item by ID if exist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content - successfully deleted"),
            @ApiResponse(responseCode = "401", description = "Required authorization",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "Not found - wrong id",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    public void deleteCartItem(
            @PathVariable Long cartItemId,
            @AuthenticationPrincipal User user
    ) {
        shoppingCartService.deleteCartItem(cartItemId, user);
    }
}
