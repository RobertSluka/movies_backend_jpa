package dev.sluka.movies.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "reviews")
@AllArgsConstructor
@NoArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    // Foreign key to Movie
    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)  // Foreign key reference
    private Movie movie;


    public Review(String content, Movie movie) {
        this.content = content;
        this.movie = movie;
    }
}
