package cz.upol.pja.tos.controllers;

import cz.upol.pja.tos.api.IUserManager;
import cz.upol.pja.tos.models.Programme;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

/**
 * Controller for handling user requests to change 
 * selected programme for notifications.
 * 
 * @author marti
 */
@Named("notificationController")
@RequestScoped
public class NotificationController {

    @EJB
    private IUserManager userManager;

    private String programme;
    
    /**
     * Handle for requests to change programme for notifications.
     */
    public void setNotification() {
        userManager.setNotification(programme);
    }

    /**
     * Returns chosen programme of current user.
     * 
     * @return Name of programme
     */
    public String getProgramme() {
        Programme p = userManager.getLoggedUser().getProgramme();
        if(p == null){
            return null;
        }
        else{
            programme = p.getName();
            return programme;
        }
    }

    public void setProgramme(String programme) {
        this.programme = programme;
    }
}
