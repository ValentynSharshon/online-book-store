package com.gmail.woosay333.onlinebookstore.controller;

import com.gmail.woosay333.onlinebookstore.dto.order.OrderRequestDto;
import com.gmail.woosay333.onlinebookstore.dto.order.OrderResponseDto;
import com.gmail.woosay333.onlinebookstore.dto.orderitem.OrderItemResponseDto;
import com.gmail.woosay333.onlinebookstore.dto.status.StatusDto;
import com.gmail.woosay333.onlinebookstore.entity.User;
import com.gmail.woosay333.onlinebookstore.service.order.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
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

@Tag(name = "Order management.",
        description = "Endpoints for managing orders.")
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    @PreAuthorize("hasAnyRole('USER','MANAGER','ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create an order",
            description = "Create an order from user`s cart")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid request body",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "401", description = "Required authorization",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    public OrderResponseDto createOrder(
            @RequestBody @Valid OrderRequestDto requestDto,
            @AuthenticationPrincipal User user
    ) {
        return orderService.createOrder(requestDto, user);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER','MANAGER','ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all orders",
            description = "Get all user`s orders")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "401", description = "Required authorization",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    public List<OrderResponseDto> getOrderHistory(
            @AuthenticationPrincipal User user,
            @ParameterObject @PageableDefault(sort = "orderDate", value = 5) Pageable pageable
    ) {
        return orderService.getOrderHistory(user, pageable);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Operation(summary = "Update order status",
            description = "Update order status by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Successfully updated"),
            @ApiResponse(responseCode = "400", description = "Invalid request body",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "401", description = "Required authorization",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "403", description = "Not enough access rights",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    public void updateStatus(
            @PathVariable Long id,
            @RequestBody StatusDto statusDto
    ) {
        orderService.updateStatus(id, statusDto);
    }

    @GetMapping("/{orderId}/items")
    @PreAuthorize("hasAnyRole('USER','MANAGER','ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Return all cart items from certain order",
            description = "Return all cart items from certain order by order ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "401", description = "Required authorization",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    public List<OrderItemResponseDto> getAllOrderItems(
            @PathVariable Long orderId,
            @AuthenticationPrincipal User user,
            @ParameterObject @PageableDefault(sort = "id", value = 5) Pageable pageable
    ) {
        return orderService.getAllOrderItems(orderId, user, pageable);
    }

    @GetMapping("/{orderId}/items/{id}")
    @PreAuthorize("hasAnyRole('USER','MANAGER','ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Return specific cart item from certain order",
            description = "Return cart item by ID from certain order by order ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "401", description = "Required authorization",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    public OrderItemResponseDto getOrderItem(
            @PathVariable Long id,
            @PathVariable Long orderId,
            @AuthenticationPrincipal User user
    ) {
        return orderService.getOrderItem(id, orderId, user);
    }
}
