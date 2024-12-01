package tables;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import database.DatabaseOperation;
import entities.FitnessData;
import entities.Gym;
import entities.Member;
import entities.Membership;

public class MembershipTable implements Relation<Membership> {

    @Override
    public int insert(Membership element) throws SQLException {
        String query = "INSERT INTO membership_at (M_id, Bno, Start_date, End_date) VALUES (?, ?, ?, ?)";
        Connection connection = null;
        try {
            connection = DatabaseOperation.getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);

            DatabaseOperation.setInt(stmt, 1, element.getMemberId());
            DatabaseOperation.setInt(stmt, 2, element.getBno());
            DatabaseOperation.setDate(stmt, 3, element.getStartDate());
            DatabaseOperation.setDate(stmt, 4, element.getEndDate());

            return stmt.executeUpdate();
        } finally {
            DatabaseOperation.closeConnection(connection);
        }
    }

// Delete existing membership record
    @Override
    public int delete(Membership element) throws SQLException {
        String query = "DELETE FROM membership_at WHERE M_id = ? AND Bno = ?";
        Connection connection = null;
        try {
            connection = DatabaseOperation.getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);

            stmt.setInt(1, element.getMemberId());
            stmt.setInt(2, element.getBno());

            return stmt.executeUpdate();
        } finally {
            DatabaseOperation.closeConnection(connection);
        }
    }

