package fr.codechill.spring.rest;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import fr.codechill.spring.model.User;
import fr.codechill.spring.repository.UserRepository;

@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5000"})
@RestController
public class UserController {
    private final UserRepository urepo;
    private final String SENDFROM = "codechill@hotmail.com";
    private final String BASE_URL = "http://localhost:8080";

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
    public ResponseEntity<?> addUser(@RequestBody User user) {
        HttpHeaders responseHeaders = new HttpHeaders();
        if (urepo.findByUsername(user.getUsername()) != null) {

            return ResponseEntity.badRequest().headers(responseHeaders).body("An account with this username already exist!");
        }
        if (urepo.findByEmail(user.getEmail()) != null) {
            return ResponseEntity.badRequest().headers(responseHeaders).body("An account with this email already exist!");
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        urepo.save(user);
        final URI location = ServletUriComponentsBuilder
            .fromCurrentServletMapping().path("/user/{id}").build()
            .expand(user.getId()).toUri();
        return ResponseEntity.created(location).headers(responseHeaders).body(urepo.findByUsername(user.getUsername()));
    }

    
    //method sending a mail to a new user email
    @PostMapping(value="/user/updateemail")
    public ResponseEntity<?> updateUserEmail(@RequestParam("email")String email)
    {
        HttpHeaders responseHeaders = new HttpHeaders();
        if(!email.equals(""))
        {
            SimpleMailMessage updateEmail = new SimpleMailMessage();
            updateEmail.setFrom(SENDFROM);
            updateEmail.setTo(email);
            updateEmail.setSubject("Email adresse change");
            updateEmail.setText("Your new email adress has been saved by our services");

            mailSender.send(updateEmail);
            return ResponseEntity.ok().headers(responseHeaders).body("Email update is a success");
        }
        return ResponseEntity.badRequest().headers(responseHeaders).body("Update Failed");
    }
    //Method sending an error message for the former email adress
    @PostMapping(value = "/user/updateinfoerror")
    public ResponseEntity<?> updateEmailError(@RequestParam("email")String email){
        User user = urepo.findByEmail(email);
        HttpHeaders responseHeaders = new HttpHeaders();
        if (user!=null){
            SimpleMailMessage infoUpdateFail = new SimpleMailMessage();
            infoUpdateFail.setFrom(SENDFROM);
            infoUpdateFail.setTo(user.getEmail());
            infoUpdateFail.setSubject("Suspicious access to your account");
            infoUpdateFail.setText("We have registered a suspicious activity on your acccount");
            
            mailSender.send(infoUpdateFail);
            return ResponseEntity.ok().headers(responseHeaders).body(user);
        }

        return ResponseEntity.badRequest().headers(responseHeaders).body(user);
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
            passwordResetEmail.setText("Reset link:\n" + BASE_URL + "/reset/" + user.getTokenPassword());
            
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