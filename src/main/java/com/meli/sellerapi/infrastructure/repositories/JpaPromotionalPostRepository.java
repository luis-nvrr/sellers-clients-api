package com.meli.sellerapi.infrastructure.repositories;

import com.meli.sellerapi.domain.entities.PromotionalPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaPromotionalPostRepository extends JpaRepository<PromotionalPost, Long> {
}
