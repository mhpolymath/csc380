package Entities;

import java.sql.Date;

public class FitnessData {
	
	private int memberId,recordNumber;
	private Date recordDate;
	private double height,weight,fatRate,muscleMass;
	public FitnessData(int memberId, int recordNumber, Date recordDate, double height, double weight, double fatRate,
			double muscleMass) {
		super();
		this.memberId = memberId;
		this.recordNumber = recordNumber;
		this.recordDate = recordDate;
		this.height = height;
		this.weight = weight;
		this.fatRate = fatRate;
		this.muscleMass = muscleMass;
	}
	public int getMemberId() {
		return memberId;
	}
	public int getRecordNumber() {
		return recordNumber;
	}
	public Date getRecordDate() {
		return recordDate;
	}
	public double getHeight() {
		return height;
	}
	public double getWeight() {
		return weight;
	}
	public double getFatRate() {
		return fatRate;
	}
	public double getMuscleMass() {
		return muscleMass;
	}
	
}	
