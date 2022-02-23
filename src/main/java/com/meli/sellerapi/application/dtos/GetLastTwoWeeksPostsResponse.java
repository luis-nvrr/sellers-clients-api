package com.meli.sellerapi.application.dtos;

import com.meli.sellerapi.domain.entities.Post;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class GetLastTwoWeeksPostsResponse {
    private String buyerUsername;
    private List<Post> posts;
}
