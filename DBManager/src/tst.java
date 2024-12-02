
import com.toedter.calendar.JCalendar;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.util.ArrayList;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

/**
 *
 * @author OAK-1
 */
import javax.swing.JFrame;
import com.toedter.calendar.JDateChooser;
import java.awt.Dimension;
import java.sql.Time;
import java.time.LocalDate;
import javax.swing.JButton;
import javax.swing.JLabel;
import tables.BookingTable;
import tables.FitnessDataTable;
public class tst {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    ArrayList<String> ar = (ArrayList<String>)new BookingTable().getMembersRelevantTo(null, null, null);
    ArrayList<String> ar2 = (ArrayList<String>)new BookingTable().getCoachesRelevantTo(null, null, null);
    
    
           for(int i=0;i<ar.size();i++){
               
               System.out.println(ar.get(i));
               
;           }
           System.out.println("\nCOACHES");
           for(int i=0;i<ar2.size();i++){
               
               System.out.println(ar2.get(i));
               
;           }
               
    
    }
    
}
