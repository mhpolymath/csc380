package tables;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import database.DatabaseOperation;
import entities.Booking;
import entities.Coach;
import entities.Member;

public class BookingTable implements Relation<Booking> {

    @Override
    public int insert(Booking booking) throws SQLException {
        Connection connection = null;
        try {
            connection = DatabaseOperation.getConnection();
            String insertQuery = "INSERT INTO Booking(M_id,C_id,Class_date,Class_time) VALUES (?,?,?,?)";

            PreparedStatement stmt = connection.prepareStatement(insertQuery);

            DatabaseOperation.setInt(stmt, 1, booking.getMemberId());
            DatabaseOperation.setInt(stmt, 2, booking.getCoachId());
            DatabaseOperation.setDate(stmt,3,booking.getWorkoutDate());
            DatabaseOperation.setTime(stmt,4,booking.getWorkoutTime());
            return stmt.executeUpdate();

        } finally {
            DatabaseOperation.closeConnection(connection);
        }

    }

    @Override
    public int delete(Booking deleted) throws SQLException {
        Connection connection = null;
        try {
            connection = DatabaseOperation.getConnection();
            String deleteQuery = "DELETE FROM Booking WHERE M_id = ? AND C_id = ? AND Class_date = ? AND Class_time = ?";
            PreparedStatement stmt = connection.prepareStatement(deleteQuery);

            DatabaseOperation.setInt(stmt, 1, deleted.getMemberId());
            DatabaseOperation.setInt(stmt, 2, deleted.getCoachId());
            DatabaseOperation.setDate(stmt,3,deleted.getWorkoutDate());
            DatabaseOperation.setTime(stmt,4,deleted.getWorkoutTime());

            return stmt.executeUpdate();

        } finally {
            DatabaseOperation.closeConnection(connection);
        }

    }

    @Override
    public int update(Booking oldElement, Booking newElement) throws SQLException {
        Connection connection = null;
        try {
            connection = DatabaseOperation.getConnection();
            String updateQuery = "UPDATE Booking SET M_id = ?,C_id = ?, Class_date = ?, Class_time = ? WHERE M_id = ? AND C_id = ? AND Class_date = ? AND Class_time = ?";
            PreparedStatement stmt = connection.prepareStatement(updateQuery);

            DatabaseOperation.setInt(stmt, 1, newElement.getMemberId());
            DatabaseOperation.setInt(stmt, 2, newElement.getCoachId());
            DatabaseOperation.setDate(stmt, 3, newElement.getWorkoutDate());
            DatabaseOperation.setTime(stmt, 4, newElement.getWorkoutTime());
            DatabaseOperation.setInt(stmt, 5, oldElement.getMemberId());
            DatabaseOperation.setInt(stmt, 6, oldElement.getCoachId());
            DatabaseOperation.setDate(stmt, 7, oldElement.getWorkoutDate());
            DatabaseOperation.setTime(stmt, 8, oldElement.getWorkoutTime());

            return stmt.executeUpdate();

        } finally {
            DatabaseOperation.closeConnection(connection);
        }

    }

    private void assignAttributesToTableRow(ResultSet rs, int rowIndex, Object data[][]) throws SQLException {

        data[rowIndex][0] = rs.getInt("Member ID");
        data[rowIndex][1] = rs.getString("Member Name");
        data[rowIndex][2] = rs.getInt("Coach ID");
        data[rowIndex][3] = rs.getString("Coach Name");
        data[rowIndex][4] = rs.getDate("Workout Date");
        data[rowIndex][5] = rs.getTime("Workout Time");
    }

    private String noConditionQuery() {
        return "Select B.M_id AS \"Member ID\" ,CONCAT(M.M_Fname,\' \',IFNULL(M.M_Lname,'')) AS \"Member Name\",\r\n"
                + " B.C_id AS \"Coach ID\", CONCAT(C.C_Fname,\' \',IFNULL(C.C_Lname,'')) AS \"Coach Name\",\r\n"
                + " Class_date AS \"Workout Date\", Class_time AS \"Workout Time\" \r\n";

    }

    private String selectionSourceQuery() {
        return " FROM\r\n" + " member M INNER JOIN booking B ON M.M_id = B.M_id\r\n"
                + " INNER JOIN coach C ON B.C_id = C.C_id";
    }

    public DefaultTableModel selectByKeys(Booking booking) {
        String columnNames[] = {"Member ID", "Member Name", "Coach ID", "Coach Name", "Workout Date", "Workout Time"};
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
            while (rs.next()) {
                assignAttributesToTableRow(rs, rowIndex++, data);
            }

        } catch (SQLException e) {
            DatabaseOperation.showErrorMessage(e.getMessage());
        } finally {
            DatabaseOperation.closeConnection(connection);
        }

