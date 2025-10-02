package UserManagement;

import Model.CardioExercise;
import Model.User;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

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

        System.out.println("\nInvalid username or password.");
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

    public void logCardioSession(){
        if (loggedInUser == null) {
            System.out.println("\nPlease log in to log a cardio session.\n");
            return;
        }

        float cardioDistanceKm;
        String cardioExerciseName;
        LocalDate cardioExerciseDate;

        Scanner scanner = new Scanner(System.in);
        String input;

        System.out.println("\nLog Cardio Session");
        System.out.println("-------------------");

        do {
            System.out.print("\nCardio Exercise Date (YYYY-MM-DD, default to today if left blank): ");

            input = scanner.nextLine();

            if (input.isBlank()) {
                System.out.println("Using today's date: " + LocalDate.now());
                cardioExerciseDate = LocalDate.now();
                break;
            } else {
                try {
                    cardioExerciseDate = LocalDate.parse(input);
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
                System.out.println("Distance cannot be blank. Please try again.\n ");
                continue;
            }

            try {
                cardioDistanceKm = Float.parseFloat(input);

                if (!CardioExercise.isValidDistance(cardioDistanceKm)) {
                    System.out.println("Distance must be a positive number. Please try again.\n ");
                } else
                    break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number for distance.\n ");
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

        LocalDate sevenDaysAgo = LocalDate.now().minusDays(7);
        LocalDate thirtyDaysAgo = LocalDate.now().minusDays(30);
        LocalDate startDate;
        LocalDate endDate;

        System.out.println("Please select from the following cardio session history options:");
        System.out.println("1. View cardio sessions for the last 7 days");
        System.out.println("2. View cardio sessions for the last 30 days");
        System.out.println("3. View cardio sessions within a date range");
        System.out.println("4. View all cardio sessions");

        viewCardioSessionsInDateRange(sevenDaysAgo, LocalDate.now());
        
        // TODO Implement menu and switch case

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
            System.out.printf("%s | %-14s | %.2f km",
                    exercise.getDate(), exercise.getName(), exercise.getDistanceKm());

            distanceSum+=exercise.getDistanceKm();
        }

        System.out.println("Total distance over this period: " + distanceSum + " km\n" );
        System.out.println("Your average distance: " + distanceSum/filteredExercises.size() + " km\n" );

        // TODO Show goal progress
        //System.out.println("Your goal distance: " + " km\n" );

    }
}
