package microservices.demos.persist;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import microservices.demos.Movie;

public interface MoviesRepository extends CrudRepository<Movie, Long> {
	List<Movie> findAll();
}
