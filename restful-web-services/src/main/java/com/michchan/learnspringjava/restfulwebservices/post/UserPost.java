package com.michchan.learnspringjava.restfulwebservices.post;

import java.util.List;

public class UserPost extends Post {
    private Integer userId;

    public UserPost(Integer userId, Post post) {
        super(
            post.getId(),
            post.getTitle(),
            post.getDescription(),
            post.getMedia()
        );
        this.userId = userId;
    }

    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
