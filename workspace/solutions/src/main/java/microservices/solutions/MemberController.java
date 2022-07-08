package microservices.solutions;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {
	
	private List<Member> members;
	
	public MemberController() {
		members = new ArrayList<>();
		members.add(new Member(1, "John", "Paxton", true));
		members.add(new Member(2, "Amir", "Shami", true));
		members.add(new Member(3, "Clint", "Brubakken", true));
		members.add(new Member(4, "Joshua", "Wurtz", false));
		members.add(new Member(5, "Kelsi", "Henderson", false));
	}

	@GetMapping("/members/status")
	public String getMembersStatus() {
		return "All members available";
	}
	
	@GetMapping("/members")
	public List<Member> getMember() {
		return members;
	}
}
