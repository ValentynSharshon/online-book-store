package com.gmail.woosay333.onlinebookstore.mapper;

import com.gmail.woosay333.onlinebookstore.config.MapperConfig;
import com.gmail.woosay333.onlinebookstore.dto.cart.item.CartItemRequestDto;
import com.gmail.woosay333.onlinebookstore.dto.cart.item.CartItemResponseDto;
import com.gmail.woosay333.onlinebookstore.entity.Book;
import com.gmail.woosay333.onlinebookstore.entity.CartItem;
import com.gmail.woosay333.onlinebookstore.entity.ShoppingCart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(config = MapperConfig.class,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CartItemMapper {
    @Mapping(target = "bookId", source = "book.id")
    @Mapping(target = "bookTitle", source = "book.title")
    CartItemResponseDto toDto(CartItem cartItem);

    default CartItem toModel(
            CartItemRequestDto requestDto, ShoppingCart shoppingCart, Book book
    ) {
        CartItem newCartItem = new CartItem();
        newCartItem.setQuantity(requestDto.quantity());
        newCartItem.setShoppingCart(shoppingCart);
        newCartItem.setBook(book);
        return newCartItem;
    }
}
