package Tables;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import DB.DatabaseOperation;
import Entities.Coach;
import Entities.Workout;

public class WorkoutTable implements Relation<Workout> {

    @Override
    public void insert(Workout workout) {
        Connection connection = null;
        try {
            connection = DatabaseOperation.getConnection();
            String insertQuery = "INSERT INTO workout_class (C_id, W_name, Class_date, Class_time, Max_capacity) VALUES (?,?,?,?,?)";
            PreparedStatement stmt = connection.prepareStatement(insertQuery);
            
            DatabaseOperation.setInt(stmt, 1, workout.getCoachId());
            stmt.setString(2, workout.getWorkoutName());
            stmt.setDate(3, workout.getWorkoutDate());
            stmt.setTime(4, workout.getWorkoutTime());
            DatabaseOperation.setInt(stmt, 5, workout.getMaxCapacity());
            
            int result = stmt.executeUpdate();
            if(result == 1)
                DatabaseOperation.showSuccessMessage("Insertion successful.");
            else
                DatabaseOperation.showErrorMessage("Insertion failed.");
        } catch (SQLException e) {
            DatabaseOperation.showErrorMessage(e.getMessage());
        } finally {
            DatabaseOperation.closeConnection(connection);
        }
    }

    @Override
    public void delete(Workout workout) {
        Connection connection = null;
        try {
            connection = DatabaseOperation.getConnection();
            String deleteQuery = "DELETE FROM workout_class WHERE C_id = ? AND W_name = ? AND Class_date = ? AND Class_time = ?";
            PreparedStatement stmt = connection.prepareStatement(deleteQuery);
            
            DatabaseOperation.setInt(stmt, 1, workout.getCoachId());
            stmt.setString(2, workout.getWorkoutName());
            stmt.setDate(3, workout.getWorkoutDate());
            stmt.setTime(4, workout.getWorkoutTime());
            
            int result = stmt.executeUpdate();
            if(result == 1)
                DatabaseOperation.showSuccessMessage("Delete successful.");
            else
                DatabaseOperation.showErrorMessage("Delete failed.");
        } catch (SQLException e) {
            DatabaseOperation.showErrorMessage(e.getMessage());
        } finally {
            DatabaseOperation.closeConnection(connection);
        }
    }

    @Override
    public void update(Workout oldElement, Workout newElement) {
        Connection connection = null;
        try {
            connection = DatabaseOperation.getConnection();
            String updateQuery = "UPDATE workout_class SET W_name = ?, Class_date = ?, Class_time = ?, Max_capacity = ? WHERE C_id = ? AND W_name = ? AND Class_date = ? AND Class_time = ?";
            PreparedStatement stmt = connection.prepareStatement(updateQuery);
            
            stmt.setString(1, newElement.getWorkoutName());
            stmt.setDate(2, newElement.getWorkoutDate());
            stmt.setTime(3, newElement.getWorkoutTime());
            DatabaseOperation.setInt(stmt, 4, newElement.getMaxCapacity());
            DatabaseOperation.setInt(stmt, 5, oldElement.getCoachId());
            stmt.setString(6, oldElement.getWorkoutName());
            stmt.setDate(7, oldElement.getWorkoutDate());
            stmt.setTime(8, oldElement.getWorkoutTime());
            
            int result = stmt.executeUpdate();
            if(result == 1)
                DatabaseOperation.showSuccessMessage("Update successful.");
            else
                DatabaseOperation.showErrorMessage("Update failed.");
        } catch (SQLException e) {
            DatabaseOperation.showErrorMessage(e.getMessage());
        } finally {
            DatabaseOperation.closeConnection(connection);
        }
    }

    // Helper method to assign attributes to the table row
    private void assignAttributesToTableRow(ResultSet rs, int rowIndex, Object data[][]) throws SQLException {
        data[rowIndex][0] = rs.getInt("Coach ID");
        data[rowIndex][1] = rs.getString("Coach Name");
        data[rowIndex][2] = rs.getString("Workout Name");
        data[rowIndex][3] = rs.getDate("Workout Date");
        data[rowIndex][4] = rs.getTime("Workout Time");
        data[rowIndex][5] = rs.getString("Current Bookings");
    }

