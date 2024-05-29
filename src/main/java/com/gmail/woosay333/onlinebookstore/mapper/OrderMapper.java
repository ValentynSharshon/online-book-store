package com.gmail.woosay333.onlinebookstore.mapper;

import com.gmail.woosay333.onlinebookstore.config.MapperConfig;
import com.gmail.woosay333.onlinebookstore.dto.order.OrderRequestDto;
import com.gmail.woosay333.onlinebookstore.dto.order.OrderResponseDto;
import com.gmail.woosay333.onlinebookstore.entity.Order;
import com.gmail.woosay333.onlinebookstore.entity.OrderItem;
import com.gmail.woosay333.onlinebookstore.entity.Status;
import com.gmail.woosay333.onlinebookstore.entity.User;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class,
        uses = OrderItemMapper.class)
public interface OrderMapper {
    @Mapping(target = "userId", source = "user.id")
    OrderResponseDto toDto(Order order);

    default Order toModel(OrderRequestDto requestDto, User user, Set<OrderItem> orderItems) {
        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setTotal(getTotalPrice(user, orderItems));
        order.setShippingAddress(requestDto.shippingAddress());
        order.setStatus(Status.PENDING);
        order.setOrderItems(orderItems.stream()
                .peek(orderItem -> orderItem.setOrder(order))
                .collect(Collectors.toSet()));
        return order;
    }

    private BigDecimal getTotalPrice(User user, Set<OrderItem> orderItems) {
        return orderItems.stream()
                .map(cartItem -> cartItem.getBook().getPrice().multiply(
                        BigDecimal.valueOf(cartItem.getQuantity())))
                .reduce(BigDecimal::add)
                .orElseThrow(() -> new ArithmeticException(
                        "Can't calculate total sum for user order. User: "
                                + user.getEmail()));
    }
}
