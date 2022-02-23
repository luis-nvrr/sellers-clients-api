package com.meli.sellerapi.application.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StopFollowingResponse {
    private String unfollowedSeller;
    private String buyerUsername;
    private int buyerNewFollowingCount;
    private int sellerNewFollowersCount;
}
