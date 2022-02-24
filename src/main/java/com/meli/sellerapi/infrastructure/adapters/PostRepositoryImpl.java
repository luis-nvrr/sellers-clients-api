package com.meli.sellerapi.infrastructure.adapters;

import com.meli.sellerapi.domain.entities.Post;
import com.meli.sellerapi.domain.repositories.PostRepository;
import com.meli.sellerapi.infrastructure.repositories.JpaPostRepository;
import org.springframework.stereotype.Repository;

@Repository
public class PostRepositoryImpl implements PostRepository {
    private final JpaPostRepository jpaPostRepository;

    public PostRepositoryImpl(JpaPostRepository jpaPostRepository) {
        this.jpaPostRepository = jpaPostRepository;
    }

    @Override
    public void savePost(Post post) {
        this.jpaPostRepository.save(post);
    }
}
