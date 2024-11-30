package Entities;

public class Member {

    private final Integer id;
    private final String firstName;
    private final String lastName;
    private final String phone;

    public Member(Integer id, String firstName, String lastName, String phone) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
    }
    
    public Member(Integer id) {
    	this(id,null,null,null);
    }

    public Integer getId() {
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
