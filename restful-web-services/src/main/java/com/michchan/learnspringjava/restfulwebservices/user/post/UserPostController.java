package com.michchan.learnspringjava.restfulwebservices.user.post;

import com.michchan.learnspringjava.restfulwebservices.post.Post;
import com.michchan.learnspringjava.restfulwebservices.post.PostNotFoundException;
import com.michchan.learnspringjava.restfulwebservices.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

// @TODO: Add HATEOAS
@RequestMapping("/users")
@RestController
public class UserPostController {
    @Autowired
    private UserPostDaoService userPostDaoService;

    private EntityModel<UserPost> withLinkToPost(EntityModel<UserPost> model, String userId, Post post) {
        WebMvcLinkBuilder linkToPost = linkTo(
            methodOn(this.getClass())
                .getPost(userId, post.getId().toString())
        );
        model.add(linkToPost.withRel("post"));
        return model;
    }

    private EntityModel<UserPost> withLinkToAllPosts(EntityModel<UserPost> model, String userId) {
        WebMvcLinkBuilder linkToPosts = linkTo(
            methodOn(this.getClass())
                .getAllPosts(userId)
        );
        model.add(linkToPosts.withRel("all-posts"));
        return model;
    }

    @GetMapping("/{id}/posts")
    public List<EntityModel<UserPost>> getAllPosts(@PathVariable("id") String userId) {
        List<UserPost> userPosts = userPostDaoService.findAllByUserId(Integer.parseInt(userId));
        List<EntityModel<UserPost>> postModels = new ArrayList<>();

        for (UserPost userPost : userPosts) {
            EntityModel<UserPost> model = EntityModel.of(userPost);
            withLinkToPost(model, userId, userPost);
            postModels.add(model);
        }
        return postModels;
    }

    @GetMapping("/{id}/posts/{post_id}")
    public EntityModel<UserPost> getPost(@PathVariable("id") String userId, @PathVariable("post_id") String postId) {
        UserPost post = userPostDaoService.findOne(
            Integer.parseInt(postId),
            Integer.parseInt(userId)
        );
        if (post == null) {
            throw new PostNotFoundException("id-" + postId);
        }

        EntityModel<UserPost> model = EntityModel.of(post);
        withLinkToAllPosts(model, userId);
        return model;
    }

    @PostMapping("/{id}/posts")
    public ResponseEntity<Object> createUser(@PathVariable("id") String userId, @RequestBody Post post) {
        UserPost savedPost = userPostDaoService.save(new UserPost(Integer.parseInt(userId), post));

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{post_id}")
                .buildAndExpand(savedPost.getId())
                .toUri();

        // Return correct HTTP status code (201) and created location.®
        // e.g. `Location = http://localhost:8080/users/2/posts/4` in response headers.
        return ResponseEntity.created(location).build();
    }
}
