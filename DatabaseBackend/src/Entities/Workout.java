package Entities;

import java.sql.Date;
import java.sql.Time;

public class Workout {
	
	private int coachId;
	private String workoutName;
	private Date workoutDate;
	private Time workoutTime;
	private int maxCapacity;
	public Workout(int coachId, String workoutName, Date workoutDate, Time workoutTime, int maxCapacity) {
		super();
		this.coachId = coachId;
		this.workoutName = workoutName;
		this.workoutDate = workoutDate;
		this.workoutTime = workoutTime;
		this.maxCapacity = maxCapacity;
	}
	public int getCoachId() {
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
	public int getMaxCapacity() {
		return maxCapacity;
	}
	
}
