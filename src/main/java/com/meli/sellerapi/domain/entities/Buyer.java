package com.meli.sellerapi.domain.entities;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Buyer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(name = "user", referencedColumnName = "id")
    private User user;

    @ManyToMany
    @JoinTable(
            name = "buyers_following",
            joinColumns = @JoinColumn(name = "buyer_id"),
            inverseJoinColumns = @JoinColumn(name = "seller_id"))
    private List<Seller> followingSellers;

    public Buyer(User user) {
        this.user = user;
        this.followingSellers = new ArrayList<>();
    }

    public void addFollowing(Seller seller) {
        this.followingSellers.add(seller);
    }

    public void stopFollowing(Seller seller) {
        this.followingSellers.removeIf(s -> s.getUsername().equals(seller.getUsername()));
    }

    public int countFollowing() {
        return this.followingSellers.size();
    }

    public String getUsername() {
        return this.user.getUsername();
    }

    public boolean isFollowing(Seller seller) {
        return this.followingSellers.contains(seller);
    }
}
