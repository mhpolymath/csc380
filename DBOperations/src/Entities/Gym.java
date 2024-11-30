package Entities;

public class Gym {

    private final Integer bno;
    private final String name;
    private final String address;

    public Gym(Integer bno, String name, String address) {
        this.bno = bno;
        this.name = name;
        this.address = address;
    }
    
    public Gym(Integer bno) {
    	this(bno,null,null);
    }

    public Integer getBno() {
        return bno;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return "Bno: " + bno + ", Name: " + name + ", Address: " + address;
    }
}
