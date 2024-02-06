package cz.upol.pja.tos.beans;

import cz.upol.pja.tos.models.Group;
import cz.upol.pja.tos.models.Role;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.Startup;
import jakarta.inject.Singleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

/**
 * Class that starts on startup of application, should create necessary rows in
 * database.
 *
 * @author marti
 */
@Startup
@Singleton
public class DatabaseInitializer {

    //Něco dělám špatně, metoda se při startu applikace nezapíná.
    //Proto kontruluju zda hodnoty v DB existují, když s nimi pracuji.
    
    @PersistenceContext(unitName = "jpa-tos-db")
    private EntityManager entityManager;

    /**
     * Method for initializing the database.
     */
    @PostConstruct
    public void initializeDatabase() {
        String[] groups = new String[2];
        groups[0] = "Bachelor";
        groups[1] = "Master";
        for (String group : groups) {
            TypedQuery<Group> query = entityManager.createQuery(
                    "SELECT c FROM Group c WHERE c.name = :name", Group.class);
            query.setParameter("name", group);
            Group foundGroup = query.getSingleResult();
            if (foundGroup == null) {
                entityManager.persist(new Group(group));
            }
        }

        String[] roles = new String[2];
        roles[0] = "student";
        roles[1] = "student";

        for (String role : roles) {
            TypedQuery<Role> query = entityManager.createQuery(
                    "SELECT c FROM Programme c WHERE c.name = :name", Role.class);
            query.setParameter("name", role);
            Role foundRole = query.getSingleResult();
            if (foundRole == null) {
                entityManager.persist(new Role(role));
            }
        }
    }
}
