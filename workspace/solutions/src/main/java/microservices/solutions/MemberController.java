package microservices.solutions;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {

	@GetMapping("/members/status")
	public String getMembersStatus() {
		return "All members available";
	}
	
	@GetMapping("/members")
	public Member getMember() {
		return new Member(1, "John", "Paxton", true);
	}
}
