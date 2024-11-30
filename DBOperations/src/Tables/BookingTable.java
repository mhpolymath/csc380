package Tables;

import java.nio.channels.UnsupportedAddressTypeException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;
import javax.xml.crypto.Data;

import DB.DatabaseOperation;
import Entities.Booking;
import Entities.Coach;
import Entities.Member;

public class BookingTable implements Relation<Booking>{

	@Override
	public void insert(Booking booking) {
		Connection connection = null;
		try {
			connection = DatabaseOperation.getConnection();
			String insertQuery = "INSERT INTO Booking(M_id,C_id) VALUES (?,?)";
			
			PreparedStatement stmt = connection.prepareStatement(insertQuery);
			
			DatabaseOperation.setInt(stmt, 1, booking.getMemberId());
			DatabaseOperation.setInt(stmt, 2, booking.getCoachId());
			
			
			int result = stmt.executeUpdate();
			if(result == 1)
				DatabaseOperation.showSuccessMessage("Insertion successful.");
			
			else
				DatabaseOperation.showErrorMessage("Insertion failed.");
			
		}catch(SQLException e) {
			DatabaseOperation.showErrorMessage(e.getMessage());
		}
		finally {
			DatabaseOperation.closeConnection(connection);
		}
		
	}

	@Override
	public void delete(Booking deleted) {
		Connection connection = null;
		try {
			connection = DatabaseOperation.getConnection();
			String deleteQuery = "DELETE FROM Booking WHERE M_id = ? AND C_id = ?";
			PreparedStatement stmt = connection.prepareStatement(deleteQuery);
			
			DatabaseOperation.setInt(stmt, 1, deleted.getMemberId());
			DatabaseOperation.setInt(stmt, 2, deleted.getCoachId());
			
			int result = stmt.executeUpdate();
			if(result == 1)
				DatabaseOperation.showSuccessMessage("Delete successful.");
			
			else
				DatabaseOperation.showErrorMessage("Delete failed. Check if deleted entry exists.");
			
		}catch(SQLException e) {
			DatabaseOperation.showErrorMessage(e.getMessage());
		}
		finally {
			DatabaseOperation.closeConnection(connection);
		}

		
	}

	@Override
	public void update(Booking oldElement, Booking newElement) {
		Connection connection = null;
		try {
			connection = DatabaseOperation.getConnection();
			String updateQuery = "UPDATE Booking SET M_id = ?,C_id = ? WHERE M_id = ? AND C_id = ?";
			PreparedStatement stmt = connection.prepareStatement(updateQuery);
			
			DatabaseOperation.setInt(stmt, 1, newElement.getMemberId());
			DatabaseOperation.setInt(stmt, 2, newElement.getCoachId());
			DatabaseOperation.setInt(stmt, 3, oldElement.getMemberId());
			DatabaseOperation.setInt(stmt, 4, oldElement.getCoachId());
			
			int result = stmt.executeUpdate();
			if(result == 1)
				DatabaseOperation.showSuccessMessage("Update successful.");
			
			else
				DatabaseOperation.showErrorMessage("Update failed. Check if updated entry exists.");
			
		}catch(SQLException e) {
			DatabaseOperation.showErrorMessage(e.getMessage());
		}
		finally {
			DatabaseOperation.closeConnection(connection);
		}
		
		
	}

		
	
	private void assignAttributesToTableRow(ResultSet rs,int rowIndex,Object data[][]) throws SQLException {
		
		data[rowIndex][0] = rs.getInt("Member ID");
		data[rowIndex][1] = rs.getString("Member Name");
		data[rowIndex][2] = rs.getInt("Coach ID");
		data[rowIndex][3] = rs.getString("Coach Name");
		data[rowIndex][4] = rs.getDate("Workout Date");
		data[rowIndex][5] = rs.getTime("Workout Time");
	}
	private String noConditionQuery() {
		return "Select B.M_id AS \"Member ID\" ,CONCAT(M.M_Fname,' ',M.M_Lname) AS \"Member Name\",\r\n"
				+ " B.C_id AS \"Coach ID\", CONCAT(C.C_Fname,' ',C.C_Lname) AS \"Coach Name\",\r\n"
				+ " Class_date AS \"Workout Date\", Class_time AS \"Workout Time\" \r\n";
				
	}
	
	private String selectionSourceQuery() {
		return  " FROM\r\n"
				+ " member M INNER JOIN booking B ON M.M_id = B.M_id\r\n"
				+ " INNER JOIN coach C ON B.C_id = C.C_id";
	}
	