    // Common query for selection
    private String noConditionQuery() {
        return "SELECT WC.C_id AS \"Coach ID\", CONCAT(C.C_Fname, ' ', C.C_Lname) AS \"Coach Name\", "
        		+ "WC.W_name AS \"Workout Name\", WC.Class_date AS \"Workout Date\", WC.Class_time AS \"Workout Time\",  "
        		+ "CONCAT(COUNT(B.M_id) , ' / ',Max_capacity) AS \"Current Bookings\"";
    }

    private String selectionSourceQuery() {
        return " FROM workout_class WC INNER JOIN coach C "
        		+ "ON WC.C_id = C.C_id LEFT OUTER JOIN booking B ON WC.C_id = B.C_id AND WC.Class_date = "
        		+ "B.Class_date AND WC.Class_time = B.Class_time";
    }
    
    private String groupBy() {
    	return " GROUP BY WC.C_id,WC.Class_date,WC.Class_time";
    }
    private String orderBy() {
    	return " ORDER BY WC.Class_date,WC.Class_time";
    }
    // Method to select workouts by specific workout keys
    public DefaultTableModel selectByKeys(Workout workout) {
        String columnNames[] = {"Coach ID", "Coach Name", "Workout Name", "Workout Date", "Workout Time", "Current Bookings"};
        Connection connection = null;
        Object data[][] = null;
        String condition = " WHERE WC.C_id = ? AND WC.W_name = ? AND WC.Class_date = ? AND WC.Class_time = ?";
        String query = noConditionQuery() + selectionSourceQuery() + condition + groupBy() + orderBy();
        String countQuery = "SELECT COUNT(DISTINCT WC.C_id) AS Row_count" + selectionSourceQuery() + condition;
        try {
            connection = DatabaseOperation.getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            DatabaseOperation.setInt(stmt, 1, workout.getCoachId());
            stmt.setString(2, workout.getWorkoutName());
            stmt.setDate(3, workout.getWorkoutDate());
            stmt.setTime(4, workout.getWorkoutTime());
            
            PreparedStatement countStmt = connection.prepareStatement(countQuery);
            DatabaseOperation.setInt(countStmt, 1, workout.getCoachId());
            countStmt.setString(2, workout.getWorkoutName());
            countStmt.setDate(3, workout.getWorkoutDate());
            countStmt.setTime(4, workout.getWorkoutTime());
            
            ResultSet rs = stmt.executeQuery();
            ResultSet countSet = countStmt.executeQuery();
            countSet.next();
            
            int tableSize = countSet.getInt("Row_count");
            data = new Object[tableSize][columnNames.length];
            int rowIndex = 0;
            while(rs.next()) 
                assignAttributesToTableRow(rs, rowIndex++, data);
        } catch (SQLException e) {
            DatabaseOperation.showErrorMessage(e.getMessage());
        } finally {
            DatabaseOperation.closeConnection(connection);
        }
        return new DefaultTableModel(data, columnNames);
    }

