package com.meli.sellerapi.infrastructure.adapters;

import com.meli.sellerapi.domain.entities.Seller;
import com.meli.sellerapi.domain.repositories.SellerRepository;
import com.meli.sellerapi.infrastructure.repositories.JpaSellerRepository;
import org.springframework.stereotype.Repository;

@Repository
public class SellerRepositoryImpl implements SellerRepository {
    private final JpaSellerRepository jpaSellerRepository;

    public SellerRepositoryImpl(JpaSellerRepository jpaSellerRepository) {
        this.jpaSellerRepository = jpaSellerRepository;
    }

    @Override
    public Seller findSellerByUsername(String username) {
        return this.jpaSellerRepository.findSellerByUser_Username(username);
    }

    @Override
    public void saveSeller(Seller seller) {
        this.jpaSellerRepository.save(seller);
    }
}
