package cz.upol.pja.tos.api;

import cz.upol.pja.tos.models.User;
import java.util.List;

/**
 * Interface for handling user accounts logic
 *
 * @author marti
 */
public interface IUserManager {

    /**
     * Login user, returns true if login was successful.
     *
     * @param email User's email address
     * @param password User's password in plain text
     * @return True if login was successful, else false.
     */
    public boolean login(String email, String password);

    /**
     * Creates new user in database.
     *
     * @param email
     * @param firstname
     * @param lastname
     * @param password
     * @param role Name of role ["editor", "student"]
     * @return True if register was successful, else false.
     */
    public boolean register(String email, String firstname, String lastname, String password, String role);

    /**
     * Sets studied programme for user in database. User will receive
     * notifications for new topics in that programme.
     *
     * @param programme Name of programme.
     */
    public void setNotification(String programme);

    /**
     * Logs out the user.
     */
    public void logout();

    /**
     * Get currently logged in user. (session)
     *
     * @return User
     */
    public User getLoggedUser();

    /**
     * List of users.
     *
     * @return List<User>
     */
    public List<User> getUsers();

    /**
     * Finds user by his email address.
     *
     * @param value
     * @return User
     */
    public User findUser(String value);
}
