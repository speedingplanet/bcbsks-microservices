package microservices.solutions;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "members")
public class Member {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String firstName;
	private String lastName;
	private boolean policyHolder;
	
	protected Member() {}

	public Member(long id, String firstName, String lastName, boolean policyHolder) {
		this(firstName, lastName, policyHolder);
		this.id = id;
	}
	
	public Member(String firstName, String lastName, boolean policyHolder) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.policyHolder = policyHolder;
	}

	public long getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public boolean isPolicyHolder() {
		return policyHolder;
	}

	@Override
	public int hashCode() {
		return Objects.hash(firstName, id, policyHolder, lastName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Member other = (Member) obj;
		return Objects.equals(firstName, other.firstName) && id == other.id && policyHolder == other.policyHolder
				&& Objects.equals(lastName, other.lastName);
	}
}
