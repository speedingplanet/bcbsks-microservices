package microservices.solutions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class MemberControllerTest {
	@LocalServerPort
	private int port;
	
	@Autowired
	TestRestTemplate client;

	@Test
	void testGetMembersStatus() {
		String testUrl = String.format("http://localhost:%d/members/status", port);
		String response = client.getForObject(testUrl, String.class);
//		assertEquals("All members available", response);
		assertThat(response).contains("members");
	}
	
	@Test
	void testGetMembers() throws Exception {
		String testUrl = String.format("http://localhost:%d/members", port);
		Member testMember = client.getForObject(testUrl, Member.class);
		assertThat(testMember.getFirstName()).isEqualTo("John");
		assertEquals(testMember, new Member(1, "John", "Paxton", true));
		assertThat(testMember).isEqualTo(new Member(1, "John", "Paxton", true));
		
	}

}
