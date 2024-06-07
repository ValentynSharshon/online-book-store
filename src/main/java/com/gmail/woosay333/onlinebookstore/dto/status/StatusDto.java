package com.gmail.woosay333.onlinebookstore.dto.status;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gmail.woosay333.onlinebookstore.entity.Status;

public record StatusDto(
        @JsonProperty(value = "status",
                required = true)
        Status status
) {
}
