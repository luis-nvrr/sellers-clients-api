package com.meli.sellerapi.infrastructure.repositories;


import com.meli.sellerapi.domain.entities.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface JpaSellerRepository extends JpaRepository<Seller, Long> {
    Seller findSellerByUser_Username(String username);
}
