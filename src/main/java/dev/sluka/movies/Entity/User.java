package dev.sluka.movies.Entity;


import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {
 
    @Id
    private int id;
    private String userName;
    private String password;
    private String email;
    // private boolean enabled;

    // @Column(name = "account_non_locked")
    // private boolean accountNonLocked;

    // @Column(name = "failed_attempt")
    // private int failedAttempt;

    // @Column(name = "lock_time")
    // private java.util.Date lockTime;
   


}

