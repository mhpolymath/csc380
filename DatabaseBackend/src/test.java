

import java.sql.Connection;
import java.util.Date;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class test {
	private static final String PASS = "";
	private static final String USER = "root";
	private static final String URL = "jdbc:mysql://localhost:3306/gymerfinal";

	public static void main(String[] args) {
		
		readAllMemberships();
	}
    public static void readAllMemberships() {
        String querySQL = "SELECT * FROM membership_at";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASS);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(querySQL)) {

            System.out.println("Membership List:");
            while (resultSet.next()) {
                int memberId = resultSet.getInt("M_id");
                int gymId = resultSet.getInt("Bno");
                Date startDate = resultSet.getDate("Start_date");
                Date endDate = resultSet.getDate("End_date");

                System.out.println("Member ID: " + memberId + ", Gym ID: " + gymId +
                                   ", Start Date: " + startDate + ", End Date: " + endDate);
            }

        } catch (SQLException e) {
        	JDialog d = new JDialog();
        	
        }
        System.out.print("\n---------------------------------------");
    }

    public static void insertMembership(int memberId, int gymId, Date startDate, Date endDate) {
        String insertSQL = "INSERT INTO membership_at (M_id, G_id, Start_date, End_date) VALUES (?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {

            preparedStatement.setInt(1, memberId);
            preparedStatement.setInt(2, gymId);
            preparedStatement.setDate(3, new java.sql.Date(startDate.getTime()));
            preparedStatement.setDate(4, new java.sql.Date(endDate.getTime()));

            int result = preparedStatement.executeUpdate();
            

        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) { 
            	System.out.println("Error: Membership for this member and gym already exists.");
            } else {
                System.out.println("Database operation failed: " + e.getMessage());
            }
            e.printStackTrace();
        }
    }

    public static void updateMembership(int memberId, int gymId, Date newStartDate, Date newEndDate) {
        String updateSQL = "UPDATE membership_at SET Start_date = ?, End_date = ? WHERE M_id = ? AND G_id = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {

            preparedStatement.setDate(1, new java.sql.Date(newStartDate.getTime()));
            preparedStatement.setDate(2, new java.sql.Date(newEndDate.getTime()));
            preparedStatement.setInt(3, memberId);
            preparedStatement.setInt(4, gymId);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Membership updated successfully for Member ID " + memberId + " and Gym ID " + gymId);
            } else {
                System.out.println("Error: Membership not found for the given Member ID and Gym ID.");
            }

        } catch (SQLException e) {
            System.out.println("Database operation failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void deleteMembership(int memberId, int gymId) {
        String deleteSQL = "DELETE FROM membership_at WHERE M_id = ? AND G_id = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {

            preparedStatement.setInt(1, memberId);
            preparedStatement.setInt(2, gymId);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Membership deleted successfully for Member ID " + memberId + " and Gym ID " + gymId);
            } else {
                System.out.println("Error: Membership not found for the given Member ID and Gym ID.");
            }

        } catch (SQLException e) {
            System.out.println("Database operation failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
	// FITNESS DATA
	public static void readAllFitnessData() {
	    String querySQL = "SELECT * FROM fitness_data";

	    try (Connection connection = DriverManager.getConnection(URL, USER, PASS);
	         Statement statement = connection.createStatement();
	         ResultSet resultSet = statement.executeQuery(querySQL)) {

	        System.out.println("Fitness Data List:");
	        while (resultSet.next()) {
	            int memberId = resultSet.getInt("M_id");
	            int dataBarcode = resultSet.getInt("Data_barcode");
	            Date recordDate = resultSet.getDate("Record_date");
	            double height = resultSet.getDouble("Height");
	            double weight = resultSet.getDouble("Weight");
	            double fatRate = resultSet.getDouble("Fat_rate");
	            double muscleMass = resultSet.getDouble("Muscle_mass");

	            System.out.println("Member ID: " + memberId + ", Data Barcode: " + dataBarcode +
	                               ", Date: " + recordDate + ", Height: " + height +
	                               ", Weight: " + weight + ", Fat Rate: " + fatRate +
	                               ", Muscle Mass: " + muscleMass);
	        }

	    } catch (SQLException e) {
	        System.out.println("Database operation failed: " + e.getMessage());
	        e.printStackTrace();
	    }
	}

	public static void fitnessDataInsert(int memberId, int dataBarcode, Date recordDate, double height, double weight, double fatRate, double muscleMass) {
	    String insertSQL = "INSERT INTO fitness_data (M_id, Data_barcode, Record_date, Height, Weight, Fat_rate, Muscle_mass) VALUES (?, ?, ?, ?, ?, ?, ?)";

	    try (Connection connection = DriverManager.getConnection(URL, USER, PASS);
	         PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {

	        preparedStatement.setInt(1, memberId);
	        preparedStatement.setInt(2, dataBarcode);
	        preparedStatement.setDate(3, new java.sql.Date(recordDate.getTime()));
	        preparedStatement.setDouble(4, height);
	        preparedStatement.setDouble(5, weight);
	        preparedStatement.setDouble(6, fatRate);
	        preparedStatement.setDouble(7, muscleMass);

	        preparedStatement.executeUpdate();
	        System.out.println("Fitness data inserted successfully.");

	    } catch (SQLException e) {
	        if (e.getErrorCode() == 1062) { 
	            System.out.println("Error: Fitness data with this data barcode already exists for the member.");
	        } else {
	            System.out.println("Database operation failed: " + e.getMessage());
	        }
	        e.printStackTrace();
	    }
	}

	public static void fitnessDataUpdate(int memberId, int recordNumber, Date recordDate, double height, double weight, double fatRate, double muscleMass) {
	    String updateSQL = "UPDATE fitness_data SET Record_date = ?, Height = ?, Weight = ?, Fat_rate = ?, Muscle_mass = ? WHERE M_id = ? AND Record_number = ?";

	    try (Connection connection = DriverManager.getConnection(URL, USER, PASS);
	         PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {

	        preparedStatement.setDate(1, new java.sql.Date(recordDate.getTime()));
	        preparedStatement.setDouble(2, height);
	        preparedStatement.setDouble(3, weight);
	        preparedStatement.setDouble(4, fatRate);
	        preparedStatement.setDouble(5, muscleMass);
	        preparedStatement.setInt(6, memberId);
	        preparedStatement.setInt(7, recordNumber);

	        int rowsAffected = preparedStatement.executeUpdate();

	        if (rowsAffected > 0) {
	            System.out.println("Fitness data for Member ID " + memberId + " and Record_number " + recordNumber + " updated successfully.");
	        } else {
	            System.out.println("Error: Fitness data not found for the given Member ID and Data Barcode.");
	        }

	    } catch (SQLException e) {
	        System.out.println("Database operation failed: " + e.getMessage());
	        e.printStackTrace();
	    }
	}

	public static void fitnessDataDelete(int memberId, int dataBarcode) {
	    String deleteSQL = "DELETE FROM fitness_data WHERE M_id = ? AND Data_barcode = ?";

	    try (Connection connection = DriverManager.getConnection(URL, USER, PASS);
	         PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {

	        preparedStatement.setInt(1, memberId);
	        preparedStatement.setInt(2, dataBarcode);

	        int rowsAffected = preparedStatement.executeUpdate();

	        if (rowsAffected > 0) {
	            System.out.println("Fitness data for Member ID " + memberId + " and Data Barcode " + dataBarcode + " deleted successfully.");
	        } else {
	            System.out.println("Error: Fitness data not found for the given Member ID and Data Barcode.");
	        }

	    } catch (SQLException e) {
	        System.out.println("Database operation failed: " + e.getMessage());
	        e.printStackTrace();
	    }
	}

	//GYMS
	public static void readAllGyms() {
	    String querySQL = "SELECT * FROM gym";

	    try (Connection connection = DriverManager.getConnection(URL, USER, PASS);
	         Statement statement = connection.createStatement();
	         ResultSet resultSet = statement.executeQuery(querySQL)) {

	        System.out.println("Gyms List:");
	        while (resultSet.next()) {
	            int id = resultSet.getInt("Bno");
	            String name = resultSet.getString("B_name");
	            String location = resultSet.getString("B_address");

	            System.out.println("ID: " + id + ", Name: " + name + ", Location: " + location);
	        }

	    } catch (SQLException e) {
	        JOptionPane.showMessageDialog(null, e,"ERROR",JOptionPane.ERROR);
	    }
	}

    public void gymInsert(int id, String name, String location) {
        String insertSQL = "INSERT INTO gym (Bno, B_name, Address) VALUES (?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {

            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, location);

            preparedStatement.executeUpdate();
            System.out.println("Gym inserted successfully.");

        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                System.out.println("Error: Gym with this ID already exists.");
            } else {
                System.out.println("Database operation failed: " + e.getMessage());
            }
            e.printStackTrace();
        }
    }

    public static void gymUpdate(int id, String name, String location) {
        String updateSQL = "UPDATE gym SET B_name = ?, Address = ? WHERE Bno = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, location);
            preparedStatement.setInt(3, id);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Gym with ID " + id + " updated successfully.");
            } else {
                System.out.println("Error: Gym with ID " + id + " not found.");
            }

        } catch (SQLException e) {
            System.out.println("Database operation failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void gymDelete(int id) {
        String deleteSQL = "DELETE FROM gym WHERE Bno = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {

            preparedStatement.setInt(1, id);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Gym with ID " + id + " deleted successfully.");
            } else {
                System.out.println("Error: Gym with ID " + id + " not found.");
            }

        } catch (SQLException e) {
            System.out.println("Database operation failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
	
	
	//memebers 
	public static void memberUpdate(int id, String firstName, String lastName, String phone) {
		String updateSQL = "UPDATE member SET M_Fname = ?, M_Lname = ?, M_phone = ? WHERE M_id = ?";

		try (Connection connection = DriverManager.getConnection(URL, USER, PASS);
				PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {

			preparedStatement.setString(1, firstName);
			preparedStatement.setString(2, lastName);
			preparedStatement.setString(3, phone);
			preparedStatement.setInt(4, id);

			int rowsAffected = preparedStatement.executeUpdate();

			if (rowsAffected > 0) {
				System.out.println("Member updated successfully.");
			} else {
				System.out.println("Error: Member with ID " + id + " not found.");
			}

		} catch (SQLException e) {
			System.out.println("Database operation failed: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public static void memberInsert(int id, String firstName, String lastName, String phone) {
		String insertSQL = "INSERT INTO member (M_id, M_Fname, M_Lname, M_phone) VALUES (?, ?, ?, ?)";
		try (Connection connection = DriverManager.getConnection(URL, USER, PASS);
				PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {

			preparedStatement.setInt(1, id);
			preparedStatement.setString(2, firstName);
			preparedStatement.setString(3, lastName);
			preparedStatement.setString(4, phone);

			preparedStatement.executeUpdate();
			System.out.println("Member added successfully.");

		} catch (SQLException e) {
			if (e.getErrorCode() == 1062) {
				System.out.println("Error: Member with this ID already exists.");
			} else {
				System.out.println("Database operation failed: " + e.getMessage());
			}
			e.printStackTrace();
		}
	}
	public static void readAllMembers() {
	    String querySQL = "SELECT * FROM member";

	    try (Connection connection = DriverManager.getConnection(URL, USER, PASS);
	         Statement statement = connection.createStatement();
	         ResultSet resultSet = statement.executeQuery(querySQL)) {

	        System.out.println("Members List:");
	        while (resultSet.next()) {
	            int id = resultSet.getInt("M_id");
	            String firstName = resultSet.getString("M_Fname");
	            String lastName = resultSet.getString("M_Lname");
	            String phone = resultSet.getString("M_phone");

	            System.out.println("ID: " + id + ", Name: " + firstName + " " + lastName + ", Phone: " + phone);
	        }

	    } catch (SQLException e) {
	        System.out.println("Database operation failed: " + e.getMessage());
	        e.printStackTrace();
	    }
	}
	public static void memberDelete(int id) {
	    String deleteSQL = "DELETE FROM member WHERE M_id = ?";

	    try (Connection connection = DriverManager.getConnection(URL, USER, PASS);
	         PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {

	        preparedStatement.setInt(1, id);

	        int rowsAffected = preparedStatement.executeUpdate();

	        if (rowsAffected > 0) {
	            System.out.println("Member with ID " + id + " deleted successfully.");
	        } else {
	            System.out.println("Error: Member with ID " + id + " not found.");
	        }

	    } catch (SQLException e) {
	        System.out.println("Database operation failed: " + e.getMessage());
	        e.printStackTrace();
	    }
	}

}
