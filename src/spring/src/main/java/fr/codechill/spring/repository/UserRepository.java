package fr.codechill.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import fr.codechill.spring.model.User;
import java.util.*;
/**
 * Created by stephan on 20.03.16.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    
    User findByUsername(String username);
    User findOne(long id);
    User save(User user);
    List<User> findAll();
    void delete(User user);
    User findByTokenPassword(String tokenPassword);
    User findByEmail(String email);
}
