package microservices.demos;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "movies")
public class Movie {

	@Id
	@SequenceGenerator(initialValue = 10, allocationSize = 1, name = "movieId")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "movieId")
//	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String title;

	@Column(name = "releaseYear")
	private int year;
	private int rating;

	Movie() {
	}

	public Movie(long id, String title, int year, int rating) {
		this(title, year, rating);
		this.id = id;
	}

	public Movie(String title, int year, int rating) {
		super();
		this.title = title;
		this.year = year;
		this.rating = rating;
	}

	public long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public int getYear() {
		return year;
	}

	public int getRating() {
		return rating;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String toString() {
		return String.format("%s (%d)", title, year);
	}

	@Override
	public int hashCode() {
		return Objects.hash(rating, title, year);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Movie other = (Movie) obj;
		return rating == other.rating && Objects.equals(title, other.title) && year == other.year;
	}

}
