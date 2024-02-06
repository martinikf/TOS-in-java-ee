package cz.upol.pja.tos.controllers;

import cz.upol.pja.tos.api.ICommentManager;
import cz.upol.pja.tos.api.ITopicManager;
import cz.upol.pja.tos.models.Comment;
import cz.upol.pja.tos.models.Topic;
import cz.upol.pja.tos.models.User;
import jakarta.ejb.EJB;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Controller for handling requests about topics.
 * 
 * @author marti
 */
@ViewScoped
@Named("topicController")
public class TopicController implements Serializable {

    @EJB
    private ITopicManager topicManager;
    
    @EJB
    private ICommentManager commentManager;

    //Selected topic for topicDetail.xhtml page
    //After action redirect to topix.xhtml?id={id}
    private int id;

    private String name;
    private String text;
    private String description;
    private List<String> programmes;

    //Selected group for topicList view
    private String group;

    public TopicController() {
    }

    /**
     * Creates new topic
     * 
     * @return redirect to index.xhtml
     */
    public String createTopic() {
        topicManager.createTopic(group, name, text, description, programmes);
        
        return "index.xhtml";
    }
    
    /**
     * Delete selected topic
     * 
     * @return redirect to index.xhtml
     */
    public String deleteTopic(){
        topicManager.removeTopic(id);
        
        return "index.xhtml";
    }

    /**
     * Returns list of topics in specific group, 
     * if group is not selected returns all topics.
     * 
     * @return List<Topic>
     */
    public List<Topic> getTopics() {
        if (group != null) {
            return topicManager.getTopics(group);
        } else {
            return topicManager.getTopics();
        }
    }

    /**
     * Returns list of topics that were created by specific user.
     * @param email email of the user
     * @return List<Topic>
     */
    public List<Topic> getTopicsBy(String email) {
        List<Topic> returnList = new ArrayList<Topic>();
        var topics = topicManager.getTopics(group);
        for (var t : topics) {
            if (t.getAuthor().getEmail().equals(email)) {
                returnList.add(t);
            }
        }
        return returnList;
    }

    /**
     * Returns comments of selected topic.
     * @return List<Comment>
     */
    public List<Comment> getComments() {
        return commentManager.getComments(id);
    }

    /**
     * Returns reference to selected topic.
     * @return Topic object
     */
    public Topic getTopic() {
        return topicManager.getTopic(id);
    }

    /**
     * Returns set of supervisors which have created one or more topics.
     * @return Set<User>
     */
    public Set<User> getSupervisors() {
        var topics = getTopics();
        Set<User> supervisors = new HashSet<>();

        for (var t : topics) {
            supervisors.add(t.getAuthor());
        }

        return supervisors;
    }
    
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

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public List<String> getProgrammes() {
        return programmes;
    }

    public void setProgrammes(List<String> programmes) {
        this.programmes = programmes;
    }
}
