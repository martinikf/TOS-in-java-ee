package cz.upol.pja.tos.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "topic")
public class Topic implements Serializable {

    public Topic() {
    }

    public Topic(String name, String text, String description, Group group, List<Programme> programmes, User author) {
        this.name = name;
        this.text = text;
        this.description = description;
        this.group = group;
        this.programmes = programmes;
        this.author = author;
    }

    @Id
    @GeneratedValue
    private int id;

    private String name;

    @Column(name="text", length=1024)
    private String text;

    @Column(name="description", length=5000)
    private String description;

    @ManyToOne
    private Group group;

    @ManyToOne
    private User author;

    @OneToMany(mappedBy = "topic", cascade = CascadeType.ALL)
    private List<Comment> comments;

    @ManyToMany
    @JoinTable(name = "topic_programme")
    private List<Programme> programmes;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public List<Programme> getProgrammes() {
        return programmes;
    }

    public void setProgrammes(List<Programme> programmes) {
        this.programmes = programmes;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        StringBuilder programmesString = new StringBuilder();
        programmesString.append("[");
        for (var programme : programmes) {
            programmesString.append(String.format("\"%s\",", programme.getName()));
        }
        if (programmesString.length() > 2) {
            programmesString.deleteCharAt(programmesString.length() - 1);
        }
        programmesString.append("]");

        return String.format("{\"name\":\"%s\", \"supervisor\":%s,\"text\":\"%s\",\"description\":\"%s\",\"group\":\"%s\",\"programmes\":%s}", name.replace("\"", "\\\""), author.toString(), text.replace("\"", "\\\""), description.replace("\"", "\\\""), group.getName(), programmesString);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.id;
        hash = 97 * hash + Objects.hashCode(this.name);
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
        final Topic other = (Topic) obj;
        if (this.id != other.id) {
            return false;
        }
        return Objects.equals(this.name, other.name);
    }
}
