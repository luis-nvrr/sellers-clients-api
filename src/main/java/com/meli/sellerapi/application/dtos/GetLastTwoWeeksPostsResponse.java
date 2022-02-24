package com.meli.sellerapi.application.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class GetLastTwoWeeksPostsResponse {
    private String buyerUsername;
    private List<PostResponse> followingSellersPosts;
    private int postsCount;
}
