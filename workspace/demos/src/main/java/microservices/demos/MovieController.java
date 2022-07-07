package microservices.demos;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MovieController {

	@GetMapping("/movies/welcome")
	public String welcome() {
		return "Welcome to our favorite movies!";
	}
	
	@GetMapping("/movies/one-movie")
	public Movie getMovie() {
		return new Movie(1, "Test Movie", 2022, 4);
	}
}
