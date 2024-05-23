package com.gmail.woosay333.onlinebookstore.mapper;

import com.gmail.woosay333.onlinebookstore.config.MapperConfig;
import com.gmail.woosay333.onlinebookstore.dto.order.OrderResponseDto;
import com.gmail.woosay333.onlinebookstore.entity.Order;
import java.util.stream.Collectors;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class, uses = OrderItemMapper.class)
public interface OrderMapper {
    @Mapping(source = "user.id", target = "userId")
    OrderResponseDto toDto(Order order);

    @AfterMapping
    default void mapOrderItemsToDto(
            Order order,
            @MappingTarget OrderResponseDto orderResponseDto,
            OrderItemMapper orderItemMapper) {
        if (order.getOrderItems() != null) {
            orderResponseDto.setOrderItems(order.getOrderItems().stream()
                    .map(orderItemMapper::toDto)
                    .collect(Collectors.toSet()));
        }
    }
}
