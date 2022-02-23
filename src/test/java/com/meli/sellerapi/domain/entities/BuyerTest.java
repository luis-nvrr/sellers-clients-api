package com.meli.sellerapi.domain.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class BuyerTest {

    Buyer buyer;

    @BeforeEach
    void setUp(){
        this.buyer = new Buyer(new User("buyer", "buyer@mail.com", new Date()));
    }

    @Test
    @DisplayName("should add a seller to the buyer's following list")
    void followSeller() {
        Seller seller = new Seller(new User("seller", "seller@mail.com", new Date()));
        int countBefore = buyer.countFollowing();

        buyer.addFollowing(seller);
        assertEquals(buyer.countFollowing(), countBefore+1);
    }

    @Test
    void countFollowing() {
        Seller seller = new Seller(new User("seller", "seller@mail.com", new Date()));
        int countBefore = buyer.countFollowing();

        buyer.addFollowing(seller);
        assertEquals(countBefore, 0);
        assertEquals(buyer.countFollowing(), 1);
    }
}