package Entities;

import java.sql.Date;

public class Membership {
	
	private int memberId,bno;
	private Date startDate,endDate;
	
	public Membership(int memberId, int bno, Date startDate, Date endDate) {
		this.memberId = memberId;
		this.bno = bno;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	public int getMemberId() {
		return memberId;
	}
	public int getBno() {
		return bno;
	}
	public Date getStartDate() {
		return startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	
}
