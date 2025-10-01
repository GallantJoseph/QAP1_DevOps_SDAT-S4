import Model.User;
import UserManagement.UserService;

public class Menu {
    UserService userService = new UserService();

    public Menu() {
        initializeData();
    }

    public void displayMenu() {
        if (userService.getLoggedInUser() != null) {
            authenticatedMenu();
        } else {
            unauthenticatedMenu();
        }
    }

    private void unauthenticatedMenu() {
        System.out.println("Welcome to the Fitness Tracker!");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Exit");
        System.out.print("Please select an option: ");
    }

    private void authenticatedMenu() {
        System.out.println("Welcome back, [User]!");
        System.out.println("1. Track Cardio");
        System.out.println("2. Track Workouts");
        System.out.println("3. Track Goals");
        System.out.println("4. View Profile");
        System.out.println("5. Logout");
        System.out.print("Please select an option: ");
    }

    private void initializeData() {
        User user = new User();

        user.setUsername("johndoe");
        user.setPassword("password123");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setDateOfBirth(java.time.LocalDate.of(1965, 6, 17));
        user.setWeightKg(65);
        user.setHeightCm(175);

        userService.registerUser(user);
        userService.login("johndoe", "password123");
    }
}
