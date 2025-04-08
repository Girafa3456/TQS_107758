package fullstack.homeWork.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Reservation {
    @Id
    private String token;
    private String studentName;
    private LocalDateTime reservationTime;
    private boolean isUsed; 
    @ManyToOne
    private Meal meal;

    public Reservation() {
    }

    public Reservation(String token, String studentName, LocalDateTime reservationTime, boolean isUsed, Meal meal) {
        this.token = token;
        this.studentName = studentName;
        this.reservationTime = reservationTime;
        this.isUsed = isUsed;
        this.meal = meal;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public LocalDateTime getReservationTime() {
        return reservationTime;
    }

    public void setReservationTime(LocalDateTime reservationTime) {
        this.reservationTime = reservationTime;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean isUsed) {
        this.isUsed = isUsed;
    }

    public Meal getMeal() {
        return meal;
    }

    public void setMeal(Meal meal) {
        this.meal = meal;
    }
}
