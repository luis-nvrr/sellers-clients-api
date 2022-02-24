package com.meli.sellerapi.infrastructure.adapters;

import com.meli.sellerapi.domain.entities.PromotionalPost;
import com.meli.sellerapi.domain.repositories.PromotionalPostRepository;
import com.meli.sellerapi.infrastructure.repositories.JpaPromotionalPostRepository;
import org.springframework.stereotype.Repository;

@Repository
public class PromotionalPostRepositoryImpl implements PromotionalPostRepository {
    private final JpaPromotionalPostRepository jpaPromotionalPostRepository;

    public PromotionalPostRepositoryImpl(JpaPromotionalPostRepository jpaPromotionalPostRepository) {
        this.jpaPromotionalPostRepository = jpaPromotionalPostRepository;
    }

    @Override
    public void savePromotionalPost(PromotionalPost post) {
        this.jpaPromotionalPostRepository.save(post);
    }
}
