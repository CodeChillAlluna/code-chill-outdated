package fr.codechill.spring.model;

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
import java.io.Serializable;

@Entity
public class Docker implements Serializable {
    @Id
    @NotNull
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String name;
    
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "dockers")
    private List<User> users = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "docker_language",
        joinColumns = @JoinColumn(name = "docker_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "language_id", referencedColumnName = "id"))     
    private List<Language> languages = new ArrayList<>();

    private Docker() {}

    public Docker(String f, String l) {
        this.name = f;
    }

    public String getName() {
        return this.name;
    }

    public List<User> getUsers() {
        return this.users;
    }

    public void setUsers(List<User> u) {
        this.users = u;
    }
}