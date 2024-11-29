package Tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import DB.DatabaseOperation;
import Entities.Gym;

public class GymTable implements Relation<Gym> {
	
	public void insert(Gym g) {
		Connection connection = null;
		try {
			connection = DatabaseOperation.getConnection();
			String insertQuery = "INSERT INTO Gym(Bno,B_name,Address) VALUES (?,?,?)";
			PreparedStatement stmt = connection.prepareStatement(insertQuery);
			
			stmt.setInt(1, g.getBno());
			stmt.setString(2, g.getbName());
			stmt.setString(3, g.getbAddress());
			
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
	public void delete(Gym deleted) {
		Connection connection = null;
		try {
			connection = DatabaseOperation.getConnection();
			String insertQuery = "DELETE FROM Gym WHERE Bno = ?";
			PreparedStatement stmt = connection.prepareStatement(insertQuery);
			
			stmt.setInt(1, deleted.getBno());
			
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
	public void update(Gym oldElement, Gym newElement) {
		
		Connection connection = null;
		try {
			connection = DatabaseOperation.getConnection();
			String insertQuery = "UPDATE Gym SET Bno = ?, B_name = ?, Address = ? WHERE Bno = ?";
			PreparedStatement stmt = connection.prepareStatement(insertQuery);
			
			stmt.setInt(1, newElement.getBno());
			stmt.setString(2, newElement.getbName());
			stmt.setString(3, newElement.getbAddress());
			stmt.setInt(4, oldElement.getBno());
			
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

	@Override
	public List<Gym> select(Gym element,String criteria) {
		String query = null;
		List<Gym> selectedResult = new ArrayList<>();
		if(criteria.equalsIgnoreCase("Bno"))
				query = "SELECT * FROM Gym WHERE Bno = ?";
		else if(criteria.equalsIgnoreCase("Address"))
			query = "SELECT * FROM Gym WHERE Address LIKE ?";
		Connection connection = null;
		try {
		connection = DatabaseOperation.getConnection();
		PreparedStatement stmt = connection.prepareStatement(query);
		
		if(criteria.equalsIgnoreCase("Bno"))
				stmt.setInt(1,element.getBno());
		
		else if(criteria.equalsIgnoreCase("Address"))
			stmt.setString(1,"%" + element.getbAddress() + "%");
		
		ResultSet rs = stmt.executeQuery();
		
		while(rs.next()) {
			Gym gRecord = new Gym(rs.getInt("Bno"),rs.getString("B_name"),rs.getString("Address"));
			selectedResult.add(gRecord);
			}
		}catch(SQLException e) {
			DatabaseOperation.showErrorMessage(e.getMessage());
		}
		finally {
			DatabaseOperation.closeConnection(connection);
		}
		return selectedResult;
	}
	
	public List<Gym> selectAll(){
		String query = "SELECT * FROM Gym";
		List<Gym> selectedResult = new ArrayList<>();
		Connection connection = null;
		try {
		connection = DatabaseOperation.getConnection();
		PreparedStatement stmt = connection.prepareStatement(query);
		
		ResultSet rs = stmt.executeQuery();
		
		while(rs.next()) {
			Gym gRecord = new Gym(rs.getInt("Bno"),rs.getString("B_name"),rs.getString("Address"));
			selectedResult.add(gRecord);
			}
		}catch(SQLException e) {
			DatabaseOperation.showErrorMessage(e.getMessage());
		}
		finally {
			DatabaseOperation.closeConnection(connection);
		}
		return selectedResult;
	}

	@Override
	public DefaultTableModel loadTable() {
		ArrayList<Gym> gyms = (ArrayList<Gym>)selectAll();
		String columnNames[] = {"Bno","Branch Name","Address"};
		Object[][] data = new Object[gyms.size()][3];

	    for (int i = 0; i < gyms.size(); i++) {
	        Gym gym = gyms.get(i);
	        data[i][0] = gym.getBno();
	        data[i][1] = gym.getbName();
	        data[i][2] = gym.getbAddress();
	    }

	    return new DefaultTableModel(data, columnNames);
	}

	public List<String> getAllKeys() {
		String query = "SELECT * FROM Gym";
		List<String> selectedResult = new ArrayList<>();
		
		Connection connection = null;
		try {
		connection = DatabaseOperation.getConnection();
		PreparedStatement stmt = connection.prepareStatement(query);
		
			
		ResultSet rs = stmt.executeQuery(); 
															
		
		while(rs.next())
			selectedResult.add("" + rs.getInt("Bno") + ":" + rs.getString("B_name"));
			
		}catch(SQLException e) {
			DatabaseOperation.showErrorMessage(e.getMessage());
		}
		finally {
			DatabaseOperation.closeConnection(connection);
		}
		return selectedResult;
	}
	
	

	
	
}
