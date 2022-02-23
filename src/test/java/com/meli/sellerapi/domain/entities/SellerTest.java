package com.meli.sellerapi.domain.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class SellerTest {
    private String sellerUsername;
    private Seller seller;

    @BeforeEach
    void setUp() {
        this.sellerUsername = "seller";
        this.seller = new Seller(new User(sellerUsername, "seller@mail.com", new Date()));
    }

    @Test
    @DisplayName("should return the followers count")
    void countFollowers() {
        Buyer buyer1 = new Buyer(new User("buyer1", "buyer1@mail.com", new Date()));
        Buyer buyer2 = new Buyer(new User("buyer2", "buyer2@mail.com", new Date()));
        seller.addFollower(buyer1);
        seller.addFollower(buyer2);

        assertEquals(2, seller.countFollowers());
    }

    @Test
    void addFollower() {
        Buyer buyer1 = new Buyer(new User("buyer1", "buyer1@mail.com", new Date()));
        Buyer buyer2 = new Buyer(new User("buyer2", "buyer2@mail.com", new Date()));
        int countBefore = seller.countFollowers();

        seller.addFollower(buyer1);
        seller.addFollower(buyer2);

        assertEquals(0, countBefore);
        assertEquals(2, seller.countFollowers());
    }

    @Test
    void addPost() {
        Post post = new Post();
        seller.addPost(post);

        assertEquals(1, seller.countPosts());
    }

    @Test
    void countPosts() {
        Post post = new Post();
        seller.addPost(post);

        assertEquals(1, seller.countPosts());
    }

    @Test
    void getUsername() {
        assertEquals(this.sellerUsername, this.seller.getUsername());
    }
}