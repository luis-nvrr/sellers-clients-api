package com.meli.sellerapi.domain.services;

import com.meli.sellerapi.application.dtos.*;
import com.meli.sellerapi.domain.exceptions.UserNotFoundException;

public interface PostService {
    CreatePostResponse createPost(CreatePostRequest createPostRequest) throws UserNotFoundException;

    GetLastTwoWeeksPostsResponse findPostsFromLastTwoWeeks(GetLastTwoWeeksPostsRequest request) throws UserNotFoundException;

    GetPromotionalPostsResponse findAllPromotionalPosts(String sellerUsername) throws UserNotFoundException;

    GetPromotionalPostsCountResponse countPromotionalPosts(String sellerUsername) throws UserNotFoundException;
}
