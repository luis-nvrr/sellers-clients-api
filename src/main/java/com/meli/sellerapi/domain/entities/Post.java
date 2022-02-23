package com.meli.sellerapi.domain.entities;

import com.meli.sellerapi.domain.utils.DateTools;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="seller_id", nullable=false)
    private Seller seller;

    private String description;
    private Date creationDate;
    private boolean isPromotion;
    private boolean isCashbackEligible;

    public Post( String description, Date creationDate, boolean isPromotion, boolean isCashbackEligible) {
        this.description = description;
        this.creationDate = creationDate;
        this.isPromotion = isPromotion;
        this.isCashbackEligible = isCashbackEligible;
    }

    public boolean isAtMostTwoWeeksOld() {
        Date dateTwoWeeksAgo = DateTools.getDateTwoWeeksAgoFromNow();
        return DateTools.dateIsAfterOrEqualToDate(creationDate, dateTwoWeeksAgo);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Post post = (Post) o;
        return id != null && Objects.equals(id, post.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

