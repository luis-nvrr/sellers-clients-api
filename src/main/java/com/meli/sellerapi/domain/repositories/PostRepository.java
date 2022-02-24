package com.meli.sellerapi.domain.repositories;

import com.meli.sellerapi.domain.entities.Post;

public interface PostRepository {
    void savePost(Post post);
}
