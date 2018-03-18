package fr.codechill.spring.rest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import java.util.List;
import java.util.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.ArrayList;
import java.util.Date;
import java.io.*;
import fr.codechill.spring.model.*;
import fr.codechill.spring.CodeChillApplication;
import fr.codechill.spring.repository.*;
import fr.codechill.spring.controller.*;
import fr.codechill.spring.model.security.Authority;
import fr.codechill.spring.model.security.AuthorityName;
import org.springframework.http.*;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.MediaType;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=CodeChillApplication.class)

@WebAppConfiguration

public class UserControllerTest{

    private MockMvc mock;
    private HttpMessageConverter mappingJackson2HttpMessageConverter;
    private  UserRepository urepo;
    private  DockerRepository drepo;
    private  DockerController dcontroller;
    private UserController cController = new UserController(urepo, drepo);
    @Autowired
    private WebApplicationContext context;
    private User testUser;
    private String testUserJson;
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String email;
    private Boolean enabled;
    private Date lastPasswordResetDate;
    private Authority auth = new Authority();
    private List<Authority> authorities = new ArrayList<Authority>();
    private Authority auth2 = new Authority();
    private Docker dock = new Docker("test");
    private List<Docker> dockers = new ArrayList<>();

    @Before
    public void setUp(){
         username = "nathou";
         password = "123456789";
         firstname = "nathan";
         lastname = "michanol";
         email = "nathoupowa972@gmail.com";
         enabled = true;
         lastPasswordResetDate = new Date(1993,12,12);
         auth = new Authority();
         auth.setId(1L);
         auth.setName(AuthorityName.ROLE_ADMIN);
         authorities.add(auth);
         auth2.setId(2L);
         auth2.setName(AuthorityName.ROLE_USER);
         authorities.add(auth2);
         dockers.add(dock);
         this.mock = MockMvcBuilders
            .webAppContextSetup(context)
            .apply(springSecurity())
            .build();
         testUser = new User(lastname, firstname);
         testUser.setId(4L);
         testUser.setUsername(username);
         testUser.setPassword(password);
         testUser.setAuthorities(authorities);
         testUser.setEmail(email);
         testUser.setLastPasswordResetDate(lastPasswordResetDate);
         testUser.setEnabled(enabled);
         testUser.setDockers(dockers);
         ObjectMapper mapper = new ObjectMapper();
         try{
            testUserJson =mapper.writerWithDefaultPrettyPrinter().writeValueAsString(testUser);
            System.out.println(" Valeurs du JSON de test : " +testUserJson);
         }
         catch (JsonGenerationException e ){
            e.printStackTrace();
         }
         catch(JsonProcessingException e){
             e.printStackTrace();
         }

    }

    @Test
    public void testUserController(){
        this.dcontroller = new DockerController(drepo);
        assertEquals(true,this.dcontroller.equals(dcontroller));
    }


   @Test
    public void testAddUser() throws Exception{
       this.mock.perform(post("/user")
        .contentType(MediaType.APPLICATION_JSON)
        .content(asJsonString(testUser)))
        .andExpect(status().isCreated());
   }

  /* private void CreateUser(){
    /*RestTemplate restTemplate = new RestTemplate();
    ObjectMapper mapper = new ObjectMapper();
    ObjectNode body = mapper.createObjectNode();
    body.put("username", this.testUser.getUsername());
    body.put("password", this.testUser.getPassword());
    body.put("firstname",this.testUser.getFirstname());
    body.put("lastname",this.testUser.getLastname());
    body.put("email",this.testUser.getEmail());
    body.put("enabled",this.testUser.getEnabled());
    body.put("lastPasswordResetDate",this.testUser.getLastPasswordResetDate().toString());
    body.put("authorities","[ {id:"+this.auth.getId()+",name:"+this.auth.getName()+"},{id:"+this.auth2.getId()+",name:"+this.auth2.getName()+"}]");
    body.put("dockers","[]");
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<String> entity = new HttpEntity<String>(body.toString(), headers);
    ResponseEntity<String> res = restTemplate.exchange("http://localhost:8080/user", HttpMethod.POST, entity, String.class);
    cController.addUser(testUser);
}

    @Test
    public void testGetUser() throws Exception {
    this.testUser.setId(4L);
    this.CreateUser();
    RestTemplate restTemplate = new RestTemplate();
    ObjectMapper mapper = new ObjectMapper();
    ObjectNode body = mapper.createObjectNode();
    body.put("username", this.username);
    body.put("password", this.password);
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<String> entity = new HttpEntity<String>(body.toString(), headers);
    ResponseEntity<String> res = restTemplate.exchange("http://localhost:8080/auth", HttpMethod.POST, entity, String.class);
    JsonNode jsonres = mapper.readValue(res.getBody(), JsonNode.class);
    String token = jsonres.get("token").textValue();
    System.out.println("token VALUE"+token);
        this.mock.perform(get("/user/"+this.testUser.getId().intValue())
        .header("Authorization", "Bearer "))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.lastname", is(this.testUser.getLastname())))
        .andExpect(jsonPath("$.firstname ", is(this.testUser.getFirstname())));
    }
*/

    /*public void testUpdateUserEmail(){
        User emailUser = new User(this.lastname,this.firstname);
        emailUser.setEmail(this.email);
        String emailTest = "nmichanol92@gmail.com";
        assert(this.cController.updateUserEmail(emailTest)==true);
      }*/

      public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}