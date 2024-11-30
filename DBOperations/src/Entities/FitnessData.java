package Entities;

import java.sql.Date;

public class FitnessData {

    private final Integer memberId;
    private final Integer recordNumber;
    private final Date recordDate;
    private final Double height;
    private final Double weight;
    private final Double fatRate;
    private final Double muscleMass;

    public FitnessData(Integer memberId, Integer recordNumber, Date recordDate, Double height, Double weight, Double fatRate, Double muscleMass) {
        this.memberId = memberId;
        this.recordNumber = recordNumber;
        this.recordDate = recordDate;
        this.height = height;
        this.weight = weight;
        this.fatRate = fatRate;
        this.muscleMass = muscleMass;
    }
    public FitnessData(Integer memberId,Integer recordNumber) {
    	this(memberId,recordNumber,null,null,null,null,null);
    }
    public Integer getMemberId() {
        return memberId;
    }

    public Integer getRecordNumber() {
        return recordNumber;
    }

    public Date getRecordDate() {
        return recordDate;
    }

    public Double getHeight() {
        return height;
    }

    public Double getWeight() {
        return weight;
    }

    public Double getFatRate() {
        return fatRate;
    }

    public Double getMuscleMass() {
        return muscleMass;
    }
}
