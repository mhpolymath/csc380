import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import DB.DatabaseOperation;
import Entities.Gym;
import Tables.GymTable;

public class mytest {

	public static void main(String[] args) {
	
		
		
		GymTable gt = new GymTable();
		
		ArrayList<Gym> ar = (ArrayList<Gym>)gt.select(new Gym(33,null,"Jeddah"), "Bno");
		
		for(int i=0;i<ar.size();i++)
				System.out.println(ar.get(i));
	}

}
