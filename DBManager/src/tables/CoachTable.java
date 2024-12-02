package tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import database.DatabaseOperation;
import entities.Coach;
import entities.Gym;

public class CoachTable implements Relation<Coach> {

    @Override
    public int insert(Coach coach) throws SQLException {
        Connection connection = null;
        try {
            connection = DatabaseOperation.getConnection();
            String insertQuery = "INSERT INTO Coach(C_id, C_Fname, C_Lname, C_phone, Salary, Bno) VALUES (?, ?, ?, ?, ?, ?)";

            PreparedStatement stmt = connection.prepareStatement(insertQuery);

            DatabaseOperation.setInt(stmt, 1, coach.getId());
            DatabaseOperation.setString(stmt, 2, coach.getFirstName());
            DatabaseOperation.setString(stmt, 3, coach.getLastName());
            DatabaseOperation.setString(stmt, 4, coach.getPhone());
            DatabaseOperation.setDouble(stmt, 5, coach.getSalary());
            DatabaseOperation.setInt(stmt, 6, coach.getBno());

            return stmt.executeUpdate();
        } finally {
            DatabaseOperation.closeConnection(connection);
        }
    }

    @Override
    public int delete(Coach deleted) throws SQLException {
        Connection connection = null;
        try {
            connection = DatabaseOperation.getConnection();
            String deleteQuery = "DELETE FROM Coach WHERE C_id = ?";
            PreparedStatement stmt = connection.prepareStatement(deleteQuery);

            DatabaseOperation.setInt(stmt, 1, deleted.getId());

            return stmt.executeUpdate();
        } finally {
            DatabaseOperation.closeConnection(connection);
        }
    }

    @Override
    public int update(Coach oldElement, Coach newElement) throws SQLException {
        Connection connection = null;
        try {
            connection = DatabaseOperation.getConnection();
            String updateQuery = "UPDATE Coach SET C_id = ?, C_Fname = ?, C_Lname = ?, C_phone = ?, Salary = ?, Bno = ? WHERE C_id = ?";
            PreparedStatement stmt = connection.prepareStatement(updateQuery);

            DatabaseOperation.setInt(stmt, 1, newElement.getId());
            DatabaseOperation.setString(stmt, 2, newElement.getFirstName());
            DatabaseOperation.setString(stmt, 3, newElement.getLastName());
            DatabaseOperation.setString(stmt, 4, newElement.getPhone());
            DatabaseOperation.setDouble(stmt, 5, newElement.getSalary());
            DatabaseOperation.setInt(stmt, 6, newElement.getBno());
            DatabaseOperation.setInt(stmt, 7, oldElement.getId());

            return stmt.executeUpdate();
        } finally {
            DatabaseOperation.closeConnection(connection);
        }
    }

    private String noConditionQuery() {
        return "SELECT C.*, CONCAT(G.Bno, \":\", G.B_name) AS Branch";
    }

    private String selectionSourceQuery() {
        return " FROM Coach C INNER JOIN Gym G ON C.Bno = G.Bno";
    }

    private void assignAttributesToRow(ResultSet rs, int rowIndex, Object data[][]) throws SQLException {
        data[rowIndex][0] = rs.getInt("C_id");
        data[rowIndex][1] = rs.getString("C_Fname");
        data[rowIndex][2] = rs.getString("C_Lname");
        data[rowIndex][3] = rs.getString("C_phone");
        data[rowIndex][4] = rs.getDouble("Salary");
        data[rowIndex][5] = rs.getString("Branch");
    }

    public DefaultTableModel selectByID(Coach coach) {
        String columnNames[] = {"Coach ID", "First Name", "Last Name", "Phone", "Salary", "Branch"};
        Object[][] data = null;
        String condition = " WHERE C.C_id = ?";
        String query = noConditionQuery() + selectionSourceQuery() + condition;
        String countQuery = "SELECT COUNT(*) AS Row_count" + selectionSourceQuery() + condition;

        Connection connection = null;
        try {
            connection = DatabaseOperation.getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            PreparedStatement countStmt = connection.prepareStatement(countQuery);
            stmt.setInt(1, coach.getId());
            countStmt.setInt(1, coach.getId());

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

    public DefaultTableModel selectByBranch(Gym gym) {
        String columnNames[] = {"Coach ID", "First Name", "Last Name", "Phone", "Salary", "Branch"};
        Object[][] data = null;
        String condition = " WHERE C.Bno = ?";
        String query = noConditionQuery() + selectionSourceQuery() + condition;
        String countQuery = "SELECT COUNT(*) AS Row_count" + selectionSourceQuery() + condition;

        Connection connection = null;
        try {
            connection = DatabaseOperation.getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            PreparedStatement countStmt = connection.prepareStatement(countQuery);
            stmt.setInt(1, gym.getBno());
            countStmt.setInt(1, gym.getBno());

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

    public DefaultTableModel loadTable() {
        String columnNames[] = {"Coach ID", "First Name", "Last Name", "Phone", "Salary", "Branch"};
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

    public List<String> getAllCoaches() {
        String query = "SELECT * FROM Coach";
        List<String> selectedResult = new ArrayList<>();

        Connection connection = null;
        try {
            connection = DatabaseOperation.getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                selectedResult.add("" + rs.getInt("C_id") + ":" + rs.getString("C_Fname") + " " + rs.getString("C_Lname"));
            }

        } catch (SQLException e) {
            DatabaseOperation.showErrorMessage(e.getMessage());
        } finally {
            DatabaseOperation.closeConnection(connection);
        }
        return selectedResult;
    }
}
