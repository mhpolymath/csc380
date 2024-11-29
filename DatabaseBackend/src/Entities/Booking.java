package Entities;

public class Booking {
	
	private int memberId,coachId;
	
	
	public Booking(int mid,int cid) {
		memberId = mid;
		coachId = cid;
	}
	public int getMemberId() {
		return memberId;
	}
	
	public int getCoachId() {
		return coachId;
	}
}
