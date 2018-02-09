
package fr.codechill.spring.domain;

import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface CodeChillUserRepository extends CrudRepository<CodeChillUser, Long>{
	List<CodeChillUser> findAll();
}