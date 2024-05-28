package com.gmail.woosay333.onlinebookstore.controller;

import com.gmail.woosay333.onlinebookstore.dto.order.OrderRequestDto;
import com.gmail.woosay333.onlinebookstore.dto.order.OrderResponseDto;
import com.gmail.woosay333.onlinebookstore.dto.orderitem.OrderItemResponseDto;
import com.gmail.woosay333.onlinebookstore.dto.status.OrderStatusDto;
import com.gmail.woosay333.onlinebookstore.entity.User;
import com.gmail.woosay333.onlinebookstore.service.order.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@Tag(name = "Order management.",
        description = "Endpoints for managing orders.")
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create an order.",
            description = "Create an order from user`s cart.")
    public OrderResponseDto createOrder(
            @RequestBody @Valid OrderRequestDto requestDto,
            @AuthenticationPrincipal User user
    ) {
        return orderService.saveOrder(requestDto, user);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all orders.",
            description = "Get all user`s orders.")
    public List<OrderResponseDto> getOrderHistory(
            @AuthenticationPrincipal User user,
            @ParameterObject @PageableDefault(sort = "orderDate", value = 5) Pageable pageable
    ) {
        return orderService.getAllOrders(user, pageable);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Operation(summary = "Update order status.",
            description = "Update order status by ID.")
    public void updateStatus(
            @PathVariable Long id,
            @RequestBody OrderStatusDto statusDto
    ) {
        orderService.updateStatus(id, statusDto);
    }

    @GetMapping("/{orderId}/items")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Return all cart items from certain order.",
            description = "Return all cart items from certain order by order ID.")
    public List<OrderItemResponseDto> getAllOrderItems(
            @PathVariable Long orderId,
            @AuthenticationPrincipal User user,
            @ParameterObject @PageableDefault(sort = "id", value = 5) Pageable pageable
    ) {
        return orderService.getAllCartItems(orderId, user, pageable);
    }

    @GetMapping("/{orderId}/items/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Return specific cart item from certain order.",
            description = "Return cart item by ID from certain order by order ID.")
    public OrderItemResponseDto getOrderItem(
            @PathVariable Long id,
            @PathVariable Long orderId,
            @AuthenticationPrincipal User user
    ) {
        return orderService.getCartItem(id, orderId, user);
    }
}