	public DefaultTableModel selectByKeys(Booking booking) {
		String columnNames[] = {"Member ID","Member Name","Coach ID","Coach Name","Workout Date","Workout Time"};
		Connection connection = null;
		Object data[][] = null;
		String condition = " WHERE B.M_id = ? AND B.C_id = ?";
		String query = noConditionQuery() + selectionSourceQuery() + condition;
		String countQuery = "SELECT COUNT(*) AS Row_count" + selectionSourceQuery() + condition;
		try {
			connection = DatabaseOperation.getConnection();
			PreparedStatement stmt = connection.prepareStatement(query);
			stmt.setInt(1, booking.getMemberId());
			stmt.setInt(2, booking.getCoachId());
			
			PreparedStatement countStmt = connection.prepareStatement(countQuery);
			countStmt.setInt(1, booking.getMemberId());
			countStmt.setInt(2, booking.getCoachId());
			
			ResultSet rs = stmt.executeQuery();
			ResultSet countSet = countStmt.executeQuery();
			countSet.next();
			
			int tableSize = countSet.getInt("Row_count");
			
			int rowIndex = 0;
			data = new Object[tableSize][columnNames.length];
			while(rs.next()) 
				assignAttributesToTableRow(rs, rowIndex++, data);
						
 			
		} catch (SQLException e) {
			DatabaseOperation.showErrorMessage(e.getMessage());
		}
		finally {
			DatabaseOperation.closeConnection(connection);		
			}
		
		return new DefaultTableModel(data,columnNames);
	}
	
	public DefaultTableModel selectByMemberName(Member member) {
		String columnNames[] = {"Member ID","Member Name","Coach ID","Coach Name","Workout Date","Workout Time"};
		Connection connection = null;
		Object data[][] = null;
		String condition = " WHERE M.M_Fname LIKE ? AND M.M_Lname LIKE ?";
		String query = noConditionQuery() + selectionSourceQuery() +condition;
		String countQuery = "SELECT COUNT(*) AS Row_count" + selectionSourceQuery() + condition;
		try {
			connection = DatabaseOperation.getConnection();
			PreparedStatement stmt = connection.prepareStatement(query);
			stmt.setString(1, "%" + member.getFirstName() + "%");
			stmt.setString(2, "%" + member.getLastName() + "%");
			
			PreparedStatement countStmt = connection.prepareStatement(countQuery);
			countStmt.setString(1, "%" + member.getFirstName() + "%");
			countStmt.setString(2, "%" + member.getLastName() + "%");
			
			ResultSet rs = stmt.executeQuery();
			ResultSet countSet = countStmt.executeQuery();
			countSet.next();
			
			int tableSize = countSet.getInt("Row_count");
			
			int rowIndex = 0;
			data = new Object[tableSize][columnNames.length];
			while(rs.next()) 
				assignAttributesToTableRow(rs, rowIndex++, data);
						
 			
		} catch (SQLException e) {
			DatabaseOperation.showErrorMessage(e.getMessage());
		}
		finally {
			DatabaseOperation.closeConnection(connection);		
			}
		
		return new DefaultTableModel(data,columnNames);
	}
	
	public DefaultTableModel selectByCoachName(Coach coach) {
		String columnNames[] = {"Member ID","Member Name","Coach ID","Coach Name","Workout Date","Workout Time"};
		Connection connection = null;
		Object data[][] = null;
		String condition = " WHERE C.C_Fname LIKE ? AND C.C_Lname LIKE ?";
		String query = noConditionQuery() + selectionSourceQuery() + condition;
		String countQuery = "SELECT COUNT(*) AS Row_count" + selectionSourceQuery() + condition;
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
			
			int rowIndex = 0;
			data = new Object[tableSize][columnNames.length];
			while(rs.next()) 
				assignAttributesToTableRow(rs, rowIndex++, data);
						
 			
		} catch (SQLException e) {
			DatabaseOperation.showErrorMessage(e.getMessage());
		}
		finally {
			DatabaseOperation.closeConnection(connection);		
			}
		
