package dev.sluka.movies.Service;

import java.sql.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import dev.sluka.movies.DTO.UserDTO;
import dev.sluka.movies.Entity.Role;
import dev.sluka.movies.Entity.User;
import dev.sluka.movies.Repository.RoleRepository;
import dev.sluka.movies.Repository.UserRepository;
import jakarta.transaction.Transactional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private AuthenticationManager  authenticationManager;
    private final JwtService jwtService;
    public static final int MAX_FAILED_ATTEMPTS = 3;
    private static final long LOCK_TIME_DURATION = 1 * 60 * 60 * 1000;
    @Autowired
    private UserRepository repo;

    @Autowired
    private RoleRepository roleRepository;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder,JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Transactional
    public UserDTO registerUser(UserDTO userDTO) {
        User user = new User();
        user.setUserName(userDTO.getUserName());
        user.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        user.setEmail(userDTO.getEmail());
    
        Set<Role> roles = new HashSet<>();
    
        // ✅ If userDTO.getRoles() is null or empty, assign default "USER" role
        if (userDTO.getRoles() == null || userDTO.getRoles().isEmpty()) {
            Role defaultRole = roleRepository.findByName("USER");
            if (defaultRole == null) {
                throw new RuntimeException("Default USER role not found in the database");
            }
            roles.add(defaultRole);
        } else {
            for (String roleName : userDTO.getRoles()) {
                Role role = roleRepository.findByName(roleName);
                if (role == null) {
                    throw new RuntimeException("Role " + roleName + " not found in the database");
                }
                roles.add(role);
            }
        }
    
        user.setRoles(roles);
        User savedUser = userRepository.save(user);
    
        // Convert User to UserDTO before returning
        return new UserDTO(savedUser);
    }
    


    public User registerUserWithRole(String username, String password, String roleName) {
        User user = new User();
        user.setUserName(username);
        user.setPassword(bCryptPasswordEncoder.encode(password));
    
        Role role = roleRepository.findByName(roleName);
        if (role == null) {
            throw new RuntimeException("Role " + roleName + " not found in the database");
        }
    
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);
    
        return userRepository.save(user);
    }
    
   
    public String verify(UserDTO userDTO) {

        User user = userRepository.findByUserName(userDTO.getUserName());
       Authentication authentication =  
       authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
         user.getUserName(), userDTO.getPassword()
         )
         );

        // var u = userRepository.findByUserName(user.getUserName());
        if(authentication.isAuthenticated()) 
            return jwtService.generateToken(new UserDTO(user));

        return "failure";

    
    }

    public User getByEmail(String email){
        User user = userRepository.findByEmail(email);
        return user;
    }

    public User getByUserName(String username){
        User user = userRepository.findByUserName(username);
        return user;
    }

    public void deleteUser(int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.getRoles().clear();
        userRepository.save(user);
    
        userRepository.delete(user);
    }

    public void increaseFailedAttempts(User user) {
        int newFailAttempts = user.getFailedAttempt() + 1;
        repo.updateFailedAttempts(newFailAttempts, user.getEmail());
    }
     
    public void resetFailedAttempts(String email) {
        repo.updateFailedAttempts(0, email);
    }
     
    public void lock(User user) {
        user.setAccountNonLocked(false);
        user.setLockTime(new Date(System.currentTimeMillis()));
         
        repo.save(user);
    }
     
    public boolean unlockWhenTimeExpired(User user) {
        long lockTimeInMillis = user.getLockTime().getTime();
        long currentTimeInMillis = System.currentTimeMillis();
         
        if (lockTimeInMillis + LOCK_TIME_DURATION < currentTimeInMillis) {
            user.setAccountNonLocked(true);
            user.setLockTime(null);
            user.setFailedAttempt(0);
             
            repo.save(user);
             
            return true;
        }
         
        return false;
    }
    @Transactional
    public void updateUserPassword(String username, String newPassword) {
        User user = userRepository.findByUserName(username);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
    
        String hashedPassword = bCryptPasswordEncoder.encode(newPassword);
       userRepository.updatePassword(username, hashedPassword);
    }
   
}
