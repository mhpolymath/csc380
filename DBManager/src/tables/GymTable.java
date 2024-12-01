package tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import database.DatabaseOperation;
import entities.Gym;

public class GymTable implements Relation<Gym> {

    @Override
    public int insert(Gym gym) throws SQLException {
        Connection connection = null;
        try {
            connection = DatabaseOperation.getConnection();
            String insertQuery = "INSERT INTO Gym(Bno, B_name, Address) VALUES (?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(insertQuery);

            DatabaseOperation.setInt(stmt, 1, gym.getBno());
            DatabaseOperation.setString(stmt, 2, gym.getName());
            DatabaseOperation.setString(stmt, 3, gym.getAddress());

            return stmt.executeUpdate();
        } finally {
            DatabaseOperation.closeConnection(connection);
        }
    }

    @Override
    public int delete(Gym deleted) throws SQLException {
        Connection connection = null;
        try {
            connection = DatabaseOperation.getConnection();
            String deleteQuery = "DELETE FROM Gym WHERE Bno = ?";
            PreparedStatement stmt = connection.prepareStatement(deleteQuery);

            DatabaseOperation.setInt(stmt, 1, deleted.getBno());

            return stmt.executeUpdate();
        } finally {
            DatabaseOperation.closeConnection(connection);
        }
    }

    @Override
    public int update(Gym oldElement, Gym newElement) throws SQLException {
        Connection connection = null;
        try {
            connection = DatabaseOperation.getConnection();
            String updateQuery = "UPDATE Gym SET Bno = ?, B_name = ?, Address = ? WHERE Bno = ?";
            PreparedStatement stmt = connection.prepareStatement(updateQuery);

            DatabaseOperation.setInt(stmt, 1, newElement.getBno());
            DatabaseOperation.setString(stmt, 2, newElement.getName());
            DatabaseOperation.setString(stmt, 3, newElement.getAddress());
            DatabaseOperation.setInt(stmt, 4, oldElement.getBno());

            return stmt.executeUpdate();
        } finally {
            DatabaseOperation.closeConnection(connection);
        }
    }

    private String noConditionQuery() {
        return "SELECT * FROM Gym";
    }

    private void assignAttributesToRow(ResultSet rs, int rowIndex, Object[][] data) throws SQLException {
        data[rowIndex][0] = rs.getInt("Bno");
        data[rowIndex][1] = rs.getString("B_name");
        data[rowIndex][2] = rs.getString("Address");
    }

    public DefaultTableModel selectByBno(int bno) {
        String columnNames[] = {"Bno", "Branch Name", "Address"};
        Object[][] data = null;
        String condition = " WHERE Bno = ?";
        String query = noConditionQuery() + condition;
        String countQuery = "SELECT COUNT(*) AS Row_count FROM Gym" + condition;

        Connection connection = null;
        try {
            connection = DatabaseOperation.getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            PreparedStatement countStmt = connection.prepareStatement(countQuery);

            stmt.setInt(1, bno);
            countStmt.setInt(1, bno);

            ResultSet rs = stmt.executeQuery();
            ResultSet countSet = countStmt.executeQuery();

            countSet.next();
            int tableSize = countSet.getInt("Row_count");
            data = new Object[tableSize][columnNames.length];

            int rowIndex = 0;
            while (rs.next()) {
                assignAttributesToRow(rs, rowIndex++, data);
            }

        } catch (SQLException e) {
            DatabaseOperation.showErrorMessage(e.getMessage());
        } finally {
            DatabaseOperation.closeConnection(connection);
        }

        return new DefaultTableModel(data, columnNames);
    }

    public DefaultTableModel selectByAddress(String address) {
        String columnNames[] = {"Bno", "Branch Name", "Address"};
        Object[][] data = null;
        String condition = " WHERE Address LIKE ?";
        String query = noConditionQuery() + condition;
        String countQuery = "SELECT COUNT(*) AS Row_count FROM Gym" + condition;

        Connection connection = null;
        try {
            connection = DatabaseOperation.getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            PreparedStatement countStmt = connection.prepareStatement(countQuery);

            stmt.setString(1, "%" + address + "%");
            countStmt.setString(1, "%" + address + "%");

            ResultSet rs = stmt.executeQuery();
            ResultSet countSet = countStmt.executeQuery();

            countSet.next();
            int tableSize = countSet.getInt("Row_count");
            data = new Object[tableSize][columnNames.length];

            int rowIndex = 0;
            while (rs.next()) {
                assignAttributesToRow(rs, rowIndex++, data);
            }

        } catch (SQLException e) {
            DatabaseOperation.showErrorMessage(e.getMessage());
        } finally {
            DatabaseOperation.closeConnection(connection);
        }

        return new DefaultTableModel(data, columnNames);
    }

    @Override
    public DefaultTableModel loadTable() {
        String columnNames[] = {"Bno", "Branch Name", "Address"};
        Object[][] data = null;
        String query = noConditionQuery();
        String countQuery = "SELECT COUNT(*) AS Row_count FROM Gym";

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
            while (rs.next()) {
                assignAttributesToRow(rs, rowIndex++, data);
            }

        } catch (SQLException e) {
            DatabaseOperation.showErrorMessage(e.getMessage());
        } finally {
            DatabaseOperation.closeConnection(connection);
        }

        return new DefaultTableModel(data, columnNames);
    }

    public List<String> getAllKeys() {
        String query = "SELECT Bno, B_name FROM Gym";
        List<String> selectedResult = new ArrayList<>();

        Connection connection = null;
        try {
            connection = DatabaseOperation.getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                selectedResult.add(rs.getInt("Bno") + ":" + rs.getString("B_name"));
            }

        } catch (SQLException e) {
            DatabaseOperation.showErrorMessage(e.getMessage());
        } finally {
            DatabaseOperation.closeConnection(connection);
        }

        return selectedResult;
    }
}
