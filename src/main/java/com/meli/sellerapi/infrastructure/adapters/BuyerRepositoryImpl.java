package com.meli.sellerapi.infrastructure.adapters;

import com.meli.sellerapi.domain.entities.Buyer;
import com.meli.sellerapi.domain.repositories.BuyerRepository;
import com.meli.sellerapi.infrastructure.repositories.JpaBuyerRepository;
import org.springframework.stereotype.Repository;

@Repository
public class BuyerRepositoryImpl implements BuyerRepository {
    private final JpaBuyerRepository jpaBuyerRepository;

    public BuyerRepositoryImpl(JpaBuyerRepository jpaBuyerRepository) {
        this.jpaBuyerRepository = jpaBuyerRepository;
    }

    @Override
    public void saveBuyer(Buyer buyer) {
        this.jpaBuyerRepository.save(buyer);
    }

    @Override
    public Buyer findBuyerByUsername(String buyerUsername) {
        return this.jpaBuyerRepository.findBuyerByUser_Username(buyerUsername);
    }
}
