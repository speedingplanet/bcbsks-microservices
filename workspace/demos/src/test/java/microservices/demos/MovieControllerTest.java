package microservices.demos;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class MovieControllerTest {
	@LocalServerPort
	private int port;
	
	@Autowired
	TestRestTemplate client;

	@Test
	void testGetMovie() {
		String url = String.format("http://localhost:%d/movies/one-movie", port);
		Movie testMovie = client.getForObject(url, Movie.class);
		assertEquals(testMovie.getTitle(), "Test Movie");
		assertEquals(testMovie.getId(), 1);
	}

}