// Update existing membership record
    @Override
    public int update(Membership oldElement, Membership newElement) throws SQLException {
        String query = "UPDATE membership_at SET Start_date = ?, End_date = ? WHERE M_id = ? AND Bno = ?";
        Connection connection = null;
        try {
            connection = DatabaseOperation.getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);

            DatabaseOperation.setDate(stmt, 1, newElement.getStartDate());
            DatabaseOperation.setDate(stmt, 2, newElement.getEndDate());
            DatabaseOperation.setInt(stmt, 3, oldElement.getMemberId());
            DatabaseOperation.setInt(stmt, 4, oldElement.getBno());

            return stmt.executeUpdate();
        } finally {
            DatabaseOperation.closeConnection(connection);
        }
    }

    private String noConditionQuery() {
        return "SELECT SH.M_id,CONCAT(M.M_Fname,\' \',M_Lname) AS Full_name, SH.Bno, G.B_name,SH.Start_date,SH.End_date";
    }

    private String selectionSourceQuery() {
        return " FROM member M INNER JOIN membership_at SH ON M.M_id = SH.M_id\r\n"
                + " INNER JOIN gym G ON SH.Bno = G.Bno";
    }

    private String orderBy() {
        return " ORDER BY Start_date,End_date";
    }

    private void assignAttributesToRow(ResultSet rs, int rowIndex, Object data[][]) throws SQLException {

        data[rowIndex][0] = rs.getInt("M_id");
        data[rowIndex][1] = rs.getString("Full_name");
        data[rowIndex][2] = rs.getInt("Bno");
        data[rowIndex][3] = rs.getString("B_name");
        data[rowIndex][4] = rs.getDate("Start_date");
        data[rowIndex][5] = rs.getDate("End_date");
    }

    public DefaultTableModel loadTableActiveMemberhips() {
        String columnNames[] = {"Member ID", "Member Name", "Branch Number", "Branch Name", "Start Date", "End Date"};
        Object[][] data = null;
        String condition = " WHERE Start_date <= CURDATE() AND End_date >= CURDATE()";
        String query = noConditionQuery() + selectionSourceQuery() + condition + orderBy();
        String countQuery = "SELECT COUNT(*) AS Row_count" + selectionSourceQuery() + condition;

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

    public DefaultTableModel loadTable() {
        String columnNames[] = {"Member ID", "Member Name", "Branch Number", "Branch Name", "Start Date", "End Date"};
        Object[][] data = null;
        String query = noConditionQuery() + selectionSourceQuery() + orderBy();
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

    public DefaultTableModel selectByMemberAndBranch(Member member, Gym gym) {
        String columnNames[] = {"Member ID", "Member Name", "Branch Number", "Branch Name", "Start Date", "End Date"};
        Object[][] data = null;
        String condition = " WHERE SH.M_id = ? AND SH.Bno = ?";
        String query = noConditionQuery() + selectionSourceQuery() + condition + orderBy();
        String countQuery = "SELECT COUNT(*) AS Row_count" + selectionSourceQuery() + condition;

        Connection connection = null;
        try {
            connection = DatabaseOperation.getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            PreparedStatement countStmt = connection.prepareStatement(countQuery);

            // Set the parameters for both queries
            stmt.setInt(1, member.getId());
            stmt.setInt(2, gym.getBno());
            countStmt.setInt(1, member.getId());
            countStmt.setInt(2, gym.getBno());

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

    public DefaultTableModel selectByMember(Member member) {
        String columnNames[] = {"Member ID", "Member Name", "Branch Number", "Branch Name", "Start Date", "End Date"};
        Object[][] data = null;
        String condition = " WHERE SH.M_id = ?";
        String query = noConditionQuery() + selectionSourceQuery() + condition + orderBy();
        String countQuery = "SELECT COUNT(*) AS Row_count" + selectionSourceQuery() + condition;

        Connection connection = null;
        try {
            connection = DatabaseOperation.getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            PreparedStatement countStmt = connection.prepareStatement(countQuery);

            // Set the parameter for both queries
            stmt.setInt(1, member.getId());
            countStmt.setInt(1, member.getId());

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
        String columnNames[] = {"Member ID", "Member Name", "Branch Number", "Branch Name", "Start Date", "End Date"};
        Object[][] data = null;
        String condition = " WHERE SH.Bno = ?";
        String query = noConditionQuery() + selectionSourceQuery() + condition + orderBy();
        String countQuery = "SELECT COUNT(*) AS Row_count" + selectionSourceQuery() + condition;

        Connection connection = null;
        try {
            connection = DatabaseOperation.getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            PreparedStatement countStmt = connection.prepareStatement(countQuery);

            // Set the parameter for both queries
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

    public List<String> getMembersRelevantTo(Integer bno, Date startDate) {
        String query = "SELECT DISTINCT M.M_id,CONCAT(M_Fname,\' \',M_Lname) AS Full_name\r\n"
                + " FROM membership_at SH INNER JOIN member M ON M.M_id = SH.M_id\r\n"
                + "WHERE IFNULL(Bno = ?,TRUE) AND\r\n" + "IFNULL(Start_date = ? , TRUE)";

        Connection connection = null;

        List<String> members = new ArrayList<>();

        try {
            // Establish connection
            connection = DatabaseOperation.getConnection(); // Assuming this is your method for DB connection

            // Prepare the statement
            PreparedStatement stmt = connection.prepareStatement(query);

            // Set the parameters using DatabaseOperation
            DatabaseOperation.setInt(stmt, 1, bno);
            DatabaseOperation.setDate(stmt, 2, startDate);

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

    public List<String> getGymsRelevantTo(Integer memberId, Date startDate) {
        String query = "SELECT DISTINCT G.Bno,G.B_name\r\n"
                + "FROM membership_at SH INNER JOIN Gym G ON G.Bno = SH.Bno\r\n"
                + "WHERE IFNULL(M_id = ?,TRUE) AND\r\n"
                + "IFNULL(Start_date = ? , TRUE)";

        Connection connection = null;

        List<String> gyms = new ArrayList<>();

        try {
            // Establish connection
            connection = DatabaseOperation.getConnection(); // Assuming this is your method for DB connection

            // Prepare the statement
            PreparedStatement stmt = connection.prepareStatement(query);

            // Set the parameters using DatabaseOperation
            DatabaseOperation.setInt(stmt, 1, memberId);
            DatabaseOperation.setDate(stmt, 2, startDate);

            // Execute the query
            ResultSet resultSet = stmt.executeQuery();

            // Process the result set
            while (resultSet.next()) {
                int bno = resultSet.getInt("Bno");
                String fullName = resultSet.getString("B_name");

                gyms.add(memberId + ":" + fullName);
            }
        } catch (SQLException e) {
            DatabaseOperation.showErrorMessage(e.getMessage());
        } finally {
            DatabaseOperation.closeConnection(connection); // Ensures the connection is closed
        }

        return gyms;
    }

    public List<String> getDatesRelevantTo(Integer memberId, Integer bno) {
        String query = "SELECT DISTINCT Start_date\r\n"
                + "FROM membership_at\r\n"
                + "WHERE IFNULL(M_id = ?,TRUE) AND\r\n"
                + "IFNULL(Bno = ?,TRUE)\r\n"
                + "ORDER BY Start_date";

        Connection connection = null;

        List<String> dates = new ArrayList<>();

        try {
            // Establish connection
            connection = DatabaseOperation.getConnection(); // Assuming this is your method for DB connection

            // Prepare the statement
            PreparedStatement stmt = connection.prepareStatement(query);

            // Set the parameters using DatabaseOperation
            DatabaseOperation.setInt(stmt, 1, memberId);
            DatabaseOperation.setInt(stmt, 2, bno);

            // Execute the query
            ResultSet resultSet = stmt.executeQuery();

            // Process the result set
            while (resultSet.next()) {
                dates.add(resultSet.getDate("Class_date").toString());
            }

        } catch (SQLException e) {
            DatabaseOperation.showErrorMessage(e.getMessage());
        } finally {
            DatabaseOperation.closeConnection(connection); // Ensures the connection is closed
        }

        return dates;
    }

}
