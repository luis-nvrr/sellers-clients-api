package com.meli.sellerapi.domain.services;

import com.meli.sellerapi.application.dtos.CreatePromotionalPostRequest;
import com.meli.sellerapi.application.dtos.CreatePromotionalPostResponse;
import com.meli.sellerapi.application.dtos.GetPromotionalPostsCountResponse;
import com.meli.sellerapi.application.dtos.GetPromotionalPostsResponse;
import com.meli.sellerapi.domain.exceptions.UserNotFoundException;

public interface PromotionalPostService {

    CreatePromotionalPostResponse createPromotionalPost(CreatePromotionalPostRequest request) throws UserNotFoundException;

    GetPromotionalPostsResponse findAllPromotionalPosts(String sellerUsername) throws UserNotFoundException;

    GetPromotionalPostsCountResponse countPromotionalPosts(String sellerUsername) throws UserNotFoundException;
}
