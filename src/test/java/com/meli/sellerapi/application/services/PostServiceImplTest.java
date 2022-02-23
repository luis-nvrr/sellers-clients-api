package com.meli.sellerapi.application.services;

import com.meli.sellerapi.application.dtos.*;
import com.meli.sellerapi.domain.entities.Buyer;
import com.meli.sellerapi.domain.entities.Post;
import com.meli.sellerapi.domain.entities.Seller;
import com.meli.sellerapi.domain.entities.User;
import com.meli.sellerapi.domain.exceptions.InvalidDateException;
import com.meli.sellerapi.domain.exceptions.UserNotFoundException;
import com.meli.sellerapi.domain.repositories.BuyerRepository;
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

    private PostService postService;

    @BeforeEach
    void setUp() {
        openMocks(this);
        this.postService = new PostServiceImpl(sellerRepository, buyerRepository);
    }

    @Test
    @DisplayName("should add a new post to a seller")
    void createPost() throws UserNotFoundException {
        String sellerUsername = "seller1";
        Seller seller = new Seller(new User(sellerUsername, "seller1@mail.com", new Date()));
        CreatePostRequest request = new CreatePostRequest("new post", false, false, sellerUsername);

        when(sellerRepository.findSellerByUsername(sellerUsername)).thenReturn(seller);

        CreatePostResponse response = this.postService.createPost(request);

        assertEquals(1, seller.countPosts());
        assertEquals(request.getDescription(), response.getPost().getDescription());
    }

    @Test
    @DisplayName("should add a new promotional post to a seller")
    void createPromotionalPost() throws UserNotFoundException {
        String sellerUsername = "seller1";
        Seller seller = new Seller(new User(sellerUsername, "seller1@mail.com", new Date()));
        CreatePostRequest request = new CreatePostRequest("new post", true, false, sellerUsername);

        when(sellerRepository.findSellerByUsername(sellerUsername)).thenReturn(seller);

        CreatePostResponse response = this.postService.createPost(request);

        assertEquals(1, seller.countPromotionalPosts());
        assertEquals(request.getDescription(), response.getPost().getDescription());
    }

    @Test
    @DisplayName("should return all posts from within the last two weeks, from the followers of a buyer")
    void findPostsFromLastTwoWeeks() throws InvalidDateException, UserNotFoundException {
        String sellerUsername = "seller1";
        Seller seller = new Seller(new User(sellerUsername, "seller1@mail.com", new Date()));
        seller.addPost(new Post("post1", DateTools.formatStringToDate("23/02/2022"), false, false));
        seller.addPost(new Post("post2", DateTools.formatStringToDate("22/02/2022"), false, false));
        seller.addPost(new Post("post3", DateTools.formatStringToDate("12/07/2020"), false, false));

        String buyerUsername = "buyer";
        Buyer buyer = new Buyer(new User(buyerUsername, "buyer@mail.com", new Date()));
        buyer.addFollowing(seller);

        GetLastTwoWeeksPostsRequest request = new GetLastTwoWeeksPostsRequest(buyerUsername,true);
        when(sellerRepository.findSellerByUsername(sellerUsername)).thenReturn(seller);
        when(buyerRepository.findBuyerByUsername(buyerUsername)).thenReturn(buyer);

        GetLastTwoWeeksPostsResponse response = this.postService.findPostsFromLastTwoWeeks(request);

        assertEquals(2, response.getPosts().size());
        assertTrue(DateTools.dateIsAfterOrEqualToDate(response.getPosts().get(0).getCreationDate(),response.getPosts().get(1).getCreationDate()));
    }

    @Test
    @DisplayName("should find all promotional posts by seller")
    void findAllPromotionalPosts() throws UserNotFoundException {
        Seller seller = new Seller(new User("seller", "seller@mail.com", new Date()));
        Post post1 = new Post("new post", new Date(), true, false);
        Post post2 = new Post("new post", new Date(), false, false);

        seller.addPost(post1);
        seller.addPost(post2);

        when(sellerRepository.findSellerByUsername(seller.getUsername())).thenReturn(seller);
        GetPromotionalPostsResponse response = this.postService.findAllPromotionalPosts(seller.getUsername());

        assertEquals(1, response.getPromotionalPosts().size());
    }

    @Test
    @DisplayName("should find all promotional posts by seller")
    void countPromotionalPosts() throws UserNotFoundException {
        Seller seller = new Seller(new User("seller", "seller@mail.com", new Date()));
        Post post1 = new Post("new post", new Date(), true, false);
        Post post2 = new Post("new post", new Date(), false, false);

        seller.addPost(post1);
        seller.addPost(post2);

        when(sellerRepository.findSellerByUsername(seller.getUsername())).thenReturn(seller);
        GetPromotionalPostsCountResponse response = this.postService.countPromotionalPosts(seller.getUsername());

        assertEquals(1, response.getPromotionalPostsCount());
    }
}