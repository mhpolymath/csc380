
import java.util.ArrayList;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

/**
 *
 * @author OAK-1
 */
import tables.FitnessDataTable;
public class tst {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("HAHA");
        ArrayList<String> ar = (ArrayList<String>)new FitnessDataTable().getMembersRelevantTo(3);
        
        for(String str:ar)
                System.out.println(str);
    }
    
}
