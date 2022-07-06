package microservices.demos;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class HelloWorldTest {
	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate client;
	
	@Test
	public void testHelloWorld() throws Exception {
		String testUrl = String.format("http://localhost:%d/hello-world", port);
		String response = client.getForObject(testUrl, String.class);
		assertEquals(HelloWorldController.STATUS_MESSAGE, response);
		assertThat(response).contains("Hello");
	}
}
