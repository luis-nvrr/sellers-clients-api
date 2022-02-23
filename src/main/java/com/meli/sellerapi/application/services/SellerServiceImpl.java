package com.meli.sellerapi.application.services;

import com.meli.sellerapi.application.dtos.BuyerResponse;
import com.meli.sellerapi.domain.entities.Buyer;
import com.meli.sellerapi.domain.entities.Seller;
import com.meli.sellerapi.domain.exceptions.UserNotFoundException;
import com.meli.sellerapi.domain.repositories.SellerRepository;
import com.meli.sellerapi.domain.services.SellerService;
import com.meli.sellerapi.application.dtos.CountFollowersResponse;
import com.meli.sellerapi.application.dtos.GetSellerFollowersResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


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

        return new CountFollowersResponse(seller.getUsername(),seller.countFollowers());
    }

    @Override
    public GetSellerFollowersResponse findFollowersBySeller(String sellerUsername) throws UserNotFoundException {
        Seller seller = this.sellerRepository.findSellerByUsername(sellerUsername);
        if (seller == null) throw new UserNotFoundException("the seller doesn't exist");

        return  buildFindFollowersBySellerResponse(seller);
    }

    private GetSellerFollowersResponse buildFindFollowersBySellerResponse(Seller seller) {
        GetSellerFollowersResponse response = new GetSellerFollowersResponse();
        response.setSellerUsername(seller.getUsername());

        List<BuyerResponse> followers = seller.getFollowers().stream()
                .map(this::buildBuyerResponse).collect(Collectors.toList());
        response.setFollowers(followers);

        return response;
    }

    private BuyerResponse buildBuyerResponse(Buyer buyer) {
        BuyerResponse buyerResponse = new BuyerResponse();
        buyerResponse.setUsername(buyer.getUser().getUsername());
        buyerResponse.setEmail(buyer.getUser().getEmail());
        buyerResponse.setCreationDate(buyer.getUser().getCreationDate());

        List<String> followingSellers = buyer.getFollowingSellers().stream()
                .map(Seller::getUsername).collect(Collectors.toList());
        buyerResponse.setFollowingSellersUsernames(followingSellers);

        return buyerResponse;
    }

}