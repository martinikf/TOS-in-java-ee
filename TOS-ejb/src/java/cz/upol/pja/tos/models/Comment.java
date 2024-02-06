package cz.upol.pja.tos.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "comment")
public class Comment implements Serializable {

    public Comment() {
    }

    public Comment(String text, User author, Topic topic) {
        this.text = text;
        this.author = author;
        //this.parentComment = parentComment;
        this.topic = topic;
    }

    @Id
    @GeneratedValue
    private int id;

    private String text;

    @ManyToOne
    private User author;

    //@ManyToOne
    //private Comment parentComment;
    @ManyToOne
    private Topic topic;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + this.id;
        hash = 79 * hash + Objects.hashCode(this.text);
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
        final Comment other = (Comment) obj;
        return this.id == other.id;
    }
}
