package dev.sluka.movies.Controller;

import java.util.Objects;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.sluka.movies.DTO.UserDTO;
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
public ResponseEntity<UserDTO> register(@RequestBody UserDTO userDto) {
    UserDTO registeredUser = userService.registerUser(userDto);
    return ResponseEntity.ok(registeredUser);
}


@PostMapping("/admin/register")
public User registerAdmin(@RequestBody UserDTO user, @RequestParam String roleName) {
    return userService.registerUserWithRole(user.getUserName(),user.getPassword(), roleName);
}
@PostMapping("/login")
public String login(@RequestBody UserDTO user) {

    return userService.verify(user);
  
}
}
