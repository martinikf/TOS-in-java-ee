package cz.upol.pja.tos.controllers;

import cz.upol.pja.tos.api.ICommentManager;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

/**
 * Controller for handling requests about comments.
 * 
 * @author marti
 */
@RequestScoped
@Named("commentController")
public class CommentController {

    @EJB
    private ICommentManager commentManager;

    private String text;

    /**
     * Creates comment under Topic with topicId and text as content.
     * 
     * @param topicId 
     */
    public void createComment(int topicId) {
        commentManager.createComment(text, topicId);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
