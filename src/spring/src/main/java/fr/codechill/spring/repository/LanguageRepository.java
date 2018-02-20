package fr.codechill.spring.repository;

import org.springframework.data.repository.CrudRepository;
import java.util.List;
import fr.codechill.spring.model.Language;

public interface LanguageRepository extends CrudRepository<Language, Long>{
	List<Language> findAll();
}