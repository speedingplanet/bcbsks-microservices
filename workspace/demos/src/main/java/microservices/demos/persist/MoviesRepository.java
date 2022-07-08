package microservices.demos.persist;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
 
import microservices.demos.Movie;

public interface MoviesRepository extends JpaRepository<Movie, Long> {
	List<Movie> findAll();
	List<Movie> findByTitle(String title);
	List<Movie> findByYear(int year);
	List<Movie> findByRating(int rating);
	List<Movie> findByTitleAndYearAndRating(String title, int year, int rating);
	List<Movie> findByRatingGreaterThan(int rating);
}
