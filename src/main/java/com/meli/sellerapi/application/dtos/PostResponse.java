package com.meli.sellerapi.application.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {
    private String description;
    private Date creationDate;
    private boolean isPromotion;
    private boolean isCashbackEligible;
}
