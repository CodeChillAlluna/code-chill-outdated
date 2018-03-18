package fr.codechill.spring.model;

import fr.codechill.spring.model.User;
import fr.codechill.spring.model.Docker;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserTest{

    private String nom;
    private String prenom;
    private String nomDocker;

    @Before
    public void setUp(){
    this.nom = "michanol";
        prenom = "nathan";
        nomDocker = "testNomDocker";
    }

    @Test
    public void testAddDocker(){
        User userTest = new User(this.nom,this.prenom);
        Docker dockerTest = new Docker (this.nomDocker);
        userTest.addDocker(dockerTest);
        assertEquals(true, userTest.getDockers().contains(dockerTest));
    }
}