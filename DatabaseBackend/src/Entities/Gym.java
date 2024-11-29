package Entities;

public class Gym {

	private int bno;
	private String name;
	private String address;
	public Gym(int bno, String bName, String bAddress) {
		
		this.bno = bno;
		this.name = bName;
		this.address = bAddress;
	}
	public int getBno() {
		return bno;
	}
	public String getbName() {
		return name;
	}
	public String getbAddress() {
		return address;
	}
	
	public String toString() {
		return "Bno: " + bno + ", Name: " + name + ", Address: " + address;
	}
	
	
}
