package UserManagement;

import Model.User;

import java.util.ArrayList;

public class UserService {
    private User loggedInUser = null;
    private final ArrayList<User> users = new ArrayList<>();

    public UserService() {

    }

    public boolean registerUser(User user) {
        if (user != null) {
            if (!usernameExists(user.getUsername())) {
                users.add(user);
                System.out.println("\nUser registered successfully: " + user.getUsername() + "\n");
                return true;
            }
        } else {
            System.out.println("\nCannot register the user: user is null\n");
        }

        return false;
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public User login(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.verifyPassword(password)) {
                this.loggedInUser = user;

                System.out.println("\nLogged in successfully.\n");
                return user;
            }
        }

        System.out.println("\nInvalid username or password.\n");
        return null;
    }

    public void logout() {
        this.loggedInUser = null;

        System.out.println("\nLogged out successfully.\n");
    }

    public boolean usernameExists(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                System.out.printf("\nUser with username %s already exists.\n\n", username);
                return true;
            }
        }

        return false;
    }
}
