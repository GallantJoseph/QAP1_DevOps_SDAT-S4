package UserManagement;

import Model.CardioExercise;
import Model.Exercise;
import Model.User;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class UserService {
    private User loggedInUser = null;

    private final ArrayList<User> users = new ArrayList<>();
    private final Scanner scanner = new Scanner(System.in);

    public UserService() {

    }

    public boolean register(User user) {
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

    public void registerScreen() {
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
                return;
            }

        } while (usernameExists(username));

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

        register(newUser);
    }

    public void loginScreen() {
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

        loggedInUser = login(username, password);
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

        final int LAST_7_DAYS_OPTION = 1;
        final int LAST_30_DAYS_OPTION = 2;
        final int DATE_RANGE_OPTION = 3;
        final int ALL_SESSIONS_OPTION = 4;
        final int BACK_TO_MENU_OPTION = 5;

        int option = 0;

        LocalDate today = LocalDate.now();
        LocalDate lastSevenDays = today.minusDays(6); // Including today
        LocalDate lastThirtyDays = today.minusDays(29); // Including today

        do {
            System.out.println("\nPlease select from the following cardio session history options");
            System.out.println("---------------------------------------------------------------");
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
                case LAST_7_DAYS_OPTION:
                    viewCardioSessionsInDateRange(lastSevenDays, today);
                    break;
                case LAST_30_DAYS_OPTION:
                    viewCardioSessionsInDateRange(lastThirtyDays, today);
                    break;
                case DATE_RANGE_OPTION:
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
                case ALL_SESSIONS_OPTION:
                    viewCardioSessionsInDateRange(LocalDate.MIN, today);
                    break;
                case BACK_TO_MENU_OPTION:
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
        int numberOfDays;

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

        // Sort the filtered exercises by date in ascending order
        filteredExercises.sort(Comparator.comparing(Exercise::getDate));

        // Calculate the number of days correctly when displaying all sessions,
        // and display from first logged session date
        if (startDate.equals(LocalDate.MIN)) {
            startDate = filteredExercises.getFirst().getDate();
        }

        numberOfDays = (int) (endDate.toEpochDay() - startDate.toEpochDay()) + 1;

        System.out.println("\nCardio Session History for " + loggedInUser.getFirstName() + " " + loggedInUser.getLastName() +
                           " from " + startDate + " to " + endDate);

        System.out.println("\nDate       | Exercise       | Distance (km)");
        System.out.println("--------------------------------------------------");
        for (CardioExercise exercise : filteredExercises) {
            System.out.printf("%s | %-14s | %5.2f km\n",
                    exercise.getDate(), exercise.getName(), exercise.getDistanceKm());

            distanceSum += exercise.getDistanceKm();
        }

        System.out.printf("\n\nTotal distance over this period: %.2f km\n", distanceSum);
        System.out.printf("Your average daily distance in the past %d day(s): %.2f km\n",
                numberOfDays, distanceSum/numberOfDays);

        System.out.println("\nYour daily distance goal is: " + (loggedInUser.getDailyDistanceGoalKm() > 0 ?
                loggedInUser.getDailyDistanceGoalKm() + " km" : "No daily distance goal set") + "\n");

        System.out.print("Press Enter to continue...");
        scanner.nextLine();
    }

    public void trackGoals() {
        if (loggedInUser == null) {
            System.out.println("\nPlease log in to track your goals.\n");
            return;
        }

        String input;

        System.out.println("\nMy Goals");
        System.out.println("-------------------------------");
        System.out.println("My current weight: " + loggedInUser.getWeightKg() + " kg");
        System.out.println("My weight goal: " + (loggedInUser.getWeightGoalKg() > 0 ?
                loggedInUser.getWeightGoalKg() + " kg" : "No weight goal set"));

        System.out.println("\nMy daily distance goal: " + (loggedInUser.getDailyDistanceGoalKm() > 0 ?
                loggedInUser.getDailyDistanceGoalKm() + " km" : "No daily distance goal set"));

        do {
            System.out.print("\nWould you like to set or update your goals? (y/n): ");
            input = scanner.nextLine();

            if (input.equalsIgnoreCase("y")) {
                int weightGoalKg = 0;
                float dailyDistanceGoalKm = 0f;

                do {
                    System.out.println("\nLeave blank to keep current value or enter '0' to remove the goal.");

                    System.out.print("\nEnter your weight goal in kilograms: ");
                    input = scanner.nextLine();

                    if (input.isBlank()) {
                        weightGoalKg = loggedInUser.getWeightGoalKg();
                        break;
                    }

                    try {
                        weightGoalKg = Integer.parseInt(input);

                        if (weightGoalKg < 0) {
                            System.out.println("The weight goal must be a positive number. Please try again.");
                        } else
                            break;
                    } catch (NumberFormatException e) {
                        System.out.println("The weight goal value is invalid. Please enter a whole number.");
                    }
                } while (true);

                do {
                    System.out.print("\nEnter your daily distance goal in kilometers: ");
                    input = scanner.nextLine();

                    if (input.isBlank()) {
                        dailyDistanceGoalKm = loggedInUser.getDailyDistanceGoalKm();
                        break;
                    }

                    try {
                        dailyDistanceGoalKm = Float.parseFloat(input);

                        if (dailyDistanceGoalKm < 0) {
                            System.out.println("The distance goal must be a positive number. Please try again.");
                        } else
                            break;
                    } catch (NumberFormatException e) {
                        System.out.println("The distance goal value is invalid. Please enter a number.");
                    }
                } while (true);

                loggedInUser.setWeightGoalKg(weightGoalKg);
                loggedInUser.setDailyDistanceGoalKm(dailyDistanceGoalKm);

                System.out.println("\nGoals updated successfully!");
                return;
            } else if (input.equalsIgnoreCase("n")) {
                return;
            } else {
                System.out.println("\nInvalid input. Please enter 'y' or 'n'.");
            }
        } while (true);
    }

    public void manageProfile(){
        if (loggedInUser == null) {
            System.out.println("\nPlease log in to manage your profile.\n");
            return;
        }

        String input;

        System.out.println("\nYour Profile Details:");
        System.out.println(loggedInUser);

        System.out.print("\nWould you like to update your profile? (y/n): ");

        do {
            input = scanner.nextLine();

            if (input.equalsIgnoreCase("y")) {
                String firstName = "";
                String lastName = "";
                int weightKg = 0;

                System.out.println("\nPlease re-enter your details. Leave blank to keep unchanged.\n");

                System.out.println("First Name: " + loggedInUser.getFirstName());
                System.out.print("New First Name: ");
                input = scanner.nextLine();

                if (!input.isBlank()) {
                    firstName = input;
                } else {
                    System.out.println("Keeping previous value.");
                }

                System.out.println("\nLast Name: " + loggedInUser.getLastName());
                System.out.print("New Last Name: ");
                input = scanner.nextLine();

                if (!input.isBlank()) {
                    lastName = input;
                } else{
                    System.out.println("Keeping previous value.");
                }

                System.out.println("\nWeight (kg): " + loggedInUser.getWeightKg());
                System.out.print("New Weight (kg): ");
                input = scanner.nextLine();

                if (!input.isBlank()) {
                    try {
                        weightKg = Integer.parseInt(input);

                        if (weightKg < 0) {
                            weightKg = 0;
                            System.out.println("Weight must be a positive number. Keeping previous value.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Keeping previous weight value.");
                    }
                } else{
                    System.out.println("Keeping previous value.");
                }

                System.out.print("\nConfirm update? (y/n): ");

                do {
                    input = scanner.nextLine();

                    if (input.equalsIgnoreCase("y")) {
                        if (!firstName.isBlank()) {
                            loggedInUser.setFirstName(firstName);
                        }

                        if (!lastName.isBlank()) {
                            loggedInUser.setLastName(lastName);
                        }

                        if (weightKg > 0) {
                            loggedInUser.setWeightKg(weightKg);
                        }

                        System.out.println("\nProfile updated successfully.");
                        return;
                    } else if (input.equalsIgnoreCase("n")) {
                        System.out.println("\nUpdate cancelled. No changes were made.");
                        return;
                    } else {
                        System.out.print("\nInvalid input. Please enter 'y' or 'n': ");
                    }
                } while (true);

            } else if (input.equalsIgnoreCase("n")) {
                return;
            } else {
                System.out.print("\nInvalid input. Please enter 'y' or 'n': ");
            }
        } while (true);
    }
}
