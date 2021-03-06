package com.meli.sellerapi.application.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetSellerFollowersResponse {
    String sellerUsername;
    List<BuyerResponse> followers;
}
