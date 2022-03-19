package com.michchan.learnspringjava.restfulwebservices.post;

import java.util.ArrayList;
import java.util.List;

public class Post {
    private Integer id;

    private String title;
    private String description;
    private List<String> media;

    public Post(Integer id, String title, String description, List<String> media) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.media = media;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getMedia() {
        return media;
    }
    public void setMedia(List<String> media) {
        this.media = media;
    }
}
