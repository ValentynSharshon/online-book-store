package com.gmail.woosay333.onlinebookstore.dto.status;

import com.gmail.woosay333.onlinebookstore.entity.Status;
import lombok.Data;

@Data
public class OrderStatusRequestDto {
    private Status status;
}
