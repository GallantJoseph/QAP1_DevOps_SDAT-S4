package Model;

import java.time.LocalDate;

public class CardioExercise extends Exercise {
    private float distanceKm;

    public CardioExercise() {
        super("Walking");
    }

    public CardioExercise(String name) {
        super(name);
    }

    public float getDistanceKm() {
        return distanceKm;
    }

    public void setDistanceKm(float distanceKm) {
        this.distanceKm = distanceKm;
    }

    public static boolean isValidDistance(float distanceKm) {
        return distanceKm > 0f;
    }
}
