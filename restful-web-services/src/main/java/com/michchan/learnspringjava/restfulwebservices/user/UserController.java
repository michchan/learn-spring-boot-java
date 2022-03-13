package com.michchan.learnspringjava.restfulwebservices.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserDaoService userDaoService;

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userDaoService.findAll();
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable String id) {
        User user = userDaoService.findOne(Integer.parseInt(id));
        if (user == null) {
            throw new UserNotFoundException("id-" + id);
        }
        return user;
    }

    @PostMapping("/users")
    public ResponseEntity<Object> createUser(@RequestBody User user) {
        User savedUser = userDaoService.save(user);

        URI location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(savedUser.getId())
            .toUri();

        // Return correct HTTP status code (201) and created location.
        // e.g. `Location = http://localhost:8080/users/4` in response headers.
        return ResponseEntity.created(location).build();
    }
}
