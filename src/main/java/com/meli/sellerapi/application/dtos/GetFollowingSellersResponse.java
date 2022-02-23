package com.meli.sellerapi.application.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetFollowingSellersResponse {
    private String buyerUsername;
    private List<SellerResponse> followingSellers;
    private int followingCount;
}
