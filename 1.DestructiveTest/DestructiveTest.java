import java.util.ArrayList;
import java.lang.Math;
/**
 * A simulator to find out the highest safe rung to drop a device from
 * 
 * @author Anh Nguyen 
 * @version 9/30/2015
 */

public class DestructiveTest {
   
   public static ArrayList<Integer> findHighestSafeRung(int ladHeight, int hiSafeRung){
      //data
      ArrayList<Integer>  results = new ArrayList<Integer>() ;
      int sqrtN = (int)java.lang.Math.sqrt((double)ladHeight);
      int testRung = 0;
      int device1Broke = -1;
      int device2Broke = -1;
      int totalDrop = 0;
      int index = 1;
      boolean device1 = true;
      boolean device2 = true;
      
      outloop:
      // divide the ladder into sqrt(n)+1 interval, 
      // try every last rung of each interval until device 1 broke
      while (index <= (ladHeight/sqrtN + 1)){
          totalDrop++;
          testRung = (index * sqrtN) > ladHeight? ladHeight: (index * sqrtN);
          // when device1 broke, use device2 to test the last interval
          if (testRung > hiSafeRung){
              device1Broke = testRung;
              device1 = false;
              
              // go back to previous interval
              testRung = (index - 1) * (int)sqrtN;
              // Using device 2, try every rung in the last interval until device 2 broke
              while (testRung < (index * (int)sqrtN)){
                  totalDrop++;
                  testRung++;
                  if (testRung > hiSafeRung){
                      device2Broke = testRung;
                      device2 = false;
                      break outloop;
                  }
              }
              break;
          }
          //stop when reach the highest rung
          if (testRung == ladHeight){
              break;
          }
          index++;    
      }
     
      results.add(ladHeight);
      results.add(hiSafeRung);
      results.add(device1 ? testRung : testRung-1);
      results.add(device1Broke);
      results.add(device2Broke);
      results.add(totalDrop);
      
      return results;
   }
}