    // Method to select workouts by coach name
    public DefaultTableModel selectByCoachName(Coach coach) {
        String columnNames[] = {"Coach ID", "Coach Name", "Workout Name", "Workout Date", "Workout Time", "Current Bookings"};
        Connection connection = null;
        Object data[][] = null;
        String condition = " WHERE C.C_Fname LIKE ? AND C.C_Lname LIKE ?";
        String query = noConditionQuery() + selectionSourceQuery() + condition + groupBy() + orderBy();
        String countQuery = "SELECT COUNT(DISTINCT WC.C_id) AS Row_count" + selectionSourceQuery() + condition;
        try {
            connection = DatabaseOperation.getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, "%" + coach.getFirstName() + "%");
            stmt.setString(2, "%" + coach.getLastName() + "%");
            
            PreparedStatement countStmt = connection.prepareStatement(countQuery);
            countStmt.setString(1, "%" + coach.getFirstName() + "%");
            countStmt.setString(2, "%" + coach.getLastName() + "%");
            
            ResultSet rs = stmt.executeQuery();
            ResultSet countSet = countStmt.executeQuery();
            countSet.next();
            
            int tableSize = countSet.getInt("Row_count");
            data = new Object[tableSize][columnNames.length];
            int rowIndex = 0;
            while(rs.next()) 
                assignAttributesToTableRow(rs, rowIndex++, data);
        } catch (SQLException e) {
            DatabaseOperation.showErrorMessage(e.getMessage());
        } finally {
            DatabaseOperation.closeConnection(connection);
        }
        return new DefaultTableModel(data, columnNames);
    }

    // Method to select workouts by date and time range
    public DefaultTableModel selectByDateTime(Date startDate, Date endDate, Time startTime, Time endTime) {
        String columnNames[] = {"Coach ID", "Coach Name", "Workout Name", "Workout Date", "Workout Time", "Current Bookings"};
        Connection connection = null;
        Object data[][] = null;
        String condition = " WHERE WC.Class_date BETWEEN ? AND ? AND WC.Class_time BETWEEN ? AND ?";
        String query = noConditionQuery() + selectionSourceQuery() + condition + groupBy() + orderBy();
        String countQuery = "SELECT COUNT(DISTINCT WC.C_id) AS Row_count" + selectionSourceQuery() + condition;
        try {
            connection = DatabaseOperation.getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setDate(1, startDate);
            stmt.setDate(2, endDate);
            stmt.setTime(3, startTime);
            stmt.setTime(4, endTime);
            
            PreparedStatement countStmt = connection.prepareStatement(countQuery);
            countStmt.setDate(1, startDate);
            countStmt.setDate(2, endDate);
            countStmt.setTime(3, startTime);
            countStmt.setTime(4, endTime);
            
            ResultSet rs = stmt.executeQuery();
            ResultSet countSet = countStmt.executeQuery();
            countSet.next();
            
            int tableSize = countSet.getInt("Row_count");
            data = new Object[tableSize][columnNames.length];
            int rowIndex = 0;
            while(rs.next()) 
                assignAttributesToTableRow(rs, rowIndex++, data);
        } catch (SQLException e) {
            DatabaseOperation.showErrorMessage(e.getMessage());
        } finally {
            DatabaseOperation.closeConnection(connection);
        }
        return new DefaultTableModel(data, columnNames);
    }
    
    @Override
    public DefaultTableModel loadTable() {
    	
    	    String columnNames[] = {"Coach ID", "Coach Name", "Workout Name", "Workout Date", "Workout Time", "Current Bookings"};
    	    Connection connection = null;
    	    Object data[][] = null;
    	    String query = noConditionQuery() + selectionSourceQuery() + groupBy() + orderBy();
    	    String countQuery = "SELECT COUNT(DISTINCT WC.C_id) AS Row_count" + selectionSourceQuery();

    	    try {
    	        connection = DatabaseOperation.getConnection();
    	       
    	        PreparedStatement stmt = connection.prepareStatement(query);
    	        PreparedStatement countStmt = connection.prepareStatement(countQuery);

    	        // Execute both the query for fetching data and the one for counting rows
    	        ResultSet rs = stmt.executeQuery();
    	        ResultSet countSet = countStmt.executeQuery();
    	        countSet.next();
    	        
    	        // Get the row count to create a proper sized table
    	        int tableSize = countSet.getInt("Row_count");
    	        data = new Object[tableSize][columnNames.length];
    	        
    	        int rowIndex = 0;
    	        while(rs.next()) {
    	            assignAttributesToTableRow(rs, rowIndex++, data);
    	        }
    	    } catch (SQLException e) {
    	        DatabaseOperation.showErrorMessage(e.getMessage());
    	    } finally {
    	        DatabaseOperation.closeConnection(connection);
    	    }
    	    
    	    return new DefaultTableModel(data, columnNames);
    	

    }
    
    public List<String> getAllKeys(){
    	
    	
    	String query = "SELECT C_id, CONCAT(C_Fname,\' \',C_Lname) AS Full_name FROM Coach";
    	
    	Connection connection = null;
    	List<String> coaches = new ArrayList<>();

        try {
            // Establish connection
            connection = DatabaseOperation.getConnection(); // Assuming this is your method for DB connection

            // Prepare the statement
            PreparedStatement stmt = connection.prepareStatement(query);

            // Set the parameters using DatabaseOperation
     

            // Execute the query
            ResultSet resultSet = stmt.executeQuery();

            // Process the result set
            while (resultSet.next()) {
                int coachId = resultSet.getInt("C_id");
                String fullName = resultSet.getString("Full_name");
                coaches.add(coachId + ":" + fullName);
            }
        } catch (SQLException e) {
            DatabaseOperation.showErrorMessage(e.getMessage());
        } finally {
            DatabaseOperation.closeConnection(connection); // Ensures the connection is closed
        }

        return coaches;

    	
    }
}
