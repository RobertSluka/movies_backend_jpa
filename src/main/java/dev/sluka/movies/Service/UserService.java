package dev.sluka.movies.Service;

import java.util.Objects;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import dev.sluka.movies.Entity.User;
import dev.sluka.movies.Repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private AuthenticationManager  authenticationManager;
    private final JwtService jwtService;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder,JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public User register(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public String verify(User user) {
       Authentication authentication =  
       authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
         user.getUserName(), user.getPassword()
         )
         );

        // var u = userRepository.findByUserName(user.getUserName());
        if(authentication.isAuthenticated()) 
            return jwtService.generateToken(user);

        return "failure";

    
    }
}
