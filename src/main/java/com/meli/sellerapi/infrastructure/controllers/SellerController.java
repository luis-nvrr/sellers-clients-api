package com.meli.sellerapi.infrastructure.controllers;

import com.meli.sellerapi.application.dtos.CountFollowersResponse;
import com.meli.sellerapi.application.dtos.GetSellerFollowersResponse;
import com.meli.sellerapi.domain.exceptions.UserNotFoundException;
import com.meli.sellerapi.domain.services.SellerService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/sellers")
public class SellerController {
    private final SellerService sellerService;

    public SellerController(SellerService sellerService) {
        this.sellerService = sellerService;
    }

    @GetMapping(value = "/{sellerUsername}/followers", params = { "shouldCount" })
    public ResponseEntity<CountFollowersResponse> countFollowers(
            @PathVariable String sellerUsername,
            @RequestParam(name = "shouldCount", required = true) boolean shouldCount) throws UserNotFoundException {
        if (!shouldCount) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(this.sellerService.countFollowers(sellerUsername));
    }

    @GetMapping(value = "/{sellerUsername}/followers")
    public ResponseEntity<GetSellerFollowersResponse> getSellerFollowers(
            @PathVariable String sellerUsername) throws UserNotFoundException {
        return ResponseEntity.ok(this.sellerService.findFollowersBySeller(sellerUsername));
    }
}
