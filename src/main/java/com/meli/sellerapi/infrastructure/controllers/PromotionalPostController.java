package com.meli.sellerapi.infrastructure.controllers;

import com.meli.sellerapi.application.dtos.CreatePromotionalPostRequest;
import com.meli.sellerapi.application.dtos.CreatePromotionalPostResponse;
import com.meli.sellerapi.application.dtos.GetPromotionalPostsCountResponse;
import com.meli.sellerapi.application.dtos.GetPromotionalPostsResponse;
import com.meli.sellerapi.domain.exceptions.UserNotFoundException;
import com.meli.sellerapi.domain.services.PromotionalPostService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/promotionalPosts")
public class PromotionalPostController {
    private final PromotionalPostService promotionalPostService;

    public PromotionalPostController(PromotionalPostService promotionalPostService) {
        this.promotionalPostService = promotionalPostService;
    }

    @PostMapping
    public ResponseEntity<CreatePromotionalPostResponse> createPost(
            @RequestBody CreatePromotionalPostRequest request)
            throws UserNotFoundException {
        return ResponseEntity.ok(this.promotionalPostService.createPromotionalPost(request));
    }

    @GetMapping(params = {"sellerUsername"})
    public ResponseEntity<GetPromotionalPostsResponse> getPromotionalPostBySeller(
            @RequestParam(name="sellerUsername") String sellerUsername) throws UserNotFoundException {
        return ResponseEntity.ok(this.promotionalPostService.findAllPromotionalPosts(sellerUsername));
    }

    @GetMapping(params = {"sellerUsername", "shouldCount"})
    public ResponseEntity<GetPromotionalPostsCountResponse> countPromotionalPostBySeller(
            @RequestParam(name="sellerUsername") String sellerUsername,
            @RequestParam(name="shouldCount") boolean shouldCount)
            throws UserNotFoundException {
        if (!shouldCount) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(this.promotionalPostService.countPromotionalPosts(sellerUsername));
    }
}
