package Model;

import at.favre.lib.crypto.bcrypt.BCrypt;

import java.time.LocalDate;
import java.util.ArrayList;

public class User {
    private static long idCounter = 1;

    private long id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private int weightKg;
    private int heightCm;
    private final ArrayList<CardioExercise> cardioExercises = new ArrayList<>();
    private int weightGoalKg;
    private float dailyDistanceGoalKm;

    public User(){
        this.id = idCounter++;
        this.weightGoalKg = 0;
        this.dailyDistanceGoalKm = 0f;
    }

    public static long getIdCounter() {
        return idCounter;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = BCrypt.withDefaults().hashToString(10, password.toCharArray());
    }

    public boolean verifyPassword(String password) {
        return BCrypt.verifyer().verify(password.toCharArray(), this.password).verified;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public int getWeightKg() {
        return weightKg;
    }

    public void setWeightKg(int weightKg) {
        this.weightKg = weightKg;
    }

    public int getHeightCm() {
        return heightCm;
    }

    public void setHeightCm(int heightCm) {
        this.heightCm = heightCm;
    }

    public ArrayList<CardioExercise> getCardioExercises() {
        return cardioExercises;
    }

    public void addCardioExercise(CardioExercise cardioExercise) {
        this.cardioExercises.add(cardioExercise);
    }

    public int getWeightGoalKg() {
        return weightGoalKg;
    }

    public void setWeightGoalKg(int weightGoalKg) {
        this.weightGoalKg = weightGoalKg;
    }

    public float getDailyDistanceGoalKm() {
        return dailyDistanceGoalKm;
    }

    public void setDailyDistanceGoalKm(float dailyDistanceGoalKm) {
        this.dailyDistanceGoalKm = dailyDistanceGoalKm;
    }


    public int getAge() {
        if (dateOfBirth == null) {
            return 0;
        }

        LocalDate today = LocalDate.now();

        int age = today.getYear() - dateOfBirth.getYear();

        // If the user's birthday hasn't occurred yet this year, subtract one from the age
        if (dateOfBirth.isAfter(today.withYear(dateOfBirth.getYear()))) {
            age--;
        }

        return age;
    }

    @Override
    public String toString() {
        return  "\nID:              " + id +
                "\nUsername:        " + username +
                "\nFirst Name:      " + firstName +
                "\nLast Name:       " + lastName +
                "\nDate of Birth:   " + dateOfBirth +
                "\nAge:             " + getAge() +
                "\nWeight (kg):     " + weightKg +
                "\nHeight (cm):     " + heightCm;
    }
}
