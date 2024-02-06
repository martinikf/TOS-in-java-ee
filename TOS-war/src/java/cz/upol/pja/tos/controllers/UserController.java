package cz.upol.pja.tos.controllers;

import cz.upol.pja.tos.api.IUserManager;
import cz.upol.pja.tos.models.User;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

/**
 * Controller for handling user requests.
 * Logout, roles, getting reference to the user.
 * 
 * @author marti
 */
@RequestScoped
@Named("userController")
public class UserController {

    @EJB
    private IUserManager userManager;

    /**
     * Request for logout of current user.
     */
    public void logout() {
        userManager.logout();
    }

    /**
     * Returns instance of current user.
     * @return 
     */
    public User getUser() {
        return userManager.getLoggedUser();
    }

    /**
     * Is current user editor?
     * @return true if user is editor, else false
     */
    public boolean isEditor() {
        var user = getUser();
        return user != null && user.getRole().getName().equals("editor");
    }

    /**
     * Is current user student?
     * @return true if user is student, else false
     */
    public boolean isStudent() {
        var user = getUser();
        return user != null && user.getRole().getName().equals("student");
    }
}
