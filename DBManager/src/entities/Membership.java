package entities;

import java.sql.Date;

public class Membership {

    private final Integer memberId;
    private final Integer bno;
    private final Date startDate;
    private final Date endDate;

    public Membership(Integer memberId, Integer bno, Date startDate, Date endDate) {
        this.memberId = memberId;
        this.bno = bno;
        this.startDate = startDate;
        this.endDate = endDate;
    }
    
    public Membership(Integer memberId,Integer bno,Date startDate) {
    	this(memberId,bno,startDate,null);
    }

    public Integer getMemberId() {
        return memberId;
    }

    public Integer getBno() {
        return bno;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }
}
