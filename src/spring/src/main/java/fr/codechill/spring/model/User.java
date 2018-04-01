package fr.codechill.spring.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.CascadeType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import fr.codechill.spring.model.security.Authority;

@Entity
@Table(name = "codechilluser")
public class User implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", length = 100, unique = true)
    @NotNull
    @Size(min = 5, max = 100)
    private String username;

    @Column(name = "password", length = 100)
    @NotNull
    @Size(min = 4, max = 100)
    @JsonIgnoreProperties("users")
    private String password;

    @Column(name = "firstname", length = 50)
    @NotNull
    @Size(min = 3, max = 50)
    private String firstname;

    @Column(name = "lastname", length = 50)
    @NotNull
    @Size(min = 3, max = 50)
    private String lastname;

    @Column(name = "email", length = 50)
    @NotNull
    @Size(min = 4, max = 50)
    private String email;

    @Column(name = "enabled")
    @NotNull
    private Boolean enabled;

    @Column(name = "lastpasswordresetdate")
    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    private Date lastPasswordResetDate;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_authority",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_id", referencedColumnName = "id")})
    @JsonIgnoreProperties("users")
    private List<Authority> authorities;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "codechilluser_docker",
        joinColumns = @JoinColumn(name = "codechilluser_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "docker_id", referencedColumnName = "id"))     
    @JsonIgnoreProperties("users")
    private List<Docker> dockers = new ArrayList<>();

    // token for forgotten password
    @Column(name = "tokenpassword")
	private String tokenPassword;


    public User(){}

    public User(String lastname, String firstname) {
        this.lastname = lastname;
        this.firstname = firstname;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public List<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<Authority> authorities) {
        this.authorities = authorities;
    }

    public Date getLastPasswordResetDate() {
        return lastPasswordResetDate;
    }

    public void setLastPasswordResetDate(Date lastPasswordResetDate) {
        this.lastPasswordResetDate = lastPasswordResetDate;
    }

    public List<Docker> getDockers() {
        return dockers;
    }

    public void setDockers(List<Docker> dockers) {
        this.dockers = dockers;
    }

    public String getTokenPassword() {
		return tokenPassword;
	}

	public void setTokenPassword(String tokenPassword) {
		this.tokenPassword = tokenPassword;
    }
    
    public void addDocker(Docker docker) {
        this.dockers.add(docker);
    }

}