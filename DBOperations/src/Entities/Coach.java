package Entities;

public class Coach {

    private final Integer id;
    private final String firstName;
    private final String lastName;
    private final String phone;
    private final Double salary;
    private final Integer bno;

    public Coach(Integer id, String firstName, String lastName, String phone, Double salary, Integer bno) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.salary = salary;
        this.bno = bno;
    }
    public Coach(Integer id) {
    	this(id,null,null,null,null,null);
		
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

    public Double getSalary() {
        return salary;
    }

    public Integer getBno() {
        return bno;
    }
}
