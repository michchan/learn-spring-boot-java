package com.michchan.learnspringjava.restfulwebservices.user;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserDaoService userDaoService;

    private EntityModel<User> withLinkToUser(EntityModel<User> model, User user) {
        WebMvcLinkBuilder linkToUser = linkTo(methodOn(this.getClass()).getUser(user.getId().toString()));
        model.add(linkToUser.withRel("user"));
        return model;
    }

    private EntityModel<User> withLinkToAllUsers(EntityModel<User> model) {
        WebMvcLinkBuilder linkToUsers = linkTo(methodOn(this.getClass()).getAllUsers());
        model.add(linkToUsers.withRel("all-users"));
        return model;
    }

    @GetMapping("/users")
    public List<EntityModel<User>> getAllUsers() {
        List<User> users = userDaoService.findAll();
        List<EntityModel<User>> userModels = new ArrayList<>();

        for (User user : users) {
            EntityModel<User> model = EntityModel.of(user);
            withLinkToUser(model, user);
            userModels.add(model);
        }
        return userModels;
    }

    @GetMapping("/users/{id}")
    public EntityModel<User> getUser(@PathVariable String id) {
        User user = userDaoService.findOne(Integer.parseInt(id));
        if (user == null) {
            throw new UserNotFoundException("id-" + id);
        }

        EntityModel<User> model = EntityModel.of(user);
        withLinkToAllUsers(model);
        return model;
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
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

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable String id) {
        User user = userDaoService.deleteById(Integer.parseInt(id));
        if (user == null) {
            throw new UserNotFoundException("id-" + id);
        }
    }
}
