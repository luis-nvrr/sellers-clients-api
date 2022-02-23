package com.meli.sellerapi.application.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FollowSellerResponse {
    private String buyerUsername;
    private String followingSeller;
    private int newFollowingCount;
}
