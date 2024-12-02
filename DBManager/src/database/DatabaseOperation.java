package database;

import java.awt.Component;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;

import javax.swing.JOptionPane;

public class DatabaseOperation {
	private static final String PASS = "";
	private static final String USER = "root";
	private static final String URL = "jdbc:mysql://localhost:3306/gymerfinal";

	public static Connection getConnection() throws SQLException {

		return DriverManager.getConnection(URL, USER, PASS);
	}

	public static void closeConnection(Connection connection) {
		if (connection == null)
			return;

		try {
			connection.close();
		} catch (SQLException e) {
			showErrorMessage(e.getMessage());
		}
	}

	private static void playErrorSound() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				Toolkit.getDefaultToolkit().beep();
			}
		}).start();
	}

	public static void showErrorMessage(String message) {
		showErrorMessage(null,message);
	}
	
	public static void showErrorMessage(Component component,String message) {
		playErrorSound();
		JOptionPane.showMessageDialog(component, message, "ERROR", JOptionPane.ERROR_MESSAGE);
	}
	
	public static void showSuccessMessage(Component component, String message) {
		JOptionPane.showMessageDialog(component, message, "Success", JOptionPane.INFORMATION_MESSAGE);
	}
	public static void showSuccessMessage(String message) {
		showSuccessMessage(null,message);
	}
	
	
	public static void setInt(PreparedStatement stmt, int index, Integer value) {
		if (stmt == null)
			return;

		try {
			if (value == null)
				stmt.setNull(index, java.sql.Types.INTEGER);

			else
				stmt.setInt(index, value);
		} catch (SQLException e) {
			showErrorMessage(e.getMessage());
		}
	}

	public static void setString(PreparedStatement stmt, int index, String value) {
		if (stmt == null)
			return;

		try {
			if (value == null)
				stmt.setNull(index, java.sql.Types.INTEGER);

			else
				stmt.setString(index, value);
		} catch (SQLException e) {
			showErrorMessage(e.getMessage());
		}
	}

	public static void setDouble(PreparedStatement stmt, int index, Double value) {
		if (stmt == null)
			return;

		try {
			if (value == null)
				stmt.setNull(index, java.sql.Types.INTEGER);

			else
				stmt.setDouble(index, value);
		} catch (SQLException e) {
			showErrorMessage(e.getMessage());
		}
	}

	public static void setDate(PreparedStatement stmt, int index, Date value) {
		if (stmt == null)
			return;

		try {
			if (value == null)
				stmt.setNull(index, java.sql.Types.INTEGER);

			else
				stmt.setDate(index, value);
		} catch (SQLException e) {
			showErrorMessage(e.getMessage());
		}
	}

	public static void setTime(PreparedStatement stmt, int index, Time value) {
		if (stmt == null)
			return;

		try {
			if (value == null)
				stmt.setNull(index, java.sql.Types.INTEGER);

			else
				stmt.setTime(index, value);
		} catch (SQLException e) {
			showErrorMessage(e.getMessage());
		}
	}
}
