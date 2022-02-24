package com.meli.sellerapi.domain.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class PromotionalPost {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(name = "post")
    private Post post;

    @ManyToOne
    @JoinColumn(name="seller", nullable=false)
    private Seller seller;

    public PromotionalPost(Post post, Seller seller) {
        this.post = post;
        this.seller = seller;
    }

    public String getDescription() {
        return this.post.getDescription();
    }

    public Date getCreationDate() {
        return this.post.getCreationDate();
    }
}
