package microservices.solutions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import microservices.solutions.persist.MemberRepository;

@RestController
public class MemberController {

	private List<Member> members;
	
	@Autowired
	private MemberRepository repo;

	@PostConstruct
	public void startup() {
		repo.save(new Member("John", "Paxton", true));
		repo.save(new Member("Amir", "Shami", true));
		repo.save(new Member("Clint", "Brubakken", true));
		repo.save(new Member("Joshua", "Wurtz", false));
		repo.save(new Member("Kelsi", "Henderson", false));
		
		members = repo.findAll();
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
