package microservices.solutions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	public List<Member> getMembers(@RequestParam Map<String, String> params) {
		/*
		 * filter on firstName filter on firstName and lastName filter on firstName and
		 * lastName and policyHolder
		 * 
		 * And any combinations of the above
		 */

		Predicate<Member> filterMembers = member -> true;
		if (params.containsKey("firstName")) {
			filterMembers = filterMembers.and(member -> {
				String paramFirstName = params.get("firstName");
				String memberFirstName = member.getFirstName();
				return memberFirstName.equals(paramFirstName);
			});
		}

		if (params.containsKey("lastName")) {
			filterMembers = filterMembers.and(member -> member.getLastName().equals(params.get("lastName")));
		}
		
		if (params.containsKey("policyHolder")) {
			boolean paramPolicyHolder = Boolean.parseBoolean(params.get("policyHolder"));
			filterMembers = filterMembers.and(member -> member.isPolicyHolder() == paramPolicyHolder);
		}

		return members.stream().filter(filterMembers).toList();
	}

	@GetMapping("/members/params-as-args")
	public List<Member> getMembersParamsAsArgs(@RequestParam(name = "firstName", required = false) String firstName,
			@RequestParam(name = "lastName", required = false) String lastName,
			@RequestParam(name = "policyHolder", required = false) boolean policyHolder

	) {
		return members;
	}

	@GetMapping("/members/simple-search")
	public List<Member> getMembersSimpleSearch(
			@RequestParam(name = "lastName", required = false) String searchLastName) {
		if (searchLastName == null) {
			return members;
		}
		return members.stream().filter(member -> member.getLastName().equals(searchLastName))
				.collect(Collectors.toList());
	}
}
