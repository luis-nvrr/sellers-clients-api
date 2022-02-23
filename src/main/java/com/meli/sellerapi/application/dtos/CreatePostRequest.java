package com.meli.sellerapi.application.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreatePostRequest {
    private String description;
    private boolean isPromotion;
    private boolean isCashbackEligible;
    private String sellerUsername;
}