		return new DefaultTableModel(data,columnNames);
	}
	
	public DefaultTableModel selectByDateTime(Date startDate,Date endDate,Time startTime,Time endTime) {
		String columnNames[] = {"Member ID","Member Name","Coach ID","Coach Name","Workout Date","Workout Time"};
		Connection connection = null;
		Object data[][] = null;
		String condition = " WHERE Class_date BETWEEN ? AND ? AND Class_time BETWEEN ? AND ?";
		String query = noConditionQuery() + selectionSourceQuery() + condition;
		String countQuery = "SELECT COUNT(*) AS Row_count" + selectionSourceQuery() + condition;
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
			
			int rowIndex = 0;
			data = new Object[tableSize][columnNames.length];
			while(rs.next()) 
				assignAttributesToTableRow(rs, rowIndex++, data);
						
 			
		} catch (SQLException e) {
			DatabaseOperation.showErrorMessage(e.getMessage());
		}
		finally {
			DatabaseOperation.closeConnection(connection);		
			}
		
		return new DefaultTableModel(data,columnNames);
	}

	@Override
	public DefaultTableModel loadTable() {
		
		String columnNames[] = {"Member ID","Member Name","Coach ID","Coach Name","Workout Date","Workout Time"};
		
		String query =  noConditionQuery();
		String rowsCountQuery = "SELECT COUNT(*) AS Rows_count FROM BOOKING";
		
		Connection connection = null;
		Object data[][] = null;
		try {
			connection = DatabaseOperation.getConnection();
			PreparedStatement stmt = connection.prepareStatement(query);
			PreparedStatement countStmt = connection.prepareStatement(rowsCountQuery);
			ResultSet rs = stmt.executeQuery();
			ResultSet countSet = countStmt.executeQuery();
			
			countSet.next();
			int tableSize = countSet.getInt("Rows_count");
			data = new Object[tableSize][columnNames.length];
			
			int rowIndex = 0;
			while(rs.next()) 
				assignAttributesToTableRow(rs, rowIndex++, data);
			
			
			
		}catch(SQLException e) {
			DatabaseOperation.showErrorMessage(e.getMessage());
		}
		finally {
			DatabaseOperation.closeConnection(connection);
		}
				
		return new DefaultTableModel(data,columnNames);
	}
	
	public List<String> getCoachesRelevantTo(Integer memberId,Date startDate,Date endDate,Time startTime,Time endTime){
		String query = "SELECT DISTINCT C.C_id,CONCAT(C_Fname,\' \',C_Lname) AS Full_name\r\n"
				+ " FROM booking B INNER JOIN coach C ON B.C_id = C.C_id \r\n"
				+ " WHERE IFNULL(M_id = ?,TRUE) AND\r\n"
				+ "IFNULL(Class_date BETWEEN ? AND ?,TRUE)\r\n"
				+ "AND IFNULL(Class_time BETWEEN ? AND ?,TRUE)";
		
		
		Connection connection = null;
	
   
      
        List<String> coaches = new ArrayList<>();

        try {
            // Establish connection
            connection = DatabaseOperation.getConnection(); // Assuming this is your method for DB connection

            // Prepare the statement
            PreparedStatement stmt = connection.prepareStatement(query);

            // Set the parameters using DatabaseOperation
            DatabaseOperation.setInt(stmt, 1, memberId);
            DatabaseOperation.setDate(stmt, 2, startDate);
            DatabaseOperation.setDate(stmt, 3, endDate);
            DatabaseOperation.setTime(stmt, 4, startTime);
            DatabaseOperation.setTime(stmt, 5, endTime);

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
	
	public List<String> getCoachesRelevantTo(Integer memberId,Date date,Time time){
		String query = "SELECT DISTINCT C.C_id,CONCAT(C_Fname,\' \',C_Lname) AS Full_name\r\n"
				+ " FROM booking B INNER JOIN coach C ON B.C_id = C.C_id \r\n"
				+ " WHERE IFNULL(M_id = ?,TRUE) AND\r\n"
				+ "IFNULL(Class_date = ?,TRUE)\r\n"
				+ "AND IFNULL(Class_time = ?,TRUE)";
		
		
		Connection connection = null;
	
   
      
        List<String> coaches = new ArrayList<>();

        try {
            // Establish connection
            connection = DatabaseOperation.getConnection(); // Assuming this is your method for DB connection

            // Prepare the statement
            PreparedStatement stmt = connection.prepareStatement(query);

            // Set the parameters using DatabaseOperation
            DatabaseOperation.setInt(stmt, 1, memberId);
            DatabaseOperation.setDate(stmt, 2, date);
            DatabaseOperation.setTime(stmt, 3, time);
           

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
	
	public List<String> getMembersRelevantTo(Integer coachId,Date date,Time time){
		String query = "SELECT DISTINCT M.M_id,CONCAT(M_Fname,\' \',M_Lname) AS Full_name\r\n"
				+ " FROM booking B INNER JOIN member M ON B.M_id = M.M_id \r\n"
				+ " WHERE IFNULL(C_id = ?,TRUE) AND\r\n"
				+ "IFNULL(Class_date = ?,TRUE)\r\n"
				+ "AND IFNULL(Class_time = ?,TRUE)";
		
		
		Connection connection = null;
	
   
      
        List<String> members = new ArrayList<>();

        try {
            // Establish connection
            connection = DatabaseOperation.getConnection(); // Assuming this is your method for DB connection

            // Prepare the statement
            PreparedStatement stmt = connection.prepareStatement(query);

            // Set the parameters using DatabaseOperation
            DatabaseOperation.setInt(stmt, 1, coachId);
            DatabaseOperation.setDate(stmt, 2, date);
            DatabaseOperation.setTime(stmt, 3, time);
           

            // Execute the query
            ResultSet resultSet = stmt.executeQuery();

            // Process the result set
            while (resultSet.next()) {
                int memberId = resultSet.getInt("C_id");
                String fullName = resultSet.getString("Full_name");

                
                members.add(memberId + ":" + fullName);
            }
        } catch (SQLException e) {
            DatabaseOperation.showErrorMessage(e.getMessage());
        } finally {
            DatabaseOperation.closeConnection(connection); // Ensures the connection is closed
        }

        return members;
	}
	
	
	public List<String> getDatesRelevantTo(Integer coachId,Integer memberId,Time time){
		String query = "SELECT DISTINCT Class_date\r\n"
				+ "FROM booking\r\n"
				+ "WHERE IFNULL(C_id = NULL,TRUE) AND\r\n"
				+ "IFNULL(M_id = NULL,TRUE)\r\n"
				+ "AND IFNULL(Class_time = NULL,TRUE)\r\n"
				+ "ORDER BY Class_date";
		
		
		Connection connection = null;
	
   
      
        List<String> dates = new ArrayList<>();

        try {
            // Establish connection
            connection = DatabaseOperation.getConnection(); // Assuming this is your method for DB connection

            // Prepare the statement
            PreparedStatement stmt = connection.prepareStatement(query);

            // Set the parameters using DatabaseOperation
            DatabaseOperation.setInt(stmt, 1, coachId);
            DatabaseOperation.setInt(stmt, 2, memberId);
            DatabaseOperation.setTime(stmt, 3, time);
           

            // Execute the query
            ResultSet resultSet = stmt.executeQuery();

            // Process the result set
            while (resultSet.next()) 
                dates.add(resultSet.getDate("Class_date").toString());
            
        } catch (SQLException e) {
            DatabaseOperation.showErrorMessage(e.getMessage());
        } finally {
            DatabaseOperation.closeConnection(connection); // Ensures the connection is closed
        }

        return dates;
	}
	
	public List<String> getTimesRelevantTo(Integer coachId,Integer memberId,Date date){
		String query = "SELECT DISTINCT Class_time\r\n"
				+ "FROM booking\r\n"
				+ "WHERE IFNULL(C_id = NULL,TRUE) AND\r\n"
				+ "IFNULL(M_id = NULL,TRUE)\r\n"
				+ "AND IFNULL(Class_date = NULL,TRUE)\r\n"
				+ "ORDER BY Class_time";
		
		
		Connection connection = null;
	
   
      
        List<String> dates = new ArrayList<>();

        try {
            // Establish connection
            connection = DatabaseOperation.getConnection(); // Assuming this is your method for DB connection

            // Prepare the statement
            PreparedStatement stmt = connection.prepareStatement(query);

            // Set the parameters using DatabaseOperation
            DatabaseOperation.setInt(stmt, 1, coachId);
            DatabaseOperation.setInt(stmt, 2, memberId);
            DatabaseOperation.setDate(stmt, 3, date);
           

            // Execute the query
            ResultSet resultSet = stmt.executeQuery();

            // Process the result set
            while (resultSet.next()) 
                dates.add(resultSet.getDate("Class_time").toString());
            
        } catch (SQLException e) {
            DatabaseOperation.showErrorMessage(e.getMessage());
        } finally {
            DatabaseOperation.closeConnection(connection); // Ensures the connection is closed
        }

        return dates;
	}
}
