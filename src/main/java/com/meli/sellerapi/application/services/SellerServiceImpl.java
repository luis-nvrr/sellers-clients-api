package com.meli.sellerapi.application.services;

import com.meli.sellerapi.domain.entities.Seller;
import com.meli.sellerapi.domain.exceptions.UserNotFoundException;
import com.meli.sellerapi.domain.repositories.SellerRepository;
import com.meli.sellerapi.domain.services.SellerService;
import com.meli.sellerapi.application.dtos.CountFollowersResponse;
import com.meli.sellerapi.application.dtos.GetSellerFollowersResponse;
import org.springframework.stereotype.Service;


@Service
public class SellerServiceImpl implements SellerService {
    private final SellerRepository sellerRepository;

    public SellerServiceImpl(SellerRepository sellerRepository) {
        this.sellerRepository = sellerRepository;
    }

    @Override
    public CountFollowersResponse countFollowers(String sellerUsername) throws UserNotFoundException {
        Seller seller = this.sellerRepository.findSellerByUsername(sellerUsername);
        if (seller == null) throw new UserNotFoundException("the seller doesn't exist");

        return new CountFollowersResponse(seller.countFollowers());
    }

    @Override
    public GetSellerFollowersResponse findFollowersBySeller(String sellerUsername) throws UserNotFoundException {
        Seller seller = this.sellerRepository.findSellerByUsername(sellerUsername);
        if (seller == null) throw new UserNotFoundException("the seller doesn't exist");

        return new GetSellerFollowersResponse(seller.getFollowers());
    }

}