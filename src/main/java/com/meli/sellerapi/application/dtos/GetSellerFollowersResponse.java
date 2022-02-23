package com.meli.sellerapi.application.dtos;

import com.meli.sellerapi.domain.entities.Buyer;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class GetSellerFollowersResponse {
    List<Buyer> followers;
}
