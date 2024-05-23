package com.gmail.woosay333.onlinebookstore.dto.order;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class OrderRequestDto {
    @NotBlank(message = "Value of the Shipping Address field can't be null or empty")
    private String shippingAddress;
}
