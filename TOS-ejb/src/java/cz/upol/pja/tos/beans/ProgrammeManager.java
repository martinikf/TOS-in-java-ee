package cz.upol.pja.tos.beans;

import cz.upol.pja.tos.api.IProgrammeManager;
import cz.upol.pja.tos.models.Programme;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.io.Serializable;
import java.util.List;

@Stateless
public class ProgrammeManager implements IProgrammeManager, Serializable {

    @PersistenceContext(unitName = "jpa-tos-db")
    private EntityManager entityManager;

    @Override
    public void createProgramme(String name) {
        entityManager.persist(new Programme(name));
    }

    @Override
    public void removeProgramme(String name) {

        TypedQuery<Programme> query = entityManager.createQuery(
                "SELECT c FROM Programme c WHERE c.name = :name", Programme.class);
        query.setParameter("name", name);
        Programme p = query.getSingleResult();

        if (p != null) {
            System.out.println("Programme remove" + p.getName());
            entityManager.remove(p);
        }
    }

    @Override
    public List<Programme> getProgrammes() {
        return entityManager.createQuery("SELECT p FROM Programme p").getResultList();
    }
}
