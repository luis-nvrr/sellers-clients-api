package com.meli.sellerapi.infrastructure.controllers;

import com.meli.sellerapi.application.dtos.FollowSellerRequest;
import com.meli.sellerapi.application.dtos.FollowSellerResponse;
import com.meli.sellerapi.application.dtos.GetFollowingSellersResponse;
import com.meli.sellerapi.application.dtos.StopFollowingResponse;
import com.meli.sellerapi.domain.exceptions.AlreadyFollowingException;
import com.meli.sellerapi.domain.exceptions.NotFollowingSellerException;
import com.meli.sellerapi.domain.exceptions.UserNotFoundException;
import com.meli.sellerapi.domain.services.BuyerService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/buyers")
public class BuyerController {

    private final BuyerService buyerService;

    public BuyerController(BuyerService buyerService) {
        this.buyerService = buyerService;
    }

    @PostMapping("/{buyerUsername}/following")
    public ResponseEntity<FollowSellerResponse> followSeller(@PathVariable String buyerUsername,
                                                             @RequestBody FollowSellerRequest request)
            throws UserNotFoundException, AlreadyFollowingException {
        return ResponseEntity.ok(this.buyerService.followSeller(buyerUsername, request));
    }

    @DeleteMapping("/{buyerUsername}/following/{sellerUsername}")
    public ResponseEntity<StopFollowingResponse> stopFollowingSeller(@PathVariable String buyerUsername,
                                                                     @PathVariable String sellerUsername)
            throws UserNotFoundException, NotFollowingSellerException {
        return ResponseEntity.ok(this.buyerService.stopFollowing(sellerUsername, buyerUsername));
    }

    @GetMapping("/{buyerUsername}/following")
    public ResponseEntity<GetFollowingSellersResponse> getFollowingSellers(@PathVariable String buyerUsername)
            throws UserNotFoundException {
        return ResponseEntity.ok(this.buyerService.getFollowingSellers(buyerUsername));
    }
}
