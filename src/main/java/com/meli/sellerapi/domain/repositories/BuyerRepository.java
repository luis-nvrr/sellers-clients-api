package com.meli.sellerapi.domain.repositories;

import com.meli.sellerapi.domain.entities.Buyer;

public interface BuyerRepository {
    void saveBuyer(Buyer buyer);
    Buyer findBuyerByUsername(String buyerUsername);
}
