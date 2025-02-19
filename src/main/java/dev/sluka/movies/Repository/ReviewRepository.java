package dev.sluka.movies.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import dev.sluka.movies.Entity.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("SELECT r FROM Review r WHERE r.movie.imdbId = :imdbId")

    Optional<Review> findReviewByImdbId(String imdbId);
}