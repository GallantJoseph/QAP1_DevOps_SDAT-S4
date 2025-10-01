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
            users.add(user);
            System.out.println("User registered successfully: " + user.getUsername());
            return true;
        } else {
            System.out.println("Cannot register the user: user is null");
            return false;
        }

    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public User login(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.verifyPassword(password)) {
                this.loggedInUser = user;
                return user;
            }
        }
        return null;
    }

    public void logout() {
        this.loggedInUser = null;
    }
}
