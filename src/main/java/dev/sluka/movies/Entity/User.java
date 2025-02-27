package dev.sluka.movies.Entity;


import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name= "users")
public class User {
 
    @Id
    // This approach is inefficient and slow under high concurrenc, MySQL doesn't support sequences natively
    @GeneratedValue(strategy= GenerationType.TABLE)
    @Column(name = "user_id")
    private int id;
    private String userName;
    private String password;
    private String email;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
            )
    private Set<Role> roles = new HashSet<>();

    // private boolean enabled;

    // @Column(name = "account_non_locked")
    // private boolean accountNonLocked;

    // @Column(name = "failed_attempt")
    // private int failedAttempt;

    // @Column(name = "lock_time")
    // private java.util.Date lockTime;
   


}

