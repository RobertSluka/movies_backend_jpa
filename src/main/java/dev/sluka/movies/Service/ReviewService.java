package dev.sluka.movies.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.sluka.movies.Entity.Movie;
import dev.sluka.movies.Entity.Review;
import dev.sluka.movies.Repository.MovieRepository;
import dev.sluka.movies.Repository.ReviewRepository;
import jakarta.transaction.Transactional;


@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Transactional
    public Review createReview(String reviewBody, String imdbId) {
        Movie movie = movieRepository.findMovieByImdbId(imdbId)
                .orElseThrow(() -> new RuntimeException("Movie not found"));

        // Associate the review with the movie
        Review review = new Review(reviewBody, movie);
        review = reviewRepository.save(review);

        // Add the review to the movie's review list
        movie.getReviews().add(review);
        movieRepository.save(movie);

        return review;
    }
}
