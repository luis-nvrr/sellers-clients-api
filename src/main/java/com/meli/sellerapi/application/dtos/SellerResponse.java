package com.meli.sellerapi.application.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SellerResponse {
    private String username;
    private String email;
    private Date creationDate;
    private List<String> followersUsernames;
    private List<PostResponse> posts;
    private List<PromotionalPostResponse> promotionalPosts;
}
