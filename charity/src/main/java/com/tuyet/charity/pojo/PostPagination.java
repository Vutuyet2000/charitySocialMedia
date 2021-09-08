package com.tuyet.charity.pojo;

import java.util.List;

public class PostPagination {
    private int count;
    private List<Post> posts;

    public PostPagination(int count, List<Post> posts){
        this.count = count;
        this.posts = posts;
    }

    public PostPagination(){
       this(0,null);
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Post> getListPost() {
        return posts;
    }

    public void setListPost(List<Post> listPost) {
        this.posts = listPost;
    }
}
