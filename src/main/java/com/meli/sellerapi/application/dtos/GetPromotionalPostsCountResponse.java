package com.meli.sellerapi.application.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetPromotionalPostsCountResponse {
    String sellerUsername;
    int promotionalPostsCount;
}
