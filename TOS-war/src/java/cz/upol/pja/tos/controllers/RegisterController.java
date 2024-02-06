package cz.upol.pja.tos.controllers;

import cz.upol.pja.tos.api.IUserManager;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

/**
 * Controller handling registration of users.
 * 
 * @author marti
 */
@Named("registerController")
@RequestScoped
public class RegisterController {

    @EJB
    private IUserManager userManager;

    private String email;
    private String firstname;
    private String lastname;
    private String role = "student"; //Default value for role
    private String password;
    private String confirmPassword;

    public RegisterController() {
    }

    /**
     * Request for user registration.
     * 
     * @return redirect to index.xhtml
     */
    public String createUser() {
        if (role != null && role.equals("editor")) {
            userManager.register(email, firstname, lastname, password, role);
        } else {
            userManager.register(email, firstname, lastname, password, "student");
        }

        return "index.xhtml";
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

}
