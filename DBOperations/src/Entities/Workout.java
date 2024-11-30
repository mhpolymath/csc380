package Entities;

import java.sql.Date;
import java.sql.Time;

public class Workout {

    private final Integer coachId;
    private final String workoutName;
    private final Date workoutDate;
    private final Time workoutTime;
    private final Integer maxCapacity;

    public Workout(Integer coachId, String workoutName, Date workoutDate, Time workoutTime, Integer maxCapacity) {
        this.coachId = coachId;
        this.workoutName = workoutName;
        this.workoutDate = workoutDate;
        this.workoutTime = workoutTime;
        this.maxCapacity = maxCapacity;
    }
    
    public Workout(Integer coachId) {
    	this(coachId,null,null,null,null);
    }

    public Integer getCoachId() {
        return coachId;
    }

    public String getWorkoutName() {
        return workoutName;
    }

    public Date getWorkoutDate() {
        return workoutDate;
    }

    public Time getWorkoutTime() {
        return workoutTime;
    }

    public Integer getMaxCapacity() {
        return maxCapacity;
    }
}
