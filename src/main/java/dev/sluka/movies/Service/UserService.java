package dev.sluka.movies.Service;

import java.sql.Date;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
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
    // public static final int MAX_FAILED_ATTEMPTS = 3;
    // private static final long LOCK_TIME_DURATION = 24 * 60 * 60 * 1000;
    @Autowired
    private UserRepository repo;

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

    // public void increaseFailedAttempts(User user) {
    //     int newFailAttempts = user.getFailedAttempt() + 1;
    //     repo.updateFailedAttempts(newFailAttempts, user.getEmail());
    // }
     
    // public void resetFailedAttempts(String email) {
    //     repo.updateFailedAttempts(0, email);
    // }
     
    // public void lock(User user) {
    //     user.setAccountNonLocked(false);
    //     user.setLockTime(new Date(System.currentTimeMillis()));
         
    //     repo.save(user);
    // }
     
    // public boolean unlockWhenTimeExpired(User user) {
    //     long lockTimeInMillis = user.getLockTime().getTime();
    //     long currentTimeInMillis = System.currentTimeMillis();
         
    //     if (lockTimeInMillis + LOCK_TIME_DURATION < currentTimeInMillis) {
    //         user.setAccountNonLocked(true);
    //         user.setLockTime(null);
    //         user.setFailedAttempt(0);
             
    //         repo.save(user);
             
    //         return true;
    //     }
         
    //     return false;
    // }
}
