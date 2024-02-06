package cz.upol.pja.tos.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "programme")
public class Programme implements Serializable {

    public Programme() {
    }

    public Programme(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue
    private int id;

    private String name;

    @ManyToMany(mappedBy = "programmes")
    private List<Topic> topics;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Topic> getTopics() {
        return topics;
    }

    public void setTopics(List<Topic> topics) {
        this.topics = topics;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.name);
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
        final Programme other = (Programme) obj;
        return true;
    }
}
