package com.meli.sellerapi.application.services;

import com.meli.sellerapi.application.dtos.*;
import com.meli.sellerapi.domain.entities.Buyer;
import com.meli.sellerapi.domain.entities.Post;
import com.meli.sellerapi.domain.entities.Seller;
import com.meli.sellerapi.domain.exceptions.UserNotFoundException;
import com.meli.sellerapi.domain.repositories.BuyerRepository;
import com.meli.sellerapi.domain.repositories.SellerRepository;
import com.meli.sellerapi.domain.services.PostService;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class PostServiceImpl implements PostService {
    private final SellerRepository sellerRepository;
    private final BuyerRepository buyerRepository;


    public PostServiceImpl(SellerRepository sellerRepository, BuyerRepository buyerRepository
    ) {
        this.sellerRepository = sellerRepository;
        this.buyerRepository = buyerRepository;
    }

    @Override
    public CreatePostResponse createPost(CreatePostRequest createPostRequest) throws UserNotFoundException {
        Seller seller = this.sellerRepository.findSellerByUsername(createPostRequest.getSellerUsername());
        if (seller == null) throw new UserNotFoundException("seller doesn't exist");

        Post post = new Post(createPostRequest.getDescription(), new Date(), createPostRequest.isPromotion(), createPostRequest.isCashbackEligible());
        seller.addPost(post);

        this.sellerRepository.saveSeller(seller);
        return new CreatePostResponse(post);
    }

    @Override
    public GetLastTwoWeeksPostsResponse findPostsFromLastTwoWeeks(GetLastTwoWeeksPostsRequest request) throws UserNotFoundException {
        Buyer buyer = this.buyerRepository.findBuyerByUsername(request.getBuyerUsername());
        if (buyer == null) throw new UserNotFoundException("buyer doesn't exist");

        List<Seller> sellersFollowing = buyer.getFollowingSellers();
        List<Post> postsFromSellers = new ArrayList<>();


        for (Seller seller: sellersFollowing) {
            postsFromSellers.addAll(seller.getPosts());
        }

        List<Post> postsFromLastTwoWeeks = new ArrayList<>();
        for (Post post : postsFromSellers) {
            if (!post.isAtMostTwoWeeksOld()) continue;
            postsFromLastTwoWeeks.add(post);
        }

        postsFromLastTwoWeeks.sort((o1, o2) -> o2.getCreationDate().compareTo(o1.getCreationDate()));

        return new GetLastTwoWeeksPostsResponse(request.getBuyerUsername(), postsFromLastTwoWeeks);
    }

    @Override
    public GetPromotionalPostsResponse findAllPromotionalPosts(String sellerUsername) throws UserNotFoundException {
        Seller seller = this.sellerRepository.findSellerByUsername(sellerUsername);
        if (seller == null) throw new UserNotFoundException("the seller doesn't exist");

        return new GetPromotionalPostsResponse(seller.getPromotionalPosts());
    }

    @Override
    public GetPromotionalPostsCountResponse countPromotionalPosts(String sellerUsername) throws UserNotFoundException {
        Seller seller = this.sellerRepository.findSellerByUsername(sellerUsername);
        if (seller == null) throw new UserNotFoundException("the seller doesn't exist");

        return new GetPromotionalPostsCountResponse(seller.countPromotionalPosts());
    }
}
