package com.meli.sellerapi.domain.repositories;

import com.meli.sellerapi.domain.entities.Seller;

public interface SellerRepository {
    Seller findSellerByUsername(String username);
    void saveSeller(Seller seller);
}
