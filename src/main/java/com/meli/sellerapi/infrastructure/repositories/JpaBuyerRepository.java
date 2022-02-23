package com.meli.sellerapi.infrastructure.repositories;

import com.meli.sellerapi.domain.entities.Buyer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface JpaBuyerRepository extends JpaRepository<Buyer, Long> {
    Buyer findBuyerByUser_Username(String username);
}
