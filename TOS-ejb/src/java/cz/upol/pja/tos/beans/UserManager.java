package cz.upol.pja.tos.beans;

import cz.upol.pja.tos.api.IUserManager;
import cz.upol.pja.tos.models.Programme;
import cz.upol.pja.tos.models.Role;
import cz.upol.pja.tos.models.User;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.List;

@Stateless
public class UserManager implements IUserManager {

    @PersistenceContext(unitName = "jpa-tos-db")
    private EntityManager entityManager;

    @Inject
    private ActiveSession activeSession;

    @Override
    public boolean login(String email, String password) {
        User user = entityManager.find(User.class, email);
        if (user == null || user.getPasswordHash().equals(password)) {
            return false;
        }

        activeSession.setLoggedUser(user);
        return true;
    }

    @Override
    public void logout() {
        activeSession.setLoggedUser(null);
    }

    @Override
    public User getLoggedUser() {
        return activeSession.getLoggedUser();
    }

    @Override
    public boolean register(String email, String firstname, String lastname, String password, String role) {
        Role r = getRoleByName(role);

        if (r == null) {
            System.out.println("Chyba, chybí role v databázi.");
            createRoles(); //DatabaseInitializer nefunguje
            r = getRoleByName(role);
        }

        var user = new User(email, firstname, lastname, passwordHasher(password), r);
        entityManager.persist(user);
        return login(email, password);
    }

    private static String passwordHasher(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(password.getBytes());
            return Base64.getEncoder().encodeToString(md.digest());
        } catch (Exception ex) {
            return null;
        }

    }

    @Override
    public void setNotification(String programme) {
        User user = activeSession.getLoggedUser();

        TypedQuery<Programme> query = entityManager.createQuery(
                "SELECT c FROM Programme c WHERE c.name = :programme", Programme.class);
        query.setParameter("programme", programme);

        Programme result = query.getSingleResult();
        if (result == null) {
            return;
        }

        user.setProgramme(result);
        entityManager.merge(user);
    }

    @Override
    public List<User> getUsers() {
        return entityManager.createQuery("SELECT u FROM User u").getResultList();
    }

    @Override
    public User findUser(String value) {
        return entityManager.find(User.class, value);
    }

    private Role getRoleByName(String role) {
        try {
            TypedQuery<Role> query = entityManager.createQuery(
                    "SELECT c FROM Role c WHERE c.name = :name", Role.class);
            query.setParameter("name", role);
            return query.getSingleResult();
        } catch (Exception ex) {
            return null;
        }
    }

    private void createRoles() {
        String[] roles = new String[2];
        roles[0] = "student";
        roles[1] = "editor";

        for (String role : roles) {
            Role foundRole = null;
            try {
                TypedQuery<Role> query = entityManager.createQuery(
                        "SELECT c FROM Programme c WHERE c.name = :name", Role.class);
                query.setParameter("name", role);
                foundRole = query.getSingleResult();
            } catch (Exception ex) {
            }

            if (foundRole == null) {
                entityManager.persist(new Role(role));
            }
        }
    }
}
