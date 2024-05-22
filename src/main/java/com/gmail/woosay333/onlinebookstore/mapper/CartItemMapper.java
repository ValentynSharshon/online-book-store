package com.gmail.woosay333.onlinebookstore.mapper;

import com.gmail.woosay333.onlinebookstore.config.MapperConfig;
import com.gmail.woosay333.onlinebookstore.dto.cart.item.CartItemResponseDto;
import com.gmail.woosay333.onlinebookstore.dto.cart.item.CreateCartItemRequestDto;
import com.gmail.woosay333.onlinebookstore.entity.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(config = MapperConfig.class,
        uses = BookMapper.class,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CartItemMapper {
    @Mapping(source = "book.id", target = "bookId")
    @Mapping(source = "book.title", target = "bookTitle")
    CartItemResponseDto toDto(CartItem cartItem);

    @Mapping(target = "book", source = "bookId", qualifiedByName = "bookFromId")
    CartItem toModel(CartItemResponseDto cartItemResponseDto);

    CartItem toModel(CreateCartItemRequestDto cartItemRequestDto);
}
