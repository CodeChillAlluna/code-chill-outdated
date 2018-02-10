package fr.codechill.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import fr.codechill.spring.model.User;

/**
 * Created by stephan on 20.03.16.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
