package fr.codechill.spring.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;

@Entity
public class CodeChillUser {
    @Id
	@NotNull
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "codechilluser_docker",
        joinColumns = @JoinColumn(name = "codechilluser_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "docker_id", referencedColumnName = "id"))     
    private List<Docker> dockers = new ArrayList<>();

    private CodeChillUser() {}

    public CodeChillUser(String f, String l, String e) {
        this.firstName = f;
        this.lastName = l;
        this.email = e;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public List<Docker> getDockers() {
        return this.dockers;
    }

    public String getEmail() {
        return this.email;
    }

    public void setFirstName(String f) {
        this.firstName = f;
    }

    public void setLastName(String l) {
        this.lastName = l;
    }

    public void setDockers(List<Docker> d) {
        this.dockers = d;
    }

    public void setEmail(String e) {
        this.email = e;
    }
}