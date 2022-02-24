package com.meli.sellerapi.domain.entities;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Seller {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(name = "user", referencedColumnName = "id")
    private User user;

    @ManyToMany(mappedBy = "followingSellers")
    private List<Buyer> followers;

    @OneToMany(mappedBy = "seller")
    private List<Post> posts;

    @OneToMany(mappedBy = "seller")
    private List<PromotionalPost> promotionalPosts;

    public Seller(User user) {
        this.user = user;
        this.followers = new ArrayList<>();
        this.posts = new ArrayList<>();
        this.promotionalPosts = new ArrayList<>();
    }

    public int countFollowers() {
        return this.followers.size();
    }

    public void addFollower(Buyer buyer) {
        this.followers.add(buyer);
    }

    public void removeFollower(Buyer buyer) {
        this.followers.removeIf(b -> b.getUsername().equals(buyer.getUsername()));
    }

    public void addPost(Post post) {
        this.posts.add(post);
    }

    public int countPosts() {
        return this.posts.size();
    }

    public String getUsername(){
        return this.user.getUsername();
    }

    public String getEmail() {
        return this.user.getEmail();
    }

    public Date getCreationDate() {
        return this.user.getCreationDate();
    }

    public int countPromotionalPosts() { return this.promotionalPosts.size(); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Seller seller = (Seller) o;
        return id != null && Objects.equals(id, seller.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}