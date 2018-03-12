package fr.codechill.spring.rest;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.cglib.transform.impl.UndeclaredThrowableTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.context.WebApplicationContext;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import org.aspectj.internal.lang.annotation.ajcITD;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.io.*;
import fr.codechill.spring.model.*;
import fr.codechill.spring.CodeChillApplication;
import fr.codechill.spring.repository.*;
import fr.codechill.spring.controller.*;
import fr.codechill.spring.model.security.Authority;
import fr.codechill.spring.model.security.AuthorityName;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=CodeChillApplication.class)
@WebAppConfiguration

public class UserControllerTest{

    private MockMvc mock;
    private HttpMessageConverter mappingJackson2HttpMessageConverter;


    private  UserRepository urepo;
    private  DockerRepository drepo;
    private  DockerController dcontroller;

    String username;
    String password;
    String firstname;
    String lastname;
    String email;
    String enabled;
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
         enabled = "true";
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
         
    }

    @Test
    public void testUserController(){
        this.dcontroller = new DockerController(drepo);
        assertEquals(true,this.dcontroller.equals(dcontroller));
    }


  /*  @Test
    public void testAddUser(){
        this.mock.perform(post("/user"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.[0].username", is(this.username)))
        .andExpect(jsonPath("$.[0].password", is(this.password)))
        .andExpect(jsonPath("$.[0].firstname", is(this.firstname)))
        .andExpect(jsonPath("$.[0].lastname", is(this.lastname)))
        .andExpect(jsonPath("$.[0].email",is(this.email)))
        .andExpect(jsonPath("$.[0].enabled",is(this.enabled)))
        .andExpect(jsonPath("$.[0].lastPasswordResetDate",is(this.lastPasswordResetDate)))
        .andExpect(jsonPath("$.[0].authorities.[0].id",is(this.auth.getId().intValue())))
        .andExpect(jsonPath("$.[0].authorities.[1].name",is(this.auth.getName())))
        .andExpect(jsonPath("$.[0].authorities.[1].id",is(this.auth2.getId().intValue())))
        .andExpect(jsonPath("$.[0].authorities.[1].name",is(auth2.getName().toString())));

    }*/

    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }

}