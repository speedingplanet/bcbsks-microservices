package microservices.demos;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(MovieController.class)
class MovieWebLayerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void testGetMoviesWelcome() throws Exception {
		mockMvc.perform(get("/movies/welcome"))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(content().string(containsString("Welcome")));
	}


}
