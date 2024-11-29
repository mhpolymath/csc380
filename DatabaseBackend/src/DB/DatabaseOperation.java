package DB;

import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class DatabaseOperation {
	private static final String PASS = "";
	private static final String USER = "root";
	private static final String URL = "jdbc:mysql://localhost:3306/gymerfinal";
	
	public static Connection getConnection() throws SQLException{
		
		return DriverManager.getConnection(URL, USER, PASS);
	}
	
	public static void closeConnection(Connection connection) {
		if(connection == null)
				return;
		
		try {
		connection.close();
		}catch(SQLException e) {
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
		playErrorSound();
		JOptionPane.showMessageDialog(null, message,"ERROR",JOptionPane.ERROR_MESSAGE);
	}
	
	public static void showSuccessMessage(String message) {
		JOptionPane.showMessageDialog(null, message,"Success",JOptionPane.INFORMATION_MESSAGE);
	}
}
