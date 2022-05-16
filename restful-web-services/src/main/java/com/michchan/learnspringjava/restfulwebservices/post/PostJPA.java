package com.michchan.learnspringjava.restfulwebservices.post;

import com.michchan.learnspringjava.restfulwebservices.user.User;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;

@Entity
public class PostJPA {
    @Id
    @GeneratedValue
    private Integer id;

    private String title;
    private String description;

    @Column(columnDefinition = "TEXT")
    private String media; // List of comma separated URLs

    // It should not try to fetch user unless post.getUser is called.
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

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
        return Arrays.asList(media.split(","));
    }
    public void setMedia(List<String> media) {
        this.media = String.join(",", media);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "PostJPA{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", media=" + media +
                '}';
    }
}
