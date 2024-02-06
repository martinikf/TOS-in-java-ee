package cz.upol.pja.tos.beans;

import cz.upol.pja.tos.models.User;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.io.Serializable;

/**
 * Managed bean for storing information about currently logged in user.
 * 
 * @author marti
 */
@SessionScoped
@Named("activeSession")
public class ActiveSession implements Serializable {
    
    private User loggedUser;

    /**
     * 
     * @return currently logged in user or null
     */
    public User getLoggedUser() {
        return loggedUser;
    }

    /**
     * Sets the currently login user.
     * 
     * @param loggedUser 
     */
    public void setLoggedUser(User loggedUser) {
        this.loggedUser = loggedUser;
    }
}
