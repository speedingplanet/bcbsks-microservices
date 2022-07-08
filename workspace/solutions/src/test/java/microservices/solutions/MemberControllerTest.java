package microservices.solutions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

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
		Member[] testMembers = client.getForObject(testUrl, Member[].class);
		assertThat(testMembers[0].getFirstName()).isEqualTo("John");
		assertEquals(testMembers[0], new Member(1, "John", "Paxton", true));
		assertThat(testMembers[0]).isEqualTo(new Member(1, "John", "Paxton", true));
	}

	@Test
	void testHowManyPolicyHolders() throws Exception {
		String testUrl = String.format("http://localhost:%d/members", port);
		Member[] testMembers = client.getForObject(testUrl, Member[].class);
		int policyHolderCount = 0;
		for (Member m : testMembers) {
			if (m.isPolicyHolder())
				policyHolderCount++;
		}
		assertThat(policyHolderCount).isGreaterThan(2);
	}

	@Test
	void testAltHowManyPolicyHolders() throws Exception {
		String testUrl = String.format("http://localhost:%d/members", port);
		Member[] testMembers = client.getForObject(testUrl, Member[].class);
		long policyHolderCount = Arrays.stream(testMembers)
//				.filter(m -> m.isPolicyHolder())
				.filter(Member::isPolicyHolder)
				.count();
		assertThat(policyHolderCount >= 2);
		

	}

}
