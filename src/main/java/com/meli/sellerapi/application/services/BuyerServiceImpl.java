package com.meli.sellerapi.application.services;

import com.meli.sellerapi.application.dtos.*;
import com.meli.sellerapi.domain.exceptions.AlreadyFollowingException;
import com.meli.sellerapi.domain.entities.Buyer;
import com.meli.sellerapi.domain.entities.Post;
import com.meli.sellerapi.domain.entities.Seller;
import com.meli.sellerapi.domain.exceptions.NotFollowingSellerException;
import com.meli.sellerapi.domain.exceptions.UserNotFoundException;
import com.meli.sellerapi.domain.repositories.BuyerRepository;
import com.meli.sellerapi.domain.repositories.SellerRepository;
import com.meli.sellerapi.domain.services.BuyerService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;


@Service
public class BuyerServiceImpl implements BuyerService {
    private final BuyerRepository buyerRepository;
    private final SellerRepository sellerRepository;
    private final Logger logger = Logger.getLogger(BuyerService.class.getName());

    public BuyerServiceImpl(BuyerRepository buyerRepository, SellerRepository sellerRepository) {
        this.buyerRepository = buyerRepository;
        this.sellerRepository = sellerRepository;
    }

    @Override
    public FollowSellerResponse followSeller(String buyerUsername, FollowSellerRequest request)
            throws UserNotFoundException, AlreadyFollowingException {
        Buyer buyer = buyerRepository.findBuyerByUsername(buyerUsername);
        if (buyer == null) throw new UserNotFoundException("the buyer doesn't exist");

        Seller seller = sellerRepository.findSellerByUsername(request.getSellerUsername());
        if (seller == null) throw new UserNotFoundException("the seller doesn't exist");

        if (buyer.isFollowing(seller)) throw new AlreadyFollowingException("the buyer already follows this seller");

        buyer.addFollowing(seller);
        seller.addFollower(buyer);
        this.sellerRepository.saveSeller(seller);
        this.buyerRepository.saveBuyer(buyer);

        return new FollowSellerResponse(buyer.getUsername(), seller.getUsername(), buyer.countFollowing());
    }

    @Override
    public StopFollowingResponse stopFollowing(String sellerUsername, String buyerUsername)
            throws UserNotFoundException, NotFollowingSellerException {
        Buyer buyer = this.buyerRepository.findBuyerByUsername(buyerUsername);
        if (buyer == null) throw new UserNotFoundException("the buyer doesn't exist");

        Seller seller = this.sellerRepository.findSellerByUsername(sellerUsername);
        if (seller == null) throw new UserNotFoundException("the seller doesn't exist");

        if (!buyer.isFollowing(seller)) throw new NotFollowingSellerException("the buyer isn't following the seller");

        buyer.stopFollowing(seller);
        seller.removeFollower(buyer);

        buyerRepository.saveBuyer(buyer);
        sellerRepository.saveSeller(seller);

        return new StopFollowingResponse(seller.getUsername(), buyer.getUsername(), buyer.countFollowing(), seller.countFollowers());
    }

    @Override
    public GetFollowingSellersResponse getFollowingSellers(String buyerUsername) throws UserNotFoundException {
        Buyer buyer = buyerRepository.findBuyerByUsername(buyerUsername);
        if (buyer == null) throw new UserNotFoundException("the buyer doesn't exist");

        return buildGetFollowingSellersResponse(buyer);
    }

    private GetFollowingSellersResponse buildGetFollowingSellersResponse(Buyer buyer) {
        GetFollowingSellersResponse response = new GetFollowingSellersResponse();
        response.setBuyerUsername(buyer.getUsername());
        response.setFollowingCount(buyer.countFollowing());

        List<SellerResponse> followingSellers = new ArrayList<>();
        buyer.getFollowingSellers().forEach(seller -> followingSellers.add(buildSellerResponse(seller)));
        response.setFollowingSellers(followingSellers);

        return response;
    }

    private SellerResponse buildSellerResponse(Seller seller) {
        SellerResponse sellerResponse = new SellerResponse();
        sellerResponse.setUsername(seller.getUsername());
        sellerResponse.setEmail(seller.getEmail());
        sellerResponse.setCreationDate(seller.getCreationDate());

        List<String> followers = seller.getFollowers().stream()
                .map(Buyer::getUsername).collect(Collectors.toList());
        sellerResponse.setFollowersUsernames(followers);

        List<PostResponse> posts = seller.getPosts().stream()
                .map(this::buildPostResponse).collect(Collectors.toList());
        sellerResponse.setPosts(posts);

        List<PostResponse> promotionalPosts = seller.getPromotionalPosts().stream()
                .map(this::buildPostResponse).collect(Collectors.toList());
        sellerResponse.setPromotionalPosts(promotionalPosts);

        return sellerResponse;
    }

    private PostResponse buildPostResponse(Post post) {
        PostResponse postResponse = new PostResponse();
        postResponse.setDescription(post.getDescription());
        postResponse.setCreationDate(post.getCreationDate());
        postResponse.setPromotion(post.isPromotion());
        postResponse.setCashbackEligible(post.isCashbackEligible());
        return postResponse;
    }

}
