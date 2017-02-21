import java.util.ArrayList;
/**
 * A driver to drive the Destructive Test
 * 
 * @author Anh Nguyen
 * @version 9/30/2015
 */
public class DestructiveTestDriv
{
    public static void main(int ladHeight, int hiSafeRung){
        ArrayList<Integer>  results = new ArrayList<Integer>() ;
        results = DestructiveTest.findHighestSafeRung(ladHeight, hiSafeRung);
        
        System.out.println("Height of ladder:    " + results.get(0));
        System.out.println("Actual SafeRung:     " + results.get(1));
        System.out.println("     ---------------");
        System.out.println("Calculated SafeRung: " + results.get(2));
        System.out.println("1st Device Broke at: " + results.get(3));
        System.out.println("2nd Device Broke at: " + results.get(4));
        System.out.println("Total drop:          " + results.get(5));
        System.out.println("----------------------------");
    }
}
