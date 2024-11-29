package Entities;

public class Member {
	
	private int id;
	private String firstName , lastName,phone;
	
	public Member(int id, String firstName, String lastName, String phone) {

		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone = phone;
	}

	public int getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getPhone() {
		return phone;
	}
	
	
	
}
