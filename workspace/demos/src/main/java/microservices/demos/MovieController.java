package microservices.demos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import microservices.demos.persist.MoviesRepository;

@RestController
public class MovieController {

	@Autowired
	MoviesRepository repo;

	@PostConstruct
	public void init() {
		repo.save(new Movie("The Dark Knight", 2008, 5));
	}

	@GetMapping("/movies/welcome")
	public String welcome() {
		return "Welcome to our favorite movies!";
	}

	@GetMapping("/movies")
	public List<Movie> getMovies(@RequestParam Map<String, String> params) {
		return searchMovies(params);
	}

	@PostMapping(consumes = "application/json", path = "/movies/search")
	public List<Movie> searchMoviesPost(@RequestBody Map<String, String> params) {
		return searchMovies(params);
	}

	public List<Movie> searchMovies(Map<String, String> params) {
		List<String> ignorePaths = new ArrayList<>();
		ignorePaths.add("id");

		Movie criteriaMovie = new Movie();

		if (!params.containsKey("title")) {
			ignorePaths.add("title");
		} else {
			criteriaMovie.setTitle(params.get("title"));
		}

		if (!params.containsKey("year")) {
			ignorePaths.add("year");
		} else {
			criteriaMovie.setYear(Integer.parseInt(params.get("year")));
		}

		if (!params.containsKey("rating")) {
			ignorePaths.add("rating");
		} else {
			criteriaMovie.setRating(Integer.parseInt(params.get("rating")));
		}

		ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreCase().withStringMatcher(StringMatcher.CONTAINING)
				.withIgnorePaths(ignorePaths.toArray(new String[0]));

		Example<Movie> exampleQuery = Example.of(criteriaMovie, matcher);

		return repo.findAll(exampleQuery);
	}

	@GetMapping("/movies/{id}")
	public Movie getMovieById(@PathVariable("id") Long id) {
		return repo.findById(id).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find a movie with the id " + id));
	}

	@PostMapping(consumes = "application/json", path = "/movies")
	@ResponseStatus(HttpStatus.CREATED)
	public Movie addMovie(@RequestBody Movie movie) {
		return repo.save(movie);
	}

	@PutMapping(consumes = "application/json", path = "/movies/{id}")
	public Movie replaceMovie(@PathVariable("id") long id, @RequestBody Movie movie) {
		movie.setId(id);
		return repo.save(movie);
	}

	@PatchMapping(consumes = "application/json", path = "/movies/{id}")
	public Movie updateMovie(@PathVariable("id") long id, @RequestBody Movie movie) {
		Movie target = repo.findById(id).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find a movie with the id " + id));
		if (movie.getRating() != 0) {
			target.setRating(movie.getRating());
		}
		if (movie.getYear() != 0) {
			target.setYear(movie.getYear());
		}

		if (movie.getTitle() != null && !movie.getTitle().isEmpty()) {
			target.setTitle(movie.getTitle());
		}

		target.setId(id);

		return repo.save(target);
	}

	@DeleteMapping("/movies/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteMovie(@PathVariable("id") Long id) {
		repo.deleteById(id);
	}

}
