package dev.sluka.movies.Controller;

import java.util.Objects;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import dev.sluka.movies.Entity.User;
import dev.sluka.movies.Repository.UserRepository;
import dev.sluka.movies.Service.UserService;

@RestController
public class UserController {

private final UserService userService;

private final UserRepository userRepository;

public UserController(UserRepository userRepository, UserService userService) {
    this.userRepository = userRepository;
    this.userService = userService;
}

@PostMapping("/register")
public User register(@RequestBody User user) {
    return userService.register(user);
}
@PostMapping("/login")
public String login(@RequestBody User user) {

    return userService.verify(user);
  
}
}
