package tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import database.DatabaseOperation;
import entities.Gym;
import entities.Member;
import java.awt.Component;

public class MemberTable implements Relation<Member> {

	@Override
	public int insert(Member element) throws SQLException {
		Connection connection = null;
		try {
			connection = DatabaseOperation.getConnection();
			String insertQuery = "INSERT INTO Member(M_id,M_Fname,M_Lname,M_phone) VALUES (?,?,?,?)";
			PreparedStatement stmt = connection.prepareStatement(insertQuery);
			
			DatabaseOperation.setInt(stmt, 1, element.getId());
			DatabaseOperation.setString(stmt, 2, element.getFirstName());
			DatabaseOperation.setString(stmt, 3, element.getLastName());
			DatabaseOperation.setString(stmt, 4, element.getPhone());
			
			return stmt.executeUpdate();
			
		}
		finally {
			DatabaseOperation.closeConnection(connection);
		}
                
          
		
	}

	@Override
	public int delete(Member element) throws SQLException{
		Connection connection = null;
		try {
			connection = DatabaseOperation.getConnection();
			String deleteQuery = "DELETE FROM Member WHERE M_id = ?";
			PreparedStatement stmt = connection.prepareStatement(deleteQuery);
			
			stmt.setInt(1, element.getId());
			
			return stmt.executeUpdate();
			
			
		}
		finally {
			DatabaseOperation.closeConnection(connection);
		}
		
	}

	@Override
	public int update(Member oldElement, Member newElement) throws SQLException{
		Connection connection = null;
		try {
			connection = DatabaseOperation.getConnection();
			String updateQuery = "UPDATE Member SET M_id = ?, M_Fname = ?, M_Lname = ?, M_phone = ? WHERE M_id = ?";
			PreparedStatement stmt = connection.prepareStatement(updateQuery);
			
			DatabaseOperation.setInt(stmt,1, newElement.getId());
			DatabaseOperation.setString(stmt,2, newElement.getFirstName());
			DatabaseOperation.setString(stmt,3, newElement.getLastName());
			DatabaseOperation.setString(stmt,4, newElement.getPhone());
			DatabaseOperation.setInt(stmt,5, oldElement.getId());
			
			
			return stmt.executeUpdate();
			
                }
		finally {
			DatabaseOperation.closeConnection(connection);
		}
		
	}
	
	private String defaultQuery() {
		return "SELECT *";
	}
	private String selectionSource() {
		return " FROM Member M";
	}
	private void assignAttributesToRow(ResultSet rs, int rowIndex,Object data[][]) throws SQLException {
		
				data[rowIndex][0] = rs.getInt("M_id");
				data[rowIndex][1] = rs.getString("M_Fname");
				data[rowIndex][2] = rs.getString("M_Lname");
				data[rowIndex][3] = rs.getString("M_phone");
	}
	public DefaultTableModel selectByMemberName(Member member) {
		String columnNames[] = {"Member ID","First Name","Last Name","Phone"};
		Object[][] data = null;
		String condition = " WHERE M_Fname LIKE ? AND M_Lname LIKE ?";
		String query = defaultQuery() + selectionSource() + condition;
		String countQuery = "SELECT COUNT(*) AS Row_count" + selectionSource() + condition;

	    Connection connection = null;
	    try {
	    	connection = DatabaseOperation.getConnection();
	    	PreparedStatement stmt = connection.prepareStatement(query);
	    	PreparedStatement countStmt = connection.prepareStatement(countQuery);
	    	
	    	DatabaseOperation.setString(stmt,1, "%"+member.getFirstName()+"%");
	    	DatabaseOperation.setString(countStmt,1, "%"+member.getFirstName()+"%");
	    	
                DatabaseOperation.setString(stmt,2, "%"+member.getLastName()+"%");
	    	DatabaseOperation.setString(countStmt,2,"%"+member.getLastName()+"%");
	    	
	    	ResultSet rs = stmt.executeQuery();
	    	ResultSet countSet = countStmt.executeQuery();
	    	countSet.next();
	    	int tableSize = countSet.getInt("Row_count");
	    	data = new Object[tableSize][columnNames.length];
	    	int rowIndex = 0;
	    	
	    	while(rs.next())
	    		assignAttributesToRow(rs, rowIndex++, data);
	    }catch(SQLException e) {
	    	DatabaseOperation.showErrorMessage(e.getMessage());
	    }finally {
	    	DatabaseOperation.closeConnection(connection);
	    }

	    return new DefaultTableModel(data, columnNames);
	}

	@Override
	public DefaultTableModel loadTable() {
		
		String columnNames[] = {"Member ID","First Name","Last Name","Phone"};
		Object[][] data = null;
		String query = defaultQuery() + selectionSource();
		String countQuery = "SELECT COUNT(*) AS Row_count" + selectionSource();

	    Connection connection = null;
	    try {
	    	connection = DatabaseOperation.getConnection();
	    	PreparedStatement stmt = connection.prepareStatement(query);
	    	PreparedStatement countStmt = connection.prepareStatement(countQuery);
	    	
	    	ResultSet rs = stmt.executeQuery();
	    	ResultSet countSet = countStmt.executeQuery();
	    	countSet.next();
	    	int tableSize = countSet.getInt("Row_count");
	    	data = new Object[tableSize][columnNames.length];
	    	int rowIndex = 0;
	    	
	    	while(rs.next())
	    		assignAttributesToRow(rs, rowIndex++, data);
	    }catch(SQLException e) {
	    	DatabaseOperation.showErrorMessage(e.getMessage());
	    }finally {
	    	DatabaseOperation.closeConnection(connection);
	    }

	    return new DefaultTableModel(data, columnNames);
	}
	
	public List<String> getAllMembers() {
		String query = "SELECT M_id, CONCAT(M_Fname,\' \',M_Lname) AS Full_name FROM Member M";

		Connection connection = null;
		List<String> members = new ArrayList<>();

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
				int memberId = resultSet.getInt("M_id");
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
	
}
