package com.meli.sellerapi.domain.services;

import com.meli.sellerapi.application.dtos.FollowSellerRequest;
import com.meli.sellerapi.application.dtos.FollowSellerResponse;
import com.meli.sellerapi.application.dtos.GetFollowingSellersResponse;
import com.meli.sellerapi.application.dtos.StopFollowingResponse;
import com.meli.sellerapi.domain.exceptions.AlreadyFollowingException;
import com.meli.sellerapi.domain.exceptions.NotFollowingSellerException;
import com.meli.sellerapi.domain.exceptions.UserNotFoundException;

public interface BuyerService {
    FollowSellerResponse followSeller(String sellerUsername, FollowSellerRequest request) throws UserNotFoundException, AlreadyFollowingException;

    StopFollowingResponse stopFollowing(String sellerUsername, String buyerUsername) throws UserNotFoundException, NotFollowingSellerException;

    GetFollowingSellersResponse getFollowingSellers(String buyerUsername) throws UserNotFoundException;
}
