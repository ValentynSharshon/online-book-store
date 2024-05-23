package com.gmail.woosay333.onlinebookstore.controller;

import com.gmail.woosay333.onlinebookstore.dto.orderitem.OrderItemResponseDto;
import com.gmail.woosay333.onlinebookstore.service.orderitem.OrderItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@Tag(name = "Order items management.",
        description = "Endpoints for managing order items.")
public class OrderItemController {
    private final OrderItemService orderItemService;

    @GetMapping("/{id}/items")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @Operation(summary = "Get all order items.",
            description = "Get all order items by the order id.")
    public List<OrderItemResponseDto> getAll(@PathVariable Long id) {
        return orderItemService.getAllOrderItems(id);
    }

    @GetMapping("/{orderId}/items/{itemId}")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @Operation(summary = "Get order item from.",
            description = "Get order item from order by the order id.")
    public OrderItemResponseDto getItem(
            @PathVariable Long orderId,
            @PathVariable Long itemId
    ) {
        return orderItemService.getItem(orderId, itemId);
    }
}
