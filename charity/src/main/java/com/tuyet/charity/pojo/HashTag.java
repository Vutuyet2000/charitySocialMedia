package com.tuyet.charity.pojo;

import javax.persistence.*;
import java.io.Serializable;

//neu luu enum duoc thi khoi lam lop nay
//tra list enum cho client

public class HashTag implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer hashTagId;

    private String content;

    public Integer getHashTagId() {
        return hashTagId;
    }

    public void setHashTagId(Integer hashTagId) {
        this.hashTagId = hashTagId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
