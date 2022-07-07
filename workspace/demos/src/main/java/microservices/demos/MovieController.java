package microservices.demos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MovieController {

	private Movie[] movies;

//	@PostConstruct
//	private void initializeMovies() {

	public MovieController() {
		movies = new Movie[5];
		movies[0] = new Movie(1, "Raiders of the Lost Ark", 1981, 5);
		movies[1] = new Movie(2, "WarGames", 1983, 4);
		movies[2] = new Movie(3, "Thor: Love and Thunder", 2022, 4);
		movies[3] = new Movie(4, "Star Wars", 1977, 4);
		movies[4] = new Movie(5, "2001", 1968, 4);
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
