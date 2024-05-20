package com.gmail.woosay333.onlinebookstore.mapper;

import com.gmail.woosay333.onlinebookstore.config.MapperConfig;
import com.gmail.woosay333.onlinebookstore.dto.shopping.cart.CartItemDto;
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
    CartItemDto toDto(CartItem cartItem);

    @Mapping(target = "book", source = "bookId", qualifiedByName = "bookFromId")
    CartItem toModel(CartItemDto cartItemDto);
}
