package com.meli.sellerapi.application.services;

import com.meli.sellerapi.application.dtos.CountFollowersResponse;
import com.meli.sellerapi.application.dtos.GetSellerFollowersResponse;
import com.meli.sellerapi.domain.entities.Buyer;
import com.meli.sellerapi.domain.entities.Seller;
import com.meli.sellerapi.domain.entities.User;
import com.meli.sellerapi.domain.exceptions.UserNotFoundException;
import com.meli.sellerapi.domain.repositories.SellerRepository;
import com.meli.sellerapi.domain.services.SellerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;


class SellerServiceImplTest {
    @Mock
    private SellerRepository sellerRepository;

    private SellerService sellerService;

    private String sellerUsername;
    private Seller seller;

    @BeforeEach
    void setUp() {
        openMocks(this);
        this.sellerService = new SellerServiceImpl(sellerRepository);

        this.sellerUsername = "seller";
        this.seller = new Seller(new User(sellerUsername, "seller@mail.com", new Date()));
        Buyer buyer1 = new Buyer(new User("buyer 1", "buyer1@mail.com", new Date()));
        Buyer buyer2 = new Buyer(new User("buyer 1", "buyer1@mail.com", new Date()));
        seller.addFollower(buyer1);
        seller.addFollower(buyer2);
    }

    @Test
    @DisplayName("should count all the followers the seller has")
    void countFollowers() throws UserNotFoundException {
        when(sellerRepository.findSellerByUsername(sellerUsername)).thenReturn(seller);
        CountFollowersResponse response = this.sellerService.countFollowers(sellerUsername);

        assertEquals(2, response.getFollowersCount());
    }

    @Test
    @DisplayName("should return all followers of a seller")
    void findFollowersBySeller() throws UserNotFoundException {
        when(sellerRepository.findSellerByUsername(sellerUsername)).thenReturn(seller);
        GetSellerFollowersResponse response = this.sellerService.findFollowersBySeller(sellerUsername);

        assertEquals(2, response.getFollowers().size());
    }
}