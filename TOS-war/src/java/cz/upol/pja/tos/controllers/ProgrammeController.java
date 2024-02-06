package cz.upol.pja.tos.controllers;

import cz.upol.pja.tos.api.IProgrammeManager;
import cz.upol.pja.tos.models.Programme;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import java.util.List;

/**
 * Controller for handling requests about programmes.
 * 
 * @author marti
 */
@Named("programmeController")
@RequestScoped
public class ProgrammeController {

    @EJB
    private IProgrammeManager programmeManager;

    private String name;

    /**
     * Creation of new programme
     */
    public void create() {
        programmeManager.createProgramme(name);
    }

    /**
     * Delete of existing programme.
     * @param name Name of programme
     */
    public void delete(String name) {
        programmeManager.removeProgramme(name);
    }

    /**
     * List of all existing programmes.
     * @return List<Programme>
     */
    public List<Programme> getProgrammes() {
        return programmeManager.getProgrammes();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
