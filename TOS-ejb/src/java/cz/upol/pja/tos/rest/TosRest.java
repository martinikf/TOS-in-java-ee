package cz.upol.pja.tos.rest;

import cz.upol.pja.tos.models.Topic;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.io.Serializable;

/**
 * Class for handling rest API.
 *
 * @author marti
 */
@Stateless
@Path("/tos")
public class TosRest implements Serializable {

    @PersistenceContext(unitName = "jpa-tos-db")
    private EntityManager entityManager;

    /**
     * API call for getting all existing topics.
     *
     * @return Topics in .json
     */
    @GET
    @Path("topics")
    @Produces(MediaType.APPLICATION_JSON)
    public String getTopics() {

        var topics = entityManager.createQuery("SELECT t from Topic t").getResultList();

        StringBuilder jsonTopics = new StringBuilder();

        jsonTopics.append("[");
        for (var topic : topics) {
            jsonTopics.append(topic.toString());
            jsonTopics.append(",");
        }
        if (jsonTopics.length() > 2) {
            jsonTopics.deleteCharAt(jsonTopics.length() - 1);
        }
        jsonTopics.append("]");

        return jsonTopics.toString();
    }

    /**
     * API call for getting one specific topic
     *
     * @param id topicId
     * @return Topic .json
     */
    @GET
    @Path("topic/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getTopic(@PathParam("id") int id) {
        var topic = entityManager.find(Topic.class, id);
        if (topic == null) return "Not found";
        return topic.toString();
    }
}
