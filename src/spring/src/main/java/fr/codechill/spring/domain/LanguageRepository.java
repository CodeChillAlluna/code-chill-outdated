
package fr.codechill.spring.domain;

import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface LanguageRepository extends CrudRepository<Language, Long>{
	List<Language> findAll();
}