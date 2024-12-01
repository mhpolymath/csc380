package tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import database.DatabaseOperation;
import entities.FitnessData;
import entities.Member;

public class FitnessDataTable implements Relation<FitnessData> {

    @Override
    public int insert(FitnessData data) throws SQLException {
        Connection connection = null;
        try {
            connection = DatabaseOperation.getConnection();
            String query = "INSERT INTO FitnessData (M_id, Record_number, Record_date, Height, Weight, Fat_rate, Muscle_mass) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(query);

            DatabaseOperation.setInt(stmt, 1, data.getMemberId());
            DatabaseOperation.setInt(stmt, 2, data.getRecordNumber());
            DatabaseOperation.setDate(stmt, 3, data.getRecordDate());
            DatabaseOperation.setDouble(stmt, 4, data.getHeight());
            DatabaseOperation.setDouble(stmt, 5, data.getWeight());
            DatabaseOperation.setDouble(stmt, 6, data.getFatRate());
            DatabaseOperation.setDouble(stmt, 7, data.getMuscleMass());

            return stmt.executeUpdate();
        } finally {
            DatabaseOperation.closeConnection(connection);
        }
    }

    @Override
    public int delete(FitnessData data) throws SQLException {
        Connection connection = null;
        try {
            connection = DatabaseOperation.getConnection();
            String query = "DELETE FROM FitnessData WHERE M_id = ? AND Record_number = ?";
            PreparedStatement stmt = connection.prepareStatement(query);

            stmt.setInt(1, data.getMemberId());
            stmt.setInt(2, data.getRecordNumber());

            return stmt.executeUpdate();
        } finally {
            DatabaseOperation.closeConnection(connection);
        }
    }

    @Override
    public int update(FitnessData oldData, FitnessData newData) throws SQLException {
        Connection connection = null;
        try {
            connection = DatabaseOperation.getConnection();
            String query = "UPDATE FitnessData SET Record_date = ?, Height = ?, Weight = ?, Fat_rate = ?, Muscle_mass = ? WHERE M_id = ? AND Record_number = ?";
            PreparedStatement stmt = connection.prepareStatement(query);

            DatabaseOperation.setDate(stmt, 1, newData.getRecordDate());
            DatabaseOperation.setDouble(stmt, 2, newData.getHeight());
            DatabaseOperation.setDouble(stmt, 3, newData.getWeight());
            DatabaseOperation.setDouble(stmt, 4, newData.getFatRate());
            DatabaseOperation.setDouble(stmt, 5, newData.getMuscleMass());
            stmt.setInt(6, oldData.getMemberId());
            stmt.setInt(7, oldData.getRecordNumber());

            return stmt.executeUpdate();
        } finally {
            DatabaseOperation.closeConnection(connection);
        }
    }

    private void assignAttributesToRow(ResultSet rs, int rowIndex, Object[][] data) throws SQLException {
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
        return "SELECT F.M_id, CONCAT(M.M_Fname, \' \', M.M_Lname) AS Full_name, F.Record_number, F.Record_date, F.Height, F.Weight, F.Fat_rate, F.Muscle_mass";
    }

    private String selectionSourceQuery() {
        return " FROM fitness_data F INNER JOIN member M ON F.M_id = M.M_id";
    }

    public DefaultTableModel selectByMemberID(Member member) {
        String columnNames[] = {"Member ID", "Member Name", "Record Number", "Record Date", "Height (m)",
            "Weight (kg)", "Fat Rate", "Muscle Mass (kg)"};
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
        String columnNames[] = {"Member ID", "Member Name", "Record Number", "Record Date", "Height (m)",
            "Weight (kg)", "Fat Rate", "Muscle Mass (kg)"};
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

    public List<String> getAllMembers() {
        String query = "SELECT F.M_id, CONCAT(M_Fname, \' \', M_Lname) AS Full_name "
                + "FROM Member M INNER JOIN fitness_data F ON M.M_id = F.M_id";

        Connection connection = null;
        List<String> members = new ArrayList<>();

        try {
            connection = DatabaseOperation.getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);

            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                int memberId = resultSet.getInt("M_id");
                String fullName = resultSet.getString("Full_name");
                members.add(memberId + ":" + fullName);
            }
        } catch (SQLException e) {
            DatabaseOperation.showErrorMessage(e.getMessage());
        } finally {
            DatabaseOperation.closeConnection(connection);
        }

        return members;
    }

    public List<Member> getAllMembersObj(){
        String query = "SELECT F.M_id, CONCAT(M_Fname, \' \', M_Lname) AS Full_name "
                + "FROM Member M INNER JOIN fitness_data F ON M.M_id = F.M_id";

        Connection connection = null;
        List<Member> members = new ArrayList<>();

        try {
            connection = DatabaseOperation.getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);

            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                int memberId = resultSet.getInt("M_id");
                String fullName = resultSet.getString("Full_name");
                members.add(new Member(memberId));
            }
        } catch (SQLException e) {
            DatabaseOperation.showErrorMessage(e.getMessage());
        } finally {
            DatabaseOperation.closeConnection(connection);
        }

        return members;
    }

    public List<String> getMembersRelevantTo(Integer recordNumber) {
        String query = "SELECT DISTINCT M.M_id,CONCAT(M_Fname,\' \',M_Lname) AS Full_name\n"
                + " FROM fitness_data F INNER JOIN member M ON F.M_id = M.M_id \n"
                + " WHERE IFNULL(Record_number = ?,TRUE)";

        Connection connection = null;

        List<String> members = new ArrayList<>();

        try {
            // Establish connection
            connection = DatabaseOperation.getConnection(); // Assuming this is your method for DB connection

            // Prepare the statement
            PreparedStatement stmt = connection.prepareStatement(query);

            // Set the parameters using DatabaseOperation
            DatabaseOperation.setInt(stmt, 1, recordNumber);

            // Execute the query
            ResultSet resultSet = stmt.executeQuery();

            // Process the result set
            while (resultSet.next()) {
                int coachId = resultSet.getInt("M_id");
                String fullName = resultSet.getString("Full_name");

                members.add(coachId + ":" + fullName);
            }
        } catch (SQLException e) {
            DatabaseOperation.showErrorMessage(e.getMessage());
        } finally {
            DatabaseOperation.closeConnection(connection); // Ensures the connection is closed
        }

        return members;
    }

    public List<String> getRecordNumbersRelevantTo(Integer memberId) {
        String query = "SELECT DISTINCT Record_number\n"
                + " FROM fitness_data\n"
                + " WHERE IFNULL(M_id = ?,TRUE)\n"
                + " ORDER BY Record_number";

        Connection connection = null;

        List<String> recordNumbers = new ArrayList<>();

        try {
            // Establish connection
            connection = DatabaseOperation.getConnection(); // Assuming this is your method for DB connection

            // Prepare the statement
            PreparedStatement stmt = connection.prepareStatement(query);

            // Set the parameters using DatabaseOperation
            DatabaseOperation.setInt(stmt, 1, memberId);

            // Execute the query
            ResultSet resultSet = stmt.executeQuery();

            // Process the result set
            while (resultSet.next()) {
                 Integer rec_number = resultSet.getInt("Record_number");
                recordNumbers.add(String.valueOf(rec_number));
            }
        } catch (SQLException e) {
            DatabaseOperation.showErrorMessage(e.getMessage());
        } finally {
            DatabaseOperation.closeConnection(connection); // Ensures the connection is closed
        }

        return recordNumbers;
    }
}
