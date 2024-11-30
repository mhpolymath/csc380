package Entities;

import java.sql.Date;
import java.sql.Time;
public class Booking {
	
	private int memberId,coachId;
	private Date workoutDate;
	private Time workoutTime;
	
	public Booking(int memberId, int coachId, Date workoutDate, Time workoutTime) {
		super();
		this.memberId = memberId;
		this.coachId = coachId;
		this.workoutDate = workoutDate;
		this.workoutTime = workoutTime;
	}

	public int getMemberId() {
		return memberId;
	}

	public int getCoachId() {
		return coachId;
	}

	public Date getWorkoutDate() {
		return workoutDate;
	}

	public Time getWorkoutTime() {
		return workoutTime;
	}
	
	
	
	
}
