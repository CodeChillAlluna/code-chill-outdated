package fr.codechill.spring.rest;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import fr.codechill.spring.CodeChillApplication;
import fr.codechill.spring.model.User;
import fr.codechill.spring.model.security.Authority;
import fr.codechill.spring.model.security.AuthorityName;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=CodeChillApplication.class)
@WebAppConfiguration
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class UserControllerTest{

    private MockMvc mock;
    @Autowired
    private WebApplicationContext context;
    private User testUser;
    private User testUser2;
    private String username = "Nathou";
    private String password = "123456789";
    private String firstname = "Nathan";
    private String lastname = "Michanol";
    private String email = "nathou@bonjour.com";
    private Boolean enabled = true;
    private Date lastPasswordResetDate = new Date(1993, 12, 12);
    private String jwtToken;

    @Before
    public void setUp() {
        this.mock = MockMvcBuilders
            .webAppContextSetup(context)
            .apply(springSecurity())
            .build();
        this.setJwtToken("dummy","admin");
        try {
            PrintWriter writer = new PrintWriter("/vagrant/testtoken.txt");
            writer.println(this.jwtToken);
            writer.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
        List<Authority> authorities = new ArrayList<Authority>();
        Authority authorityUser = this.createAuthority(1L, AuthorityName.ROLE_USER);
        authorities = this.addAuthority(authorities, authorityUser);
        this.testUser = setUpUser
            (
                this.username, this.password, this.firstname,
                this.lastname, this.email, this.enabled,
                this.lastPasswordResetDate, authorities
            );
        this.testUser2 = setUpUser
            (
                this.username + "2", this.password, this.firstname,
                this.lastname, "nathoupowa972@gmail.com", this.enabled,
                this.lastPasswordResetDate, authorities
            );
        this.addUserToBdd(this.testUser2);
    }

    public Authority createAuthority(Long id, AuthorityName name) {
        Authority authority = new Authority();
        authority.setId(id);
        authority.setName(name);
        return authority;
    }

    public List<Authority> addAuthority(List<Authority> authorities, Authority authority) {
        authorities.add(authority);
        return authorities;
    }

    public User setUpUser(String username, String password, String firstname,
                          String lastname, String email, Boolean enabled,
                          Date lastPasswordResetDate, List<Authority> authorities) {
        User user = new User(lastname, firstname);
        user.setUsername(username);
        user.setPassword(password);
        user.setAuthorities(authorities);
        user.setEmail(email);
        user.setLastPasswordResetDate(lastPasswordResetDate);
        user.setEnabled(enabled);
        return user;
    }

    public void setJwtToken(String username,String password) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode body = mapper.createObjectNode();
        body.put("username", username);
        body.put("password", password);
        try {
            String res = this.mock.perform(post("/auth")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(body)))
            .andReturn().getResponse().getContentAsString();
            JsonNode jsonres = mapper.readValue(res, JsonNode.class);
            this.jwtToken = jsonres.get("token").textValue();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void addUserToBdd(User user) {
        try 
        {
            this.mock.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(user)))
                .andReturn().getResponse().getContentAsString();
        } 
          catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAddUser() throws Exception {
        this.mock.perform(post("/user")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(testUser)))
            .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void testAddUserUsername() throws Exception {
        this.testUser.setUsername("dummy");
        this.mock.perform(post("/user")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(testUser)))
            .andExpect(status().is4xxClientError());
    }
    @Test
    public void testAddUserEmail() throws Exception {
        this.testUser.setEmail("admin@admin.com");
        this.mock.perform(post("/user")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(testUser)))
            .andExpect(status().is4xxClientError());
    }

    @Test
    public void testAuth() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode body = mapper.createObjectNode();
        body.put("username", "dummy");
        body.put("password", "admin");
        this.mock.perform(post("/auth")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(body)))
            .andExpect(status().isOk());
    }

    @Test
    public void testGetUser() throws Exception {
        this.mock.perform(get("/user/1")
            .header("Authorization", "Bearer " + this.jwtToken)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

   /* @Test
    public void testResetPassword() throws Exception{
        this.setJwtToken(this.testUser.getUsername(),this.testUser.getPassword());
        this.mock.perform(get("/reset/"+this.jwtToken)
        .header("Authorization","Bearer "+this.jwtToken))
        .andExpect(status().isOk()); 
    }
    */

   /*@Test
    public void testEditUser() throws Exception {
        PrintWriter writer = new PrintWriter("/vagrant/test.txt");
        this.setJwtToken(testUser2.getUsername(), testUser2.getPassword());
        writer.println(this.jwtToken);
        writer.println(testUser2.getUsername());
        writer.println(testUser2.getPassword());
        writer.close();
        this.testUser2.setLastname("Simon");
        this.testUser2.setFirstname("Ludwig");
        String token = this.jwtToken;
        String res = this.mock.perform(put("/user")
            .header("Authorization", "Bearer " + token)
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(testUser2)))
            .andExpect(status().is2xxSuccessful())
            .andReturn().getResponse().getContentAsString();        
        // writer.println(res);
    }*/
    @Test
    public void testDelete() throws Exception{
        this.mock.perform(delete("/user")
        .header("Authorization","Bearer "+this.jwtToken))
        .andExpect(status().is2xxSuccessful());
    }


    public static String asJsonString(final Object obj) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(obj);
    }

}