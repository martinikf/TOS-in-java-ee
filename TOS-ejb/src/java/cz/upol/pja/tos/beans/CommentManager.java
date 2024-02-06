package cz.upol.pja.tos.beans;

import cz.upol.pja.tos.api.ICommentManager;
import cz.upol.pja.tos.api.IUserManager;
import cz.upol.pja.tos.models.Comment;
import cz.upol.pja.tos.models.Topic;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.io.Serializable;
import java.util.List;

@Stateless
public class CommentManager implements ICommentManager, Serializable {

    @PersistenceContext(unitName = "jpa-tos-db")
    private EntityManager entityManager;

    @EJB
    private IUserManager userManager;

    @Override
    public void createComment(String text, int topicId) {
        var topic = entityManager.find(Topic.class, topicId);
        //var parent = entityManager.find(Comment.class);
        var comment = new Comment(text, userManager.getLoggedUser(), topic);
        entityManager.persist(comment);
    }

    @Override
    public List<Comment> getComments(int topicId) {
        TypedQuery<Comment> query = entityManager.createQuery(
                "SELECT c FROM Comment c WHERE c.topic.id = :topicId", Comment.class);
        query.setParameter("topicId", topicId);

        List<Comment> comments = query.getResultList();
        System.out.println("NALEZENO KOMENTARU: " + comments.size());
        return comments;
    }
}
