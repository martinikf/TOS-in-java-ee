package cz.upol.pja.tos.beans;

import cz.upol.pja.tos.api.ITopicManager;
import cz.upol.pja.tos.models.Group;
import cz.upol.pja.tos.models.Programme;
import cz.upol.pja.tos.models.Topic;
import jakarta.annotation.Resource;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.jms.JMSConnectionFactory;
import jakarta.jms.JMSContext;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class TopicManager implements ITopicManager, Serializable {

    @PersistenceContext(unitName = "jpa-tos-db")
    private EntityManager entityManager;

    @Inject
    @JMSConnectionFactory("java:comp/DefaultJMSConnectionFactory")
    private JMSContext context;

    @Inject
    private ActiveSession activeSession;

    @Resource(mappedName = "newTopicTopic")
    private jakarta.jms.Topic newTopicTopic;

    @Override
    public void createTopic(String group, String name, String text, String description, List<String> recommendedProgrammes) {
        System.out.println("ASJFKDASPHKLFLFKASDKL" + group);
        Group groupObj = getGroupByName(group);

        if (groupObj == null) {
            System.out.println("Chyba, skupina neexistuje v databázi");
            createGroups();
            groupObj = getGroupByName(group);
        }

        var programmes = new ArrayList<Programme>();

        if (recommendedProgrammes != null) {
            for (var p : recommendedProgrammes) {
                programmes.add(getProgrammeByName(p));
            }
        }
        var topic = new Topic(name, text, description, groupObj, programmes, activeSession.getLoggedUser());

        entityManager.persist(topic);
        //Send message about new topic
        context.createProducer().send(newTopicTopic, topic);
    }

    @Override
    public void removeTopic(int id) {
        Topic topic = entityManager.find(Topic.class, id);
        
        if(activeSession.getLoggedUser().getEmail().equals(topic.getAuthor().getEmail())){
            entityManager.remove(topic);
        }
    }

    public Topic getTopic(int id) {
        return entityManager.find(Topic.class, id);
    }

    @Override
    public List<Topic> getTopics() {
        return entityManager.createQuery("SELECT t FROM Topic t").getResultList();
    }

    @Override
    public List<Topic> getTopics(String group) {
        List<Topic> t = entityManager.createQuery("SELECT t FROM Topic t").getResultList();
        var r = new ArrayList<Topic>();
        for (var topic : t) {
            if (topic.getGroup().getName().equals(group)) {
                r.add(topic);
            }
        }

        return r;
    }

    private Group getGroupByName(String group) {
        try {
            TypedQuery<Group> querygr = entityManager.createQuery(
                    "SELECT c FROM Group c WHERE c.name = :group", Group.class);
            querygr.setParameter("group", group);

            return querygr.getSingleResult();
        } catch (Exception ex) {
            return null;
        }
    }

    private Programme getProgrammeByName(String p) {
        try {
            TypedQuery<Programme> query = entityManager.createQuery(
                    "SELECT c FROM Programme c WHERE c.name = :name", Programme.class);
            query.setParameter("name", p);
            return query.getSingleResult();
        } catch (Exception ex) {
            return null;
        }
    }

    private void createGroups() {
        String[] groups = new String[2];
        groups[0] = "Bachelor";
        groups[1] = "Master";

        for (String group : groups) {
            Group foundGroup = null;
            try {
                TypedQuery<Group> query = entityManager.createQuery(
                        "SELECT c FROM Group c WHERE c.name = :name", Group.class);
                query.setParameter("name", group);
                foundGroup = query.getSingleResult();
            } catch (Exception ex) {
            }

            if (foundGroup == null) {
                entityManager.persist(new Group(group));
                entityManager.flush();
            }
        }
    }
}
