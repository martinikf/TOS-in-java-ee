package cz.upol.pja.tos.api;

import cz.upol.pja.tos.models.Topic;
import java.util.List;

/**
 * Interface for handling topics logic.
 *
 * @author marti
 */
public interface ITopicManager {

    /**
     * Creates new topic in database.
     *
     * @param group Name of already created group. ["Bachelor", "Master"]
     * @param name
     * @param text
     * @param description
     * @param recommendedProgrammes List<String> with names of already existing
     * programmes.
     */
    public void createTopic(String group, String name, String text, String description, List<String> recommendedProgrammes);

    /**
     * Removed specific topic.
     *
     * @param id identification of the topic.
     */
    public void removeTopic(int id);

    /**
     * Get specific topic.
     *
     * @param id identification of the topic.
     * @return Topic or null
     */
    public Topic getTopic(int id);

    /**
     * Get List of all topics.
     *
     * @return List<Topic>
     */
    public List<Topic> getTopics();

    /**
     * Get List of all topics in specific group.
     *
     * @param group Name of group.
     * @return List<Topic>
     */
    public List<Topic> getTopics(String group);

}
