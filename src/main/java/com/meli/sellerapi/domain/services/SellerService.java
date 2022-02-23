package com.meli.sellerapi.domain.services;

import com.meli.sellerapi.domain.exceptions.UserNotFoundException;
import com.meli.sellerapi.application.dtos.CountFollowersResponse;
import com.meli.sellerapi.application.dtos.GetSellerFollowersResponse;

public interface SellerService {

    CountFollowersResponse countFollowers(String sellerUsername) throws UserNotFoundException;

    GetSellerFollowersResponse findFollowersBySeller(String sellerUsername) throws UserNotFoundException;
}
