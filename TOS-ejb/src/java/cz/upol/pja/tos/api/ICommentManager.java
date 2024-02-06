package cz.upol.pja.tos.api;

import cz.upol.pja.tos.models.Comment;
import java.util.List;

/**
 * Interface for handling comments logic.
 *
 * @author marti
 */
public interface ICommentManager {

    /**
     * Creates new comment under specific topic. User must be logged in.
     *
     * @param text Content of the comment.
     * @param topicId Topic id.
     */
    public void createComment(String text, int topicId);

    /**
     * Get all comments of specific topic.
     *
     * @param topicId
     * @return List<Comment>
     */
    public List<Comment> getComments(int topicId);
}
