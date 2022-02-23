package com.meli.sellerapi.infrastructure.repositories;

import com.meli.sellerapi.domain.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface JpaPostRepository extends JpaRepository<Post, Long> {
}
