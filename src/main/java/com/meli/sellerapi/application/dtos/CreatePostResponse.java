package com.meli.sellerapi.application.dtos;

import com.meli.sellerapi.domain.entities.Post;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreatePostResponse {
    private Post post;
}
