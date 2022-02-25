package com.meli.sellerapi.application.services;

import com.meli.sellerapi.application.dtos.*;
import com.meli.sellerapi.domain.entities.*;
import com.meli.sellerapi.domain.exceptions.UserNotFoundException;
import com.meli.sellerapi.domain.repositories.BuyerRepository;
import com.meli.sellerapi.domain.repositories.PostRepository;
import com.meli.sellerapi.domain.repositories.SellerRepository;
import com.meli.sellerapi.domain.services.PostService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {
    private final SellerRepository sellerRepository;
    private final BuyerRepository buyerRepository;
    private final PostRepository postRepository;

    @Override
    public CreatePostResponse createPost(CreatePostRequest createPostRequest) throws UserNotFoundException {
        Seller seller = this.sellerRepository.findSellerByUsername(createPostRequest.getSellerUsername());
        if (seller == null) throw new UserNotFoundException("seller doesn't exist");

        Post post = new Post(seller, createPostRequest.getDescription(), new Date());
        this.postRepository.savePost(post);

        seller.addPost(post);
        this.sellerRepository.saveSeller(seller);

        return new CreatePostResponse(buildPostResponse(post));
    }

    // TODO: refactor this method into a builder class
    private PostResponse buildPostResponse(Post post) {
        PostResponse postResponse = new PostResponse();
        postResponse.setDescription(post.getDescription());
        postResponse.setCreationDate(post.getCreationDate());
        postResponse.setSellerUsername(post.getSeller().getUsername());
        return postResponse;
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

        List<PostResponse> postsFromLastTwoWeeks = postsFromSellers.stream().
                filter(Post::isAtMostTwoWeeksOld)
                .sorted((o1, o2) -> o2.getCreationDate().compareTo(o1.getCreationDate())) // TODO refactor this sort into a class
                .map(this::buildPostResponse)
                .collect(Collectors.toList());

        return new GetLastTwoWeeksPostsResponse(request.getBuyerUsername(), postsFromLastTwoWeeks, postsFromLastTwoWeeks.size());
    }
}
