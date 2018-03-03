package fr.codechill.spring.repository;

import org.springframework.data.repository.CrudRepository;
import java.util.List;
import fr.codechill.spring.model.Docker;

public interface DockerRepository extends CrudRepository<Docker, Long>{
	List<Docker> findAll();
}