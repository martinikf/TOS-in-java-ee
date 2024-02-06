package cz.upol.pja.tos.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User implements Serializable {

    public User() {
    }

    public User(String email, String firstname, String lastname, String passwordHash, Role role) {
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    @Id
    private String email;

    private String firstname;
    private String lastname;

    @Column(length = 128)
    private String passwordHash;

    private Role role;

    //Selected studied programme for notifications
    private Programme programme;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Topic> topics;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Comment> comments;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Topic> getTopics() {
        return topics;
    }

    public void setTopics(List<Topic> topics) {
        this.topics = topics;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Programme getProgramme() {
        return programme;
    }

    public void setProgramme(Programme programme) {
        this.programme = programme;
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

    @Override
    public String toString() {
        return String.format("{\"email\":\"%s\",\"firstname\":\"%s\",\"lastname\":\"%s\"}", email, firstname, lastname);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + Objects.hashCode(this.email);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        if (!Objects.equals(this.passwordHash, other.passwordHash)) {
            return false;
        }
        return Objects.equals(this.role, other.role);
    }
}
