package com.meli.sellerapi.application.services;

import com.meli.sellerapi.application.dtos.FollowSellerRequest;
import com.meli.sellerapi.application.dtos.FollowSellerResponse;
import com.meli.sellerapi.application.dtos.GetFollowingSellersResponse;
import com.meli.sellerapi.application.dtos.StopFollowingResponse;
import com.meli.sellerapi.domain.entities.Buyer;
import com.meli.sellerapi.domain.entities.Seller;
import com.meli.sellerapi.domain.entities.User;
import com.meli.sellerapi.domain.exceptions.AlreadyFollowingException;
import com.meli.sellerapi.domain.exceptions.NotFollowingSellerException;
import com.meli.sellerapi.domain.exceptions.UserNotFoundException;
import com.meli.sellerapi.domain.repositories.BuyerRepository;
import com.meli.sellerapi.domain.repositories.SellerRepository;
import com.meli.sellerapi.domain.services.BuyerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;


class BuyerServiceImplTest {
    @Mock
    private BuyerRepository buyerRepository;
    @Mock
    private SellerRepository sellerRepository;

    private BuyerService buyerService;

    private String buyerUsername;
    private Buyer buyer;

    @BeforeEach
    void setUp() {
        openMocks(this);
        this.buyerService = new BuyerServiceImpl(buyerRepository, sellerRepository);
        this.buyerUsername = "buyer";
        this.buyer = new Buyer(new User(buyerUsername, "buyer@mail.com", new Date()));
    }

    @Test
    @DisplayName("should add a seller to the buyer's following list")
    void followSeller() throws UserNotFoundException, AlreadyFollowingException {
        String sellerUsername = "seller";
        Seller seller = new Seller(new User(sellerUsername, "seller@mail.com", new Date()));

        when(buyerRepository.findBuyerByUsername(buyerUsername)).thenReturn(buyer);
        when(sellerRepository.findSellerByUsername(sellerUsername)).thenReturn(seller);

        FollowSellerRequest request = new FollowSellerRequest(buyerUsername);
        FollowSellerResponse response = this.buyerService.followSeller(sellerUsername, request);

        assertEquals(1,response.getNewFollowingCount());
    }

    @Test
    @DisplayName("should remove a seller from the buyer's following list")
    void stopFollowing() throws UserNotFoundException, NotFollowingSellerException {
        Seller seller1 = new Seller(new User("seller1", "seller1@mail.com", new Date()));
        Seller seller2 = new Seller(new User("seller2", "seller2@mail.com", new Date()));
        this.buyer.addFollowing(seller1);
        this.buyer.addFollowing(seller2);

        when(buyerRepository.findBuyerByUsername(buyerUsername)).thenReturn(buyer);
        when(sellerRepository.findSellerByUsername(seller1.getUsername())).thenReturn(seller1);

        StopFollowingResponse response = this.buyerService.stopFollowing(seller1.getUsername(), buyerUsername);

        assertEquals(1,response.getBuyerNewFollowingCount());
        assertEquals(0,response.getSellerNewFollowersCount());
    }

    @Test
    @DisplayName("should find all followed sellers")
    void getFollowedSellers() throws UserNotFoundException {
        Seller seller1 = new Seller(new User("seller1", "seller1@mail.com", new Date()));
        Seller seller2 = new Seller(new User("seller2", "seller2@mail.com", new Date()));
        Seller seller3 = new Seller(new User("seller3", "seller3@mail.com", new Date()));
        buyer.addFollowing(seller1);
        buyer.addFollowing(seller2);
        buyer.addFollowing(seller3);

        when(buyerRepository.findBuyerByUsername(buyerUsername)).thenReturn(buyer);
        GetFollowingSellersResponse response = this.buyerService.getFollowingSellers(buyerUsername);

        assertEquals(3, response.getFollowingCount());
    }


}