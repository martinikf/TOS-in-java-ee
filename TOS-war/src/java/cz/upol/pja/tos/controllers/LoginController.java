package cz.upol.pja.tos.controllers;

import cz.upol.pja.tos.api.IUserManager;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

/**
 * Controller for handling login requests.
 * 
 * @author marti
 */
@RequestScoped
@Named("loginController")
public class LoginController {

    @EJB
    private IUserManager userManager;

    private String email;
    private String password;

    /**
     * Logins the user if credentials are correct.
     * 
     * @param target
     * @return target redirect
     */
    public String login(String target) {
        if (password != null && email != null) {
            userManager.login(email, password);
        }

        return target;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String loginName) {
        this.email = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
