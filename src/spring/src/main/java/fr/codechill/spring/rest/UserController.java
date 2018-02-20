package fr.codechill.spring.rest;

import java.util.Calendar;
import java.util.UUID;
import java.util.Map;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.mail.SimpleMailMessage;


import fr.codechill.spring.model.User;
import fr.codechill.spring.repository.UserRepository;

@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5000"})
@RestController
public class UserController {
    private final UserRepository urepo;
    private final String SENDFROM = "";
    private final String BASE_URL = "localhost:8080";

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    public UserController(UserRepository urepo) { 
        this.urepo = urepo;
    }

    @GetMapping("/user/{id}")
    public User getUser(@PathVariable("id") Long id) {
        User user = this.urepo.findOne(id);
        return user;
    }

    
    @DeleteMapping("/user/{id}")
    public Boolean deleteUser(@PathVariable("id") Long id) {
        User user = this.urepo.findOne(id);
        this.urepo.delete(user);
        return true;
    }

    @PutMapping("/user/{id}")
    public User editUser(@RequestBody User user) {
        this.urepo.save(user);
        return user;
    }

    @PostMapping("/user")
    public User addUser(@RequestBody User user) {
        urepo.save(user);
        return user;
    }

    // Process form submission from forgotPassword page
	@PostMapping(value = "/user/forgottenpassword")
	public ResponseEntity<?> processForgotPasswordForm(@RequestParam("email") String email) {
        User user = urepo.findByEmail(email);
        HttpHeaders responseHeaders = new HttpHeaders();

        if(user != null) {
            user.setLastPasswordResetDate(new Date());
            user.setTokenPassword(UUID.randomUUID().toString());
            user = urepo.save(user);

            SimpleMailMessage passwordResetEmail = new SimpleMailMessage();
            passwordResetEmail.setFrom(SENDFROM);
            passwordResetEmail.setTo(user.getEmail());
            passwordResetEmail.setSubject("Password Reset Request");
            passwordResetEmail.setText("Reset link:\n" + BASE_URL + "/reset?token=" + user.getTokenPassword());
            
            mailSender.send(passwordResetEmail);
            return ResponseEntity.ok().headers(responseHeaders).body(user);
        }
        return ResponseEntity.badRequest().headers(responseHeaders).body(user);
    }

    @GetMapping(value = "/reset/{token}")
    public ResponseEntity<?> resetPassword(@PathVariable("token") String token) {
        HttpHeaders responseHeaders = new HttpHeaders();
        User user = urepo.findByTokenPassword(token);

        Date currentDate = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(user.getLastPasswordResetDate());
        c.add(Calendar.DATE, 1);
        Date currentDatePlusOne = c.getTime();

        if(user != null) {
            if(currentDate.after(user.getLastPasswordResetDate()) && currentDate.before(currentDatePlusOne)) {
                return ResponseEntity.ok().headers(responseHeaders).body(null);
            }
        }
        return ResponseEntity.badRequest().headers(responseHeaders).body(null);
    }

    @PostMapping(value = "/reset")
    public ResponseEntity<?> setNewPassword(@RequestParam Map<String, String> requestParams) {
        User user = urepo.findByTokenPassword(requestParams.get("token"));
        HttpHeaders responseHeaders = new HttpHeaders();

        if(user != null) {
            user.setPassword(bCryptPasswordEncoder.encode(requestParams.get("password")));
            user.setTokenPassword(null);
            urepo.save(user);
            return ResponseEntity.ok().headers(responseHeaders).body(null);
        }
        return ResponseEntity.badRequest().headers(responseHeaders).body(null);
    }
}