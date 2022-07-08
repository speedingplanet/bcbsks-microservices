package microservices.demos;

import java.util.Objects;

public class Movie {
	private long id;
	private String title;
	private int year;
	private int rating;

	public Movie(long id, String title, int year, int rating) {
		super();
		this.id = id;
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
