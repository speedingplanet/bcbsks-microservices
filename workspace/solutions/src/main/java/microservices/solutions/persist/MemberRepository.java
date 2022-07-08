package microservices.solutions.persist;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import microservices.solutions.Member;

public interface MemberRepository extends CrudRepository<Member, Long> {
	List<Member> findAll();
}
