import Model.CardioExercise;
import Model.User;
import UserManagement.UserService;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Menu {
    private final UserService userService = new UserService();
    private final Scanner scanner = new Scanner(System.in);

    public Menu() {

    }

    public void displayMenu() {
        unauthenticatedMenu();
    }

    private void unauthenticatedMenu() {
        final int LOGIN_OPTION = 1;
        final int REGISTER_OPTION = 2;
        final int EXIT_OPTION = 3;

        int option = 0;

        do {
            System.out.println("\nWelcome to the Fitness Tracker Application!");
            System.out.println("--------------------------------------------");
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
                case LOGIN_OPTION:
                    userService.loginScreen();

                    if (userService.getLoggedInUser() != null) {
                        authenticatedMenu();
                    } else
                        pressEnterToContinue();
                    break;
                case REGISTER_OPTION:
                    userService.registerScreen();
                    pressEnterToContinue();
                    break;
                case EXIT_OPTION:
                    System.out.println("\nThank you for using the Fitness Tracker application. Have a good day!");
                    return;
                default:
                    System.out.println("\nInvalid option. Please try again.");
                    pressEnterToContinue();
            }
        } while (true);
    }

    private void authenticatedMenu() {
        final int TRACK_CARDIO_OPTION = 1;
        final int TRACK_GOALS_OPTION = 2;
        final int PROFILE_OPTION = 3;
        final int QUIT_OPTION = 4;

        int option = 0;

        do {
            System.out.printf("\nWelcome back, %s!\n", userService.getLoggedInUser().getFirstName());
            System.out.println("----------------------------");
            System.out.println("1. Track Cardio Sessions");
            System.out.println("2. Track Goals");
            System.out.println("3. My Profile");
            System.out.println("4. Logout");
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
                case TRACK_CARDIO_OPTION:
                    trackCardioSessionsMenu();
                    pressEnterToContinue();
                    break;
                case TRACK_GOALS_OPTION:
                    userService.trackGoals();
                    pressEnterToContinue();
                    break;
                case PROFILE_OPTION:
                    userService.manageProfile();
                    pressEnterToContinue();
                    break;
                case QUIT_OPTION:
                    userService.logout();
                    pressEnterToContinue();
                    return;
                default:
                    System.out.println("\nInvalid option. Please try again.");
                    pressEnterToContinue();
            }
        } while(true);
    }

    private void trackCardioSessionsMenu(){
        final int LOG_CARDIO_OPTION = 1;
        final int VIEW_HISTORY_OPTION = 2;
        final int BACK_TO_MAIN_MENU_OPTION = 3;

        int option = 0;

        do {
            System.out.println("\nCardio Sessions Tracking");
            System.out.println("--------------------------");
            System.out.println("1. Log Cardio Session");
            System.out.println("2. View Cardio History");
            System.out.println("3. Back to Main Menu");

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
                case LOG_CARDIO_OPTION:
                    userService.logCardioSession();
                    pressEnterToContinue();
                    break;
                case VIEW_HISTORY_OPTION:
                    userService.viewCardioSessionHistory();
                    pressEnterToContinue();
                    break;
                case BACK_TO_MAIN_MENU_OPTION:
                    return;
                default:
                    System.out.println("\nInvalid option. Please try again.");
                    pressEnterToContinue();
            }
        } while (true);
    }

    private void pressEnterToContinue() {
        System.out.print("\nPress Enter to continue...");
        scanner.nextLine();
    }
}
