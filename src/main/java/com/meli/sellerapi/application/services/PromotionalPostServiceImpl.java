package com.meli.sellerapi.application.services;

import com.meli.sellerapi.application.dtos.*;
import com.meli.sellerapi.domain.entities.Post;
import com.meli.sellerapi.domain.entities.PromotionalPost;
import com.meli.sellerapi.domain.entities.Seller;
import com.meli.sellerapi.domain.exceptions.UserNotFoundException;
import com.meli.sellerapi.domain.repositories.PostRepository;
import com.meli.sellerapi.domain.repositories.PromotionalPostRepository;
import com.meli.sellerapi.domain.repositories.SellerRepository;
import com.meli.sellerapi.domain.services.PromotionalPostService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PromotionalPostServiceImpl implements PromotionalPostService {
    private PromotionalPostRepository promotionalPostRepository;
    private PostRepository postRepository;
    private SellerRepository sellerRepository;

    @Override
    public CreatePromotionalPostResponse createPromotionalPost(CreatePromotionalPostRequest request)
            throws UserNotFoundException {
        Seller seller = this.sellerRepository.findSellerByUsername(request.getSellerUsername());
        if (seller == null) throw new UserNotFoundException("the seller doesn't exist");

        Post post = new Post(seller, request.getDescription(), new Date()); // TODO refactor date creation to custom format
        this.postRepository.savePost(post);

        PromotionalPost promotionalPost = new PromotionalPost(post, seller);
        this.promotionalPostRepository.savePromotionalPost(promotionalPost);

        return new CreatePromotionalPostResponse(
                new PostResponse(promotionalPost.getPost().getDescription(),
                        promotionalPost.getPost().getCreationDate()));
    }

    @Override
    public GetPromotionalPostsResponse findAllPromotionalPosts(String sellerUsername) throws UserNotFoundException {
        Seller seller = this.sellerRepository.findSellerByUsername(sellerUsername);
        if (seller == null) throw new UserNotFoundException("the seller doesn't exist");

        List<PromotionalPostResponse> promotionalPosts = seller.getPromotionalPosts().stream()
                .map(this::buildPromotionalPostResponse)
                .collect(Collectors.toList());

        return new GetPromotionalPostsResponse(promotionalPosts);
    }

    @Override
    public GetPromotionalPostsCountResponse countPromotionalPosts(String sellerUsername) throws UserNotFoundException {
        Seller seller = this.sellerRepository.findSellerByUsername(sellerUsername);
        if (seller == null) throw new UserNotFoundException("the seller doesn't exist");

        return new GetPromotionalPostsCountResponse(seller.getUsername(), seller.countPromotionalPosts());
    }

    private PromotionalPostResponse buildPromotionalPostResponse(PromotionalPost promotionalPost) {
        PromotionalPostResponse response = new PromotionalPostResponse();
        response.setDescription(promotionalPost.getDescription());
        response.setCreationDate(promotionalPost.getCreationDate());
        return response;
    }
}
