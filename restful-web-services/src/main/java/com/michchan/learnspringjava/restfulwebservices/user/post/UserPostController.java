package com.michchan.learnspringjava.restfulwebservices.user.post;

import com.michchan.learnspringjava.restfulwebservices.post.Post;
import com.michchan.learnspringjava.restfulwebservices.post.PostNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

// @TODO: Add HATEOAS
@RequestMapping("/users")
@RestController
public class UserPostController {
    @Autowired
    private UserPostDaoService userPostDaoService;

    @GetMapping("/{id}/posts")
    public List<UserPost> getAllPosts(@PathVariable("id") String userId) {
        return userPostDaoService.findAllByUserId(Integer.parseInt(userId));
    }

    @GetMapping("/{id}/posts/{post_id}")
    public UserPost getPost(@PathVariable("id") String userId, @PathVariable("post_id") String postId) {
        UserPost post = userPostDaoService.findOne(
            Integer.parseInt(userId),
            Integer.parseInt(postId)
        );
        if (post == null) {
            throw new PostNotFoundException("id-" + postId);
        }
        return post;
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
