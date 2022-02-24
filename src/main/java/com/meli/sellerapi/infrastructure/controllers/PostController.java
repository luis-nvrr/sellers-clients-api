package com.meli.sellerapi.infrastructure.controllers;

import com.meli.sellerapi.application.dtos.*;
import com.meli.sellerapi.domain.exceptions.UserNotFoundException;
import com.meli.sellerapi.domain.services.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<CreatePostResponse> createPost(
            @RequestBody CreatePostRequest request) throws UserNotFoundException {
        return ResponseEntity.ok(this.postService.createPost(request));
    }

    @GetMapping
    public ResponseEntity<GetLastTwoWeeksPostsResponse> getPostsFromLastTwoWeeks(GetLastTwoWeeksPostsRequest request)
            throws UserNotFoundException {
        return ResponseEntity.ok(this.postService.findPostsFromLastTwoWeeks(request));
    }
}
