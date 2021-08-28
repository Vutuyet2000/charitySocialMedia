package com.tuyet.charity.pojo;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "post_auction")
public class PostAuction implements Serializable {
    @Id
    @Column(name = "post_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer postAuctionId;

    private boolean active = true;

    private Integer winner;

    @Column(precision = 20, scale = 2, columnDefinition = "DECIMAL(20,2)")
    private BigDecimal cost;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id")
    @MapsId
    private Post post;

    public Integer getPostAuctionId() {
        return postAuctionId;
    }

    public void setPostAuctionId(Integer postAuctionId) {
        this.postAuctionId = postAuctionId;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Integer getWinner() {
        return winner;
    }

    public void setWinner(Integer winner) {
        this.winner = winner;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    @JsonBackReference(value = "post-postauction")
    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
