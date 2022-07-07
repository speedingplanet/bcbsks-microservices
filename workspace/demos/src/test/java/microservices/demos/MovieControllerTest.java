package microservices.demos;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class MovieControllerTest {
	@LocalServerPort
	private int port;

	@Autowired
	TestRestTemplate client;

	String urlTemplate = "http://localhost:%d/%s";

	@Test
	void testGetMovie() {
		String url = String.format("http://localhost:%d/movies/one-movie", port);
		Movie testMovie = client.getForObject(url, Movie.class);
		assertEquals(testMovie.getTitle(), "Test Movie");
		assertEquals(testMovie.getId(), 1);
	}

	@Test
	void testGetMovies() {
		String url = String.format(urlTemplate, port, "movies");
		Movie[] testMovies = client.getForObject(url, Movie[].class);
		assertEquals(5, testMovies.length);
		assertEquals(1, testMovies[0].getId());
	}

	@Test
	void testGetMoviesAsList() {
		String url = String.format(urlTemplate, port, "movies/as-list");
		ResponseEntity<List<Movie>> moviesListResponse = client.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Movie>>() {
				});
		List<Movie> moviesList = moviesListResponse.getBody();
		assertEquals(5, moviesList.size());
	}

	@Test
	void testGetMoviesAsListEasier() {
		String url = String.format(urlTemplate, port, "movies");
		Movie[] testMovies = client.getForObject(url, Movie[].class);
		
		// Two ways to convert an Array to a List
		List<Movie> alsoMoviesList = Arrays.asList(testMovies);
		List<Movie> testMoviesList = Arrays.stream(testMovies).collect(Collectors.toList());
		assertEquals(5, testMoviesList.size());
		assertEquals(1, testMoviesList.get(0).getId());
	}

	@Test
	void testGetMoviesAsSet() {
		String url = String.format(urlTemplate, port, "movies/as-list");
		ResponseEntity<Set<Movie>> moviesListResponse = client.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<Set<Movie>>() {
				});
		Set<Movie> moviesList = moviesListResponse.getBody();
		assertEquals(5, moviesList.size());
	}

}
