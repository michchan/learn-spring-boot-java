package com.michchan.learnspringjava.restfulwebservices.user;

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
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UserJPAController {
    @Autowired
    private UserRepository userRepository;

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

    @GetMapping("/jpa/users")
    public List<EntityModel<User>> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<EntityModel<User>> userModels = new ArrayList<>();

        for (User user : users) {
            EntityModel<User> model = EntityModel.of(user);
            withLinkToUser(model, user);
            userModels.add(model);
        }
        return userModels;
    }

    @GetMapping("/jpa/users/{id}")
    public EntityModel<User> getUser(@PathVariable String id) {
        Optional<User> user = userRepository.findById(Integer.parseInt(id));
        if (!user.isPresent()) {
            throw new UserNotFoundException("id-" + id);
        }

        EntityModel<User> model = EntityModel.of(user.get());
        withLinkToAllUsers(model);
        return model;
    }

    @PostMapping("/jpa/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User savedUser = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(savedUser.getId())
            .toUri();

        // Return correct HTTP status code (201) and created location.
        // e.g. `Location = http://localhost:8080/users/4` in response headers.
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/jpa/users/{id}")
    public void deleteUser(@PathVariable String id) {
        userRepository.deleteById(Integer.parseInt(id));
    }
}
