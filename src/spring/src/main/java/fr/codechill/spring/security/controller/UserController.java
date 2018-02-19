package fr.codechill.spring.security.controller;
import org.apache.tomcat.util.buf.StringCache;
import org.springframework.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;

import fr.codechill.spring.model.User;
import fr.codechill.spring.repository.UserRepository;
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5000"})
@RestController
public class UserController {

    
    private final UserRepository repo;

    @Autowired
    public UserController (UserRepository repo) {
        this.repo = repo;
    }

    @GetMapping("/user/{id}")
    public User getUser(@PathVariable("id")Long id){
        User user = this.repo.findOne(id);
        return user;
    }
    

    @PutMapping("/user/{id}")
    public User editUser(@RequestBody User user){
        this.repo.save(user);
        return user;
    }
    @PostMapping(value="/user")
    public User addUser(@RequestBody User user){
        repo.save(user);
        return user;
    }

    @DeleteMapping("/user/{id}")
    public boolean deleteUser(@PathVariable("id")Long id){
        User user = this.repo.findOne(id);
        if(user!=null){
            repo.delete(user);
       }
      return true;
    }

}