        return new DefaultTableModel(data, columnNames);
    }

    public DefaultTableModel selectByMember(Member member) {
        String columnNames[] = {"Member ID", "Member Name", "Coach ID", "Coach Name", "Workout Date", "Workout Time"};
        Connection connection = null;
        Object data[][] = null;
        String condition = " WHERE M.M_id = ?";
        String query = noConditionQuery() + selectionSourceQuery() + condition;
        String countQuery = "SELECT COUNT(*) AS Row_count" + selectionSourceQuery() + condition;
        try {
            connection = DatabaseOperation.getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, member.getId());

            PreparedStatement countStmt = connection.prepareStatement(countQuery);
            countStmt.setInt(1, member.getId());

            ResultSet rs = stmt.executeQuery();
            ResultSet countSet = countStmt.executeQuery();
            countSet.next();

            int tableSize = countSet.getInt("Row_count");

            int rowIndex = 0;
            data = new Object[tableSize][columnNames.length];
            while (rs.next()) {
                assignAttributesToTableRow(rs, rowIndex++, data);
            }

        } catch (SQLException e) {
            DatabaseOperation.showErrorMessage(e.getMessage());
        } finally {
            DatabaseOperation.closeConnection(connection);
        }

        return new DefaultTableModel(data, columnNames);
    }

    public DefaultTableModel selectByCoach(Coach coach) {
        String columnNames[] = {"Member ID", "Member Name", "Coach ID", "Coach Name", "Workout Date", "Workout Time"};
        Connection connection = null;
        Object data[][] = null;
        String condition = " WHERE C.C_id = ?";
        String query = noConditionQuery() + selectionSourceQuery() + condition;
        String countQuery = "SELECT COUNT(*) AS Row_count" + selectionSourceQuery() + condition;
        try {
            connection = DatabaseOperation.getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, coach.getId());

            PreparedStatement countStmt = connection.prepareStatement(countQuery);
            countStmt.setInt(1, coach.getId());

            ResultSet rs = stmt.executeQuery();
            ResultSet countSet = countStmt.executeQuery();
            countSet.next();

            int tableSize = countSet.getInt("Row_count");

            int rowIndex = 0;
            data = new Object[tableSize][columnNames.length];
            while (rs.next()) {
                assignAttributesToTableRow(rs, rowIndex++, data);
            }

        } catch (SQLException e) {
            DatabaseOperation.showErrorMessage(e.getMessage());
        } finally {
            DatabaseOperation.closeConnection(connection);
        }

        return new DefaultTableModel(data, columnNames);
    }

    public DefaultTableModel selectByDateTime(Date startDate, Date endDate, Time startTime, Time endTime) {
        String columnNames[] = {"Member ID", "Member Name", "Coach ID", "Coach Name", "Workout Date", "Workout Time"};
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
            while (rs.next()) {
                assignAttributesToTableRow(rs, rowIndex++, data);
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

        String columnNames[] = {"Member ID", "Member Name", "Coach ID", "Coach Name", "Workout Date", "Workout Time"};

        String query = noConditionQuery() + selectionSourceQuery();
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
            while (rs.next()) {
                assignAttributesToTableRow(rs, rowIndex++, data);
            }

        } catch (SQLException e) {
            DatabaseOperation.showErrorMessage(e.getMessage());
        } finally {
            DatabaseOperation.closeConnection(connection);
        }

        return new DefaultTableModel(data, columnNames);
    }

    public List<String> getCoachesRelevantTo(Integer memberId, Date date, Time time) {
        String query = "SELECT DISTINCT C.C_id, CONCAT(C_Fname, \' \', C_Lname) AS Full_name\n"
                + "FROM workout_class W INNER JOIN coach C ON W.C_id = C.C_id \n"
                + " JOIN membership_at MT ON C.Bno = MT.Bno\n"
                + "WHERE CURDATE() <= MT.End_date AND IFNULL(MT.M_id = ?,TRUE)\n"
                + "AND IFNULL(W.Class_date = ?,TRUE) AND IFNULL(W.Class_time = ? , TRUE)\n"
                + "ORDER BY C.C_id, Full_name;";

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

    public List<String> getMembersRelevantTo(Integer coachId, Date date, Time time) {
        String query = "SELECT DISTINCT M.M_id, CONCAT(M_Fname, \' \', M_Lname) AS Full_name\n"
                + "FROM membership_at MT INNER JOIN member M ON MT.M_id = M.M_id AND CURDATE() <= MT.End_date\n"
                + "LEFT JOIN coach C ON MT.Bno = C.Bno\n"
                + "INNER JOIN workout_class W ON C.C_id = W.C_id\n"
                + "WHERE IFNULL(W.C_id = ?,TRUE)\n"
                + "AND IFNULL(W.Class_date = ?,TRUE)\n"
                + "AND IFNULL(W.Class_time = ?,TRUE) ORDER BY M_id,Full_name";

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

    public List<String> getDatesRelevantTo(Integer coachId, Integer memberId, Time time) {
        String query = "SELECT DISTINCT Class_date\n"
                + "FROM membership_at MT LEFT JOIN coach C ON MT.Bno = C.Bno \n"
                + "INNER JOIN  workout_class W ON C.C_id = W.C_id\n"
                + "\n"
                + "WHERE CURDATE() <= MT.End_date AND IFNULL(W.C_id = ?, TRUE) \n"
                + "AND\n"
                + "IFNULL(MT.M_id = ?, TRUE)\n"
                + "AND IFNULL(Class_time = ?, TRUE)\n"
                + "\n"
                + "ORDER BY class_date";

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

    public List<String> getTimesRelevantTo(Integer coachId, Integer memberId, Date date) {
        String query = "SELECT DISTINCT Class_time\n"
                + "FROM membership_at MT LEFT JOIN coach C ON MT.Bno = C.Bno \n"
                + "INNER JOIN  workout_class W ON C.C_id = W.C_id\n"
                + "\n"
                + "WHERE CURDATE() <= MT.End_date AND IFNULL(W.C_id = ?, TRUE)\n"
                + " AND IFNULL(MT.M_id = ?, TRUE)\n"
                + "AND IFNULL(Class_date = ?, TRUE)\n"
                + "\n"
                + "ORDER BY class_time";

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
            while (resultSet.next()) {
                dates.add(resultSet.getTime("Class_time").toString());
            }

        } catch (SQLException e) {
            DatabaseOperation.showErrorMessage(e.getMessage());
        } finally {
            DatabaseOperation.closeConnection(connection); // Ensures the connection is closed
        }

        return dates;
    }
}
