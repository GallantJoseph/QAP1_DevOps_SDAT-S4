import Model.User;
import UserManagement.UserService;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Menu {
    private final UserService userService = new UserService();
    private final Scanner scanner = new Scanner(System.in);

    public Menu() {
        initializeData();
    }

    public void displayMenu() {
        unauthenticatedMenu();
    }

    private void unauthenticatedMenu() {
        int option = 0;

        do {
            System.out.println("\nWelcome to the Fitness Tracker Application!\n");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");

            System.out.print("\nPlease select an option: ");

            if (scanner.hasNextInt()) {
                option = scanner.nextInt();
                scanner.nextLine();
            } else {
                System.out.println("\nInvalid input. Please enter a number.");
                scanner.nextLine();
                pressEnterToContinue();
                continue;
            }

            switch (option) {
                case 1:
                    loginMenu();
                    break;
                case 2:
                    registerMenu();
                    break;
                case 3:
                    System.out.println("\nThank you for using the Fitness Tracker application. Have a good day!");
                    return;
                default:
                    System.out.println("\nInvalid option. Please try again.");
                    pressEnterToContinue();
            }
        } while (true);
    }

    private void authenticatedMenu() {
        System.out.printf("Welcome back, %s!\n", userService.getLoggedInUser().getFirstName());
//        System.out.println("1. Track Cardio");
//        System.out.println("2. Track Workouts");
//        System.out.println("3. Track Goals");
//        System.out.println("4. View Profile");
//        System.out.println("5. Logout");
//        System.out.print("\nPlease select an option: ");
        // TODO: Implement authenticated menu options and remove logout and pressEnterToContinue

        userService.logout();
        pressEnterToContinue();
    }

    private void loginMenu() {
        String username;
        String password;

        System.out.println("\nLogin to Your Account");
        System.out.println("---------------------");

        System.out.print("Username: ");

        do {
            username = scanner.nextLine();

            if(username.isBlank()) {
                System.out.print("Username cannot be blank. Please enter your username: ");
            } else {
                break;
            }
        } while (true);

        System.out.print("Password: ");

        do {
            password = scanner.nextLine();

            if(password.isBlank()) {
                System.out.print("Password cannot be blank. Please enter your password: ");
            } else {
                break;
            }
        } while (true);

        User user = userService.login(username, password);

        if (user != null) {
            authenticatedMenu();
        } else
            pressEnterToContinue();
    }

    private void registerMenu() {
        String username;
        String password;
        String firstName;
        String lastName;
        LocalDate dateOfBirth;
        int weightKg;
        int heightCm;
        String input;

        System.out.println("\nRegister a New Account");
        System.out.println("-----------------------");
        System.out.println("Please enter your details to create an account.\n");

        do {
            System.out.print("Username (leave blank to quit): ");
            username = scanner.nextLine();

            if (username.isBlank()) {
                System.out.println("Registration cancelled.");
                pressEnterToContinue();
                return;
            }

        } while (userService.usernameExists(username));

        do {
            System.out.print("Password: ");
            password = scanner.nextLine();

            if (password.isBlank()) {
                System.out.println("Password cannot be blank. Please try again\n");
            } else
                break;
        } while (true);

        do {
            System.out.print("First Name: ");
            firstName = scanner.nextLine();

            if (firstName.isBlank()) {
                System.out.println("First name cannot be blank. Please try again.\n ");
            } else
                break;
        } while (true);

        do {
            System.out.print("Last Name: ");
            lastName = scanner.nextLine();

            if (lastName.isBlank()) {
                System.out.println("Last name cannot be blank. Please try again.\n ");
            } else
                break;
        } while (true);

        do {
            System.out.print("Date of Birth (YYYY-MM-DD): ");
            input = scanner.nextLine();

            try {
                dateOfBirth = LocalDate.parse(input);
                break;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please enter your date of birth in the format YYYY-MM-DD\n ");
            }
        } while (true);

        do {
            System.out.print("Weight (kg): ");
            input = scanner.nextLine();

            if (input.isBlank()) {
                System.out.println("Weight cannot be blank. Please try again.\n ");
                continue;
            }

            try {
                weightKg = Integer.parseInt(input);

                if (weightKg <= 0) {
                    System.out.println("Weight must be a positive number. Please try again.\n ");
                } else
                    break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number for weight.\n ");
            }
        } while (true);

        do {
            System.out.print("Height (cm): ");
            input = scanner.nextLine();

            if (input.isBlank()) {
                System.out.println("Height cannot be blank. Please try again.\n ");
                continue;
            }

            try {
                heightCm = Integer.parseInt(input);

                if (heightCm <= 0) {
                    System.out.println("Height must be a positive number. Please try again.\n ");
                } else
                    break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number for height.\n ");
            }
        } while (true);

        User newUser = new User();

        newUser.setUsername(username);
        newUser.setPassword(password);
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.setDateOfBirth(dateOfBirth);
        newUser.setWeightKg(weightKg);
        newUser.setHeightCm(heightCm);

        userService.registerUser(newUser);

        pressEnterToContinue();
    }

    private void pressEnterToContinue() {
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
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
        //userService.login("johndoe", "password123");
    }
}
