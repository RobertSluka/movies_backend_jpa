package dev.sluka.movies.DTO;

import dev.sluka.movies.Entity.Review;
import lombok.Data;

@Data
public class ReviewDTO {
    private String body;
    private String movieTitle; // Avoid lazy initialization by using title instead of full Movie object

    public ReviewDTO(Review review) {
        this.body = review.getBody();
        this.movieTitle = review.getMovie().getTitle(); // Avoid full Movie serialization
    }
}

