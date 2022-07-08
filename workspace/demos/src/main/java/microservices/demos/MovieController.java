package microservices.demos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import microservices.demos.persist.MoviesRepository;

@RestController
public class MovieController {

	@Autowired
	MoviesRepository repo;
	
	private Movie[] movies;

	@PostConstruct
	public void init() {
		repo.save(new Movie(7, "The Dark Knight", 2008, 5));
		movies = repo.findAll().toArray(new Movie[0]);
	}

	@GetMapping("/movies/welcome")
	public String welcome() {
		return "Welcome to our favorite movies!";
	}

	@GetMapping("/movies/one-movie")
	public Movie getMovie() {
		return new Movie(1, "Test Movie", 2022, 4);
	}
	
	@GetMapping("/movies/all-params")
	public Map<String, String> getAllParameters(@RequestParam Map<String, String> params) {
		return params;
	}

	@GetMapping("/movies")
	public List<Movie> getMovies(@RequestParam(name = "year", required = false) Integer year) {
		List<Movie> filteredMovies = new ArrayList<>();
		if (year == null) {
			return Arrays.asList(movies);
		}
		for (Movie m: movies) {
			if(m.getYear() == year) filteredMovies.add(m);
		}
		
		return filteredMovies;
		/*
		return Arrays.stream(movies)
				.filter(movie -> movie.getYear() == year)
//				.toArray(Movie[]::new);
				.toArray(Collectors.toList());
				*/
	}

	@GetMapping("/movies/as-list")
	public List<Movie> getMoviesAsList() {
		return Arrays.stream(movies).collect(Collectors.toList());
	}

}
