import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import DB.DatabaseOperation;
import Entities.Booking;
import Entities.Coach;
import Entities.Gym;
import Entities.Member;
import Tables.BookingTable;
import Tables.CoachTable;
import Tables.GymTable;
import Tables.WorkoutTable;

public class mytest {

    public static void main(String[] args) {
        
        BookingTable bt = new BookingTable();  // Create your GymTable object
        GymTable gt = new GymTable();
        CoachTable ct = new CoachTable();
        WorkoutTable wt = new WorkoutTable();
        
        Date startDate = Date.valueOf(LocalDate.of(2024, 11, 28)); // yyyy-MM-dd
        Date endDate = Date.valueOf(LocalDate.of(2024, 11, 29));   // yyyy-MM-dd
        Time startTime = Time.valueOf(LocalTime.of(13, 0));        // HH:mm:ss
        Time endTime = Time.valueOf(LocalTime.of(22, 59));         // HH:mm:ss

        // Pass these to your JTable initialization
        JTable jt = new JTable(wt.selectByDateTime(startDate, endDate, startTime, endTime)); // Load the table data
        JScrollPane scrollPane = new JScrollPane(jt);  // Add the JTable to a JScrollPane
       
        List<String> coaches = bt.getCoachesRelevantTo(2, null, null, null, null); // Pass an example member ID, e.g., 1

        // Create a JComboBox and populate it with the coaches list
        JComboBox<String> coachComboBox = new JComboBox<>();
        
        for (String coach : coaches) {
            coachComboBox.addItem(coach); // Adding coach IDs and names to the combo box
        }
        coachComboBox.setSelectedItem(null);
        
        // Set a smaller size for the combo box
        coachComboBox.setPreferredSize(new Dimension(200, 30)); // Width: 200px, Height: 30px

        // Create a JFrame to display the combo box
        JFrame frame = new JFrame("Select Coach");
        frame.setSize(400, 200);  // Set a reasonable frame size
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        // Add the combo box to the frame
        frame.add(new JLabel("Select a Coach:"), BorderLayout.NORTH);
        frame.add(coachComboBox, BorderLayout.CENTER);
        System.out.println(coachComboBox.getSelectedItem());
        frame.setVisible(true);
    }
}
