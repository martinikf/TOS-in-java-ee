package cz.upol.pja.tos.api;

import cz.upol.pja.tos.models.Programme;
import java.util.List;

/**
 * Interface for handling programmes logic.
 *
 * @author marti
 */
public interface IProgrammeManager {

    /**
     * Creates new programme.
     *
     * @param name
     */
    public void createProgramme(String name);

    /**
     * Removes specific programme.
     *
     * @param name
     */
    public void removeProgramme(String name);

    /**
     * Returns List of programmes
     *
     * @return List<Programme>
     */
    public List<Programme> getProgrammes();
}
