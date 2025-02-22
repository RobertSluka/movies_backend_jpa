package dev.sluka.movies.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.sluka.movies.DTO.MovieDTO;
import dev.sluka.movies.Entity.Movie;
import dev.sluka.movies.Service.MovieService;
import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    // @Autowired
    // private MovieService movieService;

    // @GetMapping
    // @PreAuthorize("hasAuthority('ROLE_USER')") // Ensure only authenticated users can access
    // @Transactional 
    // public List<Movie> getAllMovies() {
    //     return movieService.allMovies();
    // }

    // @GetMapping("/{imdbId}")
    // @PreAuthorize("hasAuthority('ROLE_USER')") // Ensure only authenticated users can access
    // public ResponseEntity<Movie> getMovieByImdbId(@PathVariable String imdbId) {
    //     Optional<Movie> movie = movieService.singleMovie(imdbId);
    //     return movie.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    // }

 @Autowired
    private MovieService movieService;

    @GetMapping
    public List<MovieDTO> getAllMovies() {
        return movieService.getAllMovies();
    }

    @GetMapping("/{imdbId}")
    public ResponseEntity<MovieDTO> getMovieByImdbId(@PathVariable String imdbId) {
        MovieDTO movieDTO = movieService.getMovieByImdbId(imdbId);
        return ResponseEntity.ok(movieDTO);
    }

    @GetMapping("/movies/{id}")
public ResponseEntity<Movie> getMovie(@PathVariable Long id) {
    Movie movie = movieService.getMovieWithBackdrops(id);
    return ResponseEntity.ok(movie);
}

}