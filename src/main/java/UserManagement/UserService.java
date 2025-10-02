package UserManagement;

import Model.CardioExercise;
import Model.User;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class UserService {
    private User loggedInUser = null;

    private final ArrayList<User> users = new ArrayList<>();
    private final Scanner scanner = new Scanner(System.in);

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

        System.out.println("\nInvalid username or password.");
        return null;
    }

    public void logout() {
        this.loggedInUser = null;

        System.out.println("\nLogged out successfully.");
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

    public void logCardioSession(){
        if (loggedInUser == null) {
            System.out.println("\nPlease log in to log a cardio session.\n");
            return;
        }

        float cardioDistanceKm;
        String cardioExerciseName;
        LocalDate cardioExerciseDate;
        LocalDate today = LocalDate.now();

        String input;

        System.out.println("\nLog Cardio Session");
        System.out.println("-------------------");

        do {
            System.out.print("\nCardio Exercise Date (YYYY-MM-DD, default to today if left blank): ");

            input = scanner.nextLine();

            if (input.isBlank()) {
                System.out.println("Using today's date: " + today);
                cardioExerciseDate = today;
                break;
            } else {
                try {
                    cardioExerciseDate = LocalDate.parse(input);

                    if (cardioExerciseDate.isAfter(today)) {
                        System.out.println("The date cannot be in the future. Please try again.");
                        continue;
                    }
                    break;
                } catch (DateTimeParseException e) {
                    System.out.println("Invalid date format. Please try again.");
                }
            }
        } while (true);

        System.out.print("\nCardio Exercise Name (default to \"Walking\" if left blank): ");
        cardioExerciseName = scanner.nextLine();

        do {
            System.out.print("\nCardio Distance (km): ");

            input = scanner.nextLine();

            if (input.isBlank()) {
                System.out.println("The distance cannot be blank. Please try again.");
                continue;
            }

            try {
                cardioDistanceKm = Float.parseFloat(input);

                if (!CardioExercise.isValidDistance(cardioDistanceKm)) {
                    System.out.println("The distance must be a positive number. Please try again.");
                } else
                    break;
            } catch (NumberFormatException e) {
                System.out.println("The distance value is invalid. Please enter a number.");
            }
        } while (true);

        // Create CardioExercise object and add it to the logged-in user's list
        CardioExercise cardioExercise;

        if (cardioExerciseName.isBlank()) {
            cardioExercise = new CardioExercise();
        } else {
            cardioExercise = new CardioExercise(cardioExerciseName);
        }

        cardioExercise.setDate(cardioExerciseDate);
        cardioExercise.setDistanceKm(cardioDistanceKm);

        loggedInUser.addCardioExercise(cardioExercise);

        System.out.println("\nCardio session logged successfully!");
    }

    public void viewCardioSessionHistory() {
        if (loggedInUser == null) {
            System.out.println("\nPlease log in to view cardio session history.\n");
            return;
        }

        if (loggedInUser.getCardioExercises().isEmpty()) {
            System.out.println("\nNo cardio sessions logged yet.");
            return;
        }

        int option = 0;

        LocalDate today = LocalDate.now();
        LocalDate sevenDaysAgo = today.minusDays(7);
        LocalDate thirtyDaysAgo = today.minusDays(30);

        do {
            System.out.println("\nPlease select from the following cardio session history options:\n");
            System.out.println("1. View cardio sessions for the last 7 days");
            System.out.println("2. View cardio sessions for the last 30 days");
            System.out.println("3. View cardio sessions within a date range");
            System.out.println("4. View all cardio sessions");
            System.out.println("5. Back to the Cardio Tracking menu");

            System.out.print("\nPlease select an option: ");

            if (scanner.hasNextInt()) {
                option = scanner.nextInt();
                scanner.nextLine();
            } else {
                System.out.println("\nInvalid input. Please enter a number.");
                scanner.nextLine();

                continue;
            }

            switch (option) {
                case 1:
                    viewCardioSessionsInDateRange(sevenDaysAgo, today);
                    break;
                case 2:
                    viewCardioSessionsInDateRange(thirtyDaysAgo, today);
                    break;
                case 3:
                    // Prompt user for start and end dates to show sessions between those dates
                    LocalDate startDate;
                    LocalDate endDate;

                    do {
                        System.out.print("\nEnter the start date (YYYY-MM-DD): ");
                        String startDateInput = scanner.nextLine();

                        System.out.print("\nEnter the end date (YYYY-MM-DD): ");
                        String endDateInput = scanner.nextLine();

                        try {
                            startDate = LocalDate.parse(startDateInput);
                            endDate = LocalDate.parse(endDateInput);

                            if (startDate.isAfter(endDate)) {
                                System.out.println("\nStart date cannot be after end date. Please try again.");
                                continue;
                            }

                            break;
                        } catch (DateTimeParseException e) {
                            System.out.println("\nInvalid date format. Please try again.");
                        }
                    } while (true);

                    viewCardioSessionsInDateRange(startDate, endDate);
                    break;
                case 4:
                    viewCardioSessionsInDateRange(LocalDate.of(1900, 1, 1), today);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("\nInvalid option. Please try again.");
            }
        } while (true);
    }

    private void viewCardioSessionsInDateRange(LocalDate startDate, LocalDate endDate) {
        if (loggedInUser == null) {
            System.out.println("\nPlease log in to view cardio session history.\n");
            return;
        }

        ArrayList<CardioExercise> cardioExercises = loggedInUser.getCardioExercises();
        ArrayList<CardioExercise> filteredExercises = new ArrayList<>();

        float distanceSum = 0f;

        if (cardioExercises.isEmpty()) {
            System.out.println("\nNo cardio sessions logged yet.\n");
            return;
        }

        for (CardioExercise exercise : cardioExercises) {
            if ((exercise.getDate().isAfter(startDate) || exercise.getDate().isEqual(startDate)) &&
                (exercise.getDate().isBefore(endDate) || exercise.getDate().isEqual(endDate))) {
                filteredExercises.add(exercise);
            }
        }

        if (filteredExercises.isEmpty()) {
            System.out.println("\nNo cardio sessions found in the specified date range.\n");
            return;
        }

        System.out.println("\nCardio Session History for " + loggedInUser.getFirstName() + " " + loggedInUser.getLastName() +
                           " from " + startDate + " to " + endDate);

        System.out.println("\nDate       | Exercise       | Distance (km)");
        System.out.println("--------------------------------------------------");
        for (CardioExercise exercise : filteredExercises) {
            System.out.printf("%s | %-14s | %5.2f km\n",
                    exercise.getDate(), exercise.getName(), exercise.getDistanceKm());

            distanceSum += exercise.getDistanceKm();
        }

        System.out.println("\n\nTotal distance over this period: " + distanceSum + " km" );
        System.out.println("Your average distance: " + distanceSum/filteredExercises.size() + " km\n" );

        System.out.print("Press Enter to continue...");
        scanner.nextLine();

        // TODO Show goal progress
        //System.out.println("Your goal distance: " + " km\n" );

    }
}
