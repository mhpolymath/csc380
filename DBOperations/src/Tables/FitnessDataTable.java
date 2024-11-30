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
import Entities.FitnessData;
import Entities.Gym;
import Entities.Member;

public class FitnessDataTable implements Relation<FitnessData> {

	@Override
	public void insert(FitnessData element) {
		Connection connection = null;
		try {
			connection = DatabaseOperation.getConnection();
			String insertQuery = "INSERT INTO fitness_data (M_id, Record_number, Record_date, Height, Weight, Fat_rate, Muscle_mass) VALUES (?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement stmt = connection.prepareStatement(insertQuery);

			DatabaseOperation.setInt(stmt, 1, element.getMemberId());
			DatabaseOperation.setInt(stmt, 2, element.getRecordNumber());
			DatabaseOperation.setDate(stmt, 3, element.getRecordDate());
			DatabaseOperation.setDouble(stmt, 4, element.getHeight());
			DatabaseOperation.setDouble(stmt, 5, element.getWeight());
			DatabaseOperation.setDouble(stmt, 6, element.getFatRate());
			DatabaseOperation.setDouble(stmt, 7, element.getMuscleMass());

			int result = stmt.executeUpdate();
			if (result == 1)
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
	public void delete(FitnessData element) {
		Connection connection = null;
		try {
			connection = DatabaseOperation.getConnection();
			String deleteQuery = "DELETE FROM fitness_data WHERE M_id = ? AND Record_number = ?";
			PreparedStatement stmt = connection.prepareStatement(deleteQuery);

			stmt.setInt(1, element.getMemberId());
			stmt.setInt(2, element.getRecordNumber());

			int result = stmt.executeUpdate();
			if (result == 1)
				DatabaseOperation.showSuccessMessage("Delete successful.");

			else
				DatabaseOperation.showErrorMessage("Delete failed. Check if deleted entry exists.");

		} catch (SQLException e) {
			DatabaseOperation.showErrorMessage(e.getMessage());
		} finally {
			DatabaseOperation.closeConnection(connection);
		}

	}

	@Override
	public void update(FitnessData oldElement, FitnessData newElement) {
		Connection connection = null;
		try {
			connection = DatabaseOperation.getConnection();
			String updateQuery = "UPDATE fitness_data SET Record_date = ?, Height = ?, Weight = ?, Fat_rate = ?, Muscle_mass = ? WHERE M_id = ? AND Record_number = ?";
			PreparedStatement stmt = connection.prepareStatement(updateQuery);

			DatabaseOperation.setDate(stmt, 1, newElement.getRecordDate());
			DatabaseOperation.setDouble(stmt, 2, newElement.getHeight());
			DatabaseOperation.setDouble(stmt, 3, newElement.getWeight());
			DatabaseOperation.setDouble(stmt, 4, newElement.getFatRate());
			DatabaseOperation.setDouble(stmt, 5, newElement.getMuscleMass());
			DatabaseOperation.setInt(stmt, 6, oldElement.getMemberId());
			DatabaseOperation.setInt(stmt, 7, oldElement.getRecordNumber());

			int result = stmt.executeUpdate();
			if (result == 1)
				DatabaseOperation.showSuccessMessage("Update successful.");

			else
				DatabaseOperation.showErrorMessage("Update failed. Check if updated entry exists.");

		} catch (SQLException e) {
			DatabaseOperation.showErrorMessage(e.getMessage());
		} finally {
			DatabaseOperation.closeConnection(connection);
		}

	}

	private void assignAttributesToRow(ResultSet rs, int rowIndex, Object data[][]) throws SQLException {
		data[rowIndex][0] = rs.getInt("M_id");
		data[rowIndex][1] = rs.getString("Full_name");
		data[rowIndex][2] = rs.getInt("Record_number");
		data[rowIndex][3] = rs.getDate("Record_date");
		data[rowIndex][4] = rs.getDouble("Height");
		data[rowIndex][5] = rs.getDouble("Weight");
		data[rowIndex][6] = rs.getDouble("Fat_rate");
		data[rowIndex][7] = rs.getDouble("Muscle_mass");
	}

	private String noConditionQuery() {
		return "SELECT F.M_id,CONCAT(M.M_Fname,' ',M.M_Lname) AS Full_name, F.Record_number,F.Record_date,F.Height,F.Weight\r\n"
				+ ",F.Fat_rate, F.Muscle_mass";

	}

	private String selectionSourceQuery() {
		return " FROM fitness_data F INNER JOIN member M ON F.M_id = M.M_id";
	}

	public DefaultTableModel selectByMemberID(Member member) {
		String columnNames[] = { "Member ID", "Member Name", "Record Number", "Record Date", "Height (m)",
				"Weight (kg)", "Fat Rate", "Muscle Mass (kg)" };
		Object[][] data = null;
		String condition = " WHERE F.M_id = ?";
		String query = noConditionQuery() + selectionSourceQuery() + condition;
		String countQuery = "SELECT COUNT(*) AS Row_count" + selectionSourceQuery() + condition;
		Connection connection = null;

		try {
			connection = DatabaseOperation.getConnection();
			PreparedStatement stmt = connection.prepareStatement(query);
			PreparedStatement countStmt = connection.prepareStatement(countQuery);
			stmt.setInt(1, member.getId());
			countStmt.setInt(1, member.getId());

			ResultSet rs = stmt.executeQuery();
			ResultSet countSet = countStmt.executeQuery();
			countSet.next();
			int tableSize = countSet.getInt("Row_count");
			data = new Object[tableSize][columnNames.length];
			int rowIndex = 0;
			while (rs.next())
				assignAttributesToRow(rs, rowIndex++, data);

		} catch (SQLException e) {
			DatabaseOperation.showErrorMessage(e.getMessage());
		} finally {
			DatabaseOperation.closeConnection(connection);
		}

		return new DefaultTableModel(data, columnNames);
	}

	@Override
	public DefaultTableModel loadTable() {

		String columnNames[] = { "Member ID", "Member Name", "Record Number", "Record Date", "Height (m)",
				"Weight (kg)", "Fat Rate", "Muscle Mass (kg)" };
		Object[][] data = null;

		String query = noConditionQuery() + selectionSourceQuery();
		String countQuery = "SELECT COUNT(*) AS Row_count" + selectionSourceQuery();
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
			while (rs.next())
				assignAttributesToRow(rs, rowIndex++, data);

		} catch (SQLException e) {
			DatabaseOperation.showErrorMessage(e.getMessage());
		} finally {
			DatabaseOperation.closeConnection(connection);
		}

		return new DefaultTableModel(data, columnNames);
	}
	
	
	public List<String> getAllMembers() {
		String query = "SELECT F.M_id, CONCAT(M_Fname,\' \',M_Lname) AS Full_name FROM Member M INNER JOIN fitness_data F ON M.M_id = F.M_id";

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
