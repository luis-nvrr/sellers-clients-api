package com.meli.sellerapi.application.services;

import com.meli.sellerapi.application.dtos.*;
import com.meli.sellerapi.domain.entities.Buyer;
import com.meli.sellerapi.domain.entities.Post;
import com.meli.sellerapi.domain.entities.Seller;
import com.meli.sellerapi.domain.entities.User;
import com.meli.sellerapi.domain.exceptions.InvalidDateException;
import com.meli.sellerapi.domain.exceptions.UserNotFoundException;
import com.meli.sellerapi.domain.repositories.BuyerRepository;
import com.meli.sellerapi.domain.repositories.PostRepository;
import com.meli.sellerapi.domain.repositories.SellerRepository;
import com.meli.sellerapi.domain.services.PostService;
import com.meli.sellerapi.domain.utils.DateTools;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class PostServiceImplTest {
    @Mock
    private SellerRepository sellerRepository;
    @Mock
    private BuyerRepository buyerRepository;
    @Mock
    private PostRepository postRepository;

    private PostService postService;

    @BeforeEach
    void setUp() {
        openMocks(this);
        this.postService = new PostServiceImpl(sellerRepository, buyerRepository, postRepository);
    }

    @Test
    @DisplayName("should add a new post to a seller")
    void createPost() throws UserNotFoundException {
        String sellerUsername = "seller1";
        Seller seller = new Seller(new User(sellerUsername, "seller1@mail.com", new Date()));
        CreatePostRequest request = new CreatePostRequest("new post", sellerUsername);

        when(sellerRepository.findSellerByUsername(sellerUsername)).thenReturn(seller);

        CreatePostResponse response = this.postService.createPost(request);

        assertEquals(1, seller.countPosts());
        assertEquals(request.getDescription(), response.getPost().getDescription());
    }

    @Test
    @DisplayName("should return all posts from within the last two weeks, from the followers of a buyer")
    void findPostsFromLastTwoWeeks() throws InvalidDateException, UserNotFoundException {
        String sellerUsername = "seller1";
        Seller seller = new Seller(new User(sellerUsername, "seller1@mail.com", new Date()));
        seller.addPost(new Post(seller, "post1", DateTools.formatStringToDate("23/02/2022")));
        seller.addPost(new Post(seller, "post2", DateTools.formatStringToDate("22/02/2022")));
        seller.addPost(new Post(seller, "post3", DateTools.formatStringToDate("12/07/2020")));

        String buyerUsername = "buyer";
        Buyer buyer = new Buyer(new User(buyerUsername, "buyer@mail.com", new Date()));
        buyer.addFollowing(seller);

        GetLastTwoWeeksPostsRequest request = new GetLastTwoWeeksPostsRequest(buyerUsername);
        when(sellerRepository.findSellerByUsername(sellerUsername)).thenReturn(seller);
        when(buyerRepository.findBuyerByUsername(buyerUsername)).thenReturn(buyer);

        GetLastTwoWeeksPostsResponse response = this.postService.findPostsFromLastTwoWeeks(request);

        assertEquals(2, response.getPostsCount());
        assertTrue(DateTools.dateIsAfterOrEqualToDate(response.getFollowingSellersPosts().get(0).getCreationDate(),
                response.getFollowingSellersPosts().get(1).getCreationDate()));
    }
}