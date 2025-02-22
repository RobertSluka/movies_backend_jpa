package dev.sluka.movies.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import dev.sluka.movies.Entity.Movie;
import dev.sluka.movies.Entity.User;


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUserName(String userName);
    
//     // ?1 and ?2 are positional parameters.
//  @Query("UPDATE User u SET u.failedAttempt = ?1 WHERE u.email = ?2")
//     @Modifying
//     public void updateFailedAttempts(int failAttempts, String email);


}