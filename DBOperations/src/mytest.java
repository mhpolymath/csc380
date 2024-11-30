import java.awt.BorderLayout;
import java.awt.Dimension;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import DB.DatabaseOperation;
import Entities.Booking;
import Entities.Coach;
import Entities.Gym;
import Entities.Member;
import Tables.BookingTable;
import Tables.CoachTable;
import Tables.FitnessDataTable;
import Tables.GymTable;
import Tables.MemberTable;
import Tables.MembershipTable;
import Tables.WorkoutTable;

public class mytest {

	public static void main(String[] args) {
		
		
		
		BookingTable bt = new BookingTable();  // Create your GymTable object
		GymTable gt = new GymTable();
		CoachTable ct = new CoachTable();
		WorkoutTable wt = new WorkoutTable();
		FitnessDataTable ft = new FitnessDataTable();
		MembershipTable mst = new MembershipTable();
		MemberTable mt = new MemberTable();
		
		

//		JTable jt = new JTable(mst.selectByBranch(new Gym(0)));// Load the table data
//        JScrollPane scrollPane = new JScrollPane(jt);  // Add the JTable to a JScrollPane
//       
//        
//        JFrame frame = new JFrame("Gym Table");  // Create a JFrame
//        frame.setSize(700, 700);  // Set the size of the frame
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Close the program when the frame closes
//        frame.setLayout(new BorderLayout());  // Use BorderLayout for better layout management
//
//        frame.add(scrollPane, BorderLayout.CENTER);  // Add the JScrollPane to the frame
//        frame.setVisible(true);  // Make the frame visible
	
	
	}

}
