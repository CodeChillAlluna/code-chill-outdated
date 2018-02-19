package fr.codechill.spring.rest;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

import fr.codechill.spring.repository.UserRepository;
import fr.codechill.spring.model.User;

@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5000"})
@RestController
public class UserController {
    private final UserRepository urepo;

    @Autowired
    public UserController(UserRepository urepo) { 
		this.urepo = urepo;
    }

    @GetMapping("/user/{id}")
    public User getUser(@PathVariable("id") Long id) {
        User user = this.urepo.findOne(id);
        return user;
    }

    @PutMapping("/user/{id}")
	public User editUser(@RequestBody User user) {
        System.out.println(user);
		this.urepo.save(user);
		return user;
    }

}