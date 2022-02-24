package com.meli.sellerapi.application.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreatePromotionalPostRequest {
    private String description;
    private String sellerUsername;
}
