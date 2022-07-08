package microservices.demos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
		
		List<String> ignorePaths = new ArrayList<>();
		ignorePaths.add("id");
		
		Movie criteriaMovie = new Movie();

		if (! params.containsKey("title")) {
			ignorePaths.add("title");
		} else {
			criteriaMovie.setTitle(params.get("title"));
		}
		
		if (! params.containsKey("year")) {
			ignorePaths.add("year");
		} else {
			criteriaMovie.setYear(Integer.parseInt(params.get("year")));
		}
		
		if (! params.containsKey("rating")) {
			ignorePaths.add("rating");
		} else {
			criteriaMovie.setRating(Integer.parseInt(params.get("rating")));
		}
		
		ExampleMatcher matcher = ExampleMatcher.matching()
				.withIgnoreCase()
				.withStringMatcher(StringMatcher.CONTAINING)
				.withIgnorePaths(ignorePaths.toArray(new String[0]));
		
		
		Example<Movie> exampleQuery = Example.of(criteriaMovie, matcher);
		
		return repo.findAll(exampleQuery);
	}

}
