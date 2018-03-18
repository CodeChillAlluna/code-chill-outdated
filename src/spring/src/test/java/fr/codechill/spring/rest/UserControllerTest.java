package fr.codechill.spring.rest;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import java.nio.charset.Charset;
import java.util.List;
import java.util.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import java.util.ArrayList;
import java.util.Date;
import java.io.*;
import fr.codechill.spring.model.*;
import fr.codechill.spring.CodeChillApplication;
import fr.codechill.spring.repository.*;
import fr.codechill.spring.controller.*;
import fr.codechill.spring.model.security.Authority;
import fr.codechill.spring.model.security.AuthorityName;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@RunWith(SpringRunner.class)
@SpringBootTest(classes=CodeChillApplication.class)

@WebAppConfiguration

public class UserControllerTest{

    private MockMvc mock;
    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    private MediaType contentType = 
        new MediaType(MediaType.APPLICATION_JSON.getType(),
        MediaType.APPLICATION_JSON.getSubtype(),
        Charset.forName("utf8"));
    private  UserRepository urepo;
    private  DockerRepository drepo;
    private  DockerController dcontroller;

    private UserController cController = new UserController(urepo, drepo);
    fr.codechill.spring.model.User testUser;
    String testUserJson;
    String username;
    String password;
    String firstname;
    String lastname;
    String email;
    Boolean enabled;
    Date lastPasswordResetDate;
    Authority auth = new Authority();
    List<Authority> authorities = new ArrayList<Authority>();
    Authority auth2 = new Authority();
    Docker dock = new Docker("test");
    List<Docker> dockers = new ArrayList<>();

    @Autowired
    private WebApplicationContext webApplicationContext;


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
         this.mock = webAppContextSetup(webApplicationContext).build();
         
         testUser = new fr.codechill.spring.model.User(lastname, firstname);
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
        //.andExpect(content().json(testUserJson))
        .andExpect(status().isCreated());
   }

   public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

   /* @Test
    public void testGetUser() throws Exception {
        this.mock.perform(get("/user/"+this.testUser.getId().intValue()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.lastname", is(this.testUser.getLastname())))
        .andExpect(jsonPath("$.firstname ", is(this.testUser.getFirstname())));
    }*/


    /*public void testUpdateUserEmail(){
        User emailUser = new User(this.lastname,this.firstname);
        emailUser.setEmail(this.email);
        String emailTest = "nmichanol92@gmail.com";
        assert(this.cController.updateUserEmail(emailTest)==true);
      }*/

}