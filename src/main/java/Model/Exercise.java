package Model;

import java.time.LocalDate;

public class Exercise {
    private String name;
    private LocalDate date = LocalDate.now();

    public Exercise() {

    }

    public Exercise(String name) {
        this.name = name;
    }

    public Exercise(String name, LocalDate date) {
        this.name = name;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
