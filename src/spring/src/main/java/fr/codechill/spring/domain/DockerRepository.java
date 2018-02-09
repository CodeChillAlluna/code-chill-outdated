
package fr.codechill.spring.domain;

import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface DockerRepository extends CrudRepository<Docker, Long>{
	List<Docker> findAll();
}