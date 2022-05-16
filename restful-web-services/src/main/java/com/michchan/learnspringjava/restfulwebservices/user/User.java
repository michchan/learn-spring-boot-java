package com.michchan.learnspringjava.restfulwebservices.user;

import com.michchan.learnspringjava.restfulwebservices.post.PostJPA;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Schema(description = "All details about the user")
@Entity
public class User {

    @Id
    @GeneratedValue // A value I want the database to generate
    private Integer id;

    @Size(min=2, max=100, message = "User's name must be characters with length between 2 to 100")
    @Schema(description = "Name should have at least 2 characters")
    private String name;

    @Past
    @Schema(description = "Birthdate should be in the past")
    private Date birthdate;

    // Do not create a separate user_posts table
    @OneToMany(mappedBy="user")
    private List<PostJPA> posts;

    // This is required by @Entity
    protected User() {
    }

    public User(Integer id, String name, Date birthdate) {
        this.id = id;
        this.name = name;
        this.birthdate = birthdate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public List<PostJPA> getPosts() {
        return posts;
    }

    public void setPosts(List<PostJPA> posts) {
        this.posts = posts;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthdate=" + birthdate +
                '}';
    }
}
