import java.util.ArrayList;
/**
 * Write a description of class check here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class check
{
    /**
     * An example of a method - replace this comment with your own
     * 
     * @param  y   a sample parameter for a method
     * @return     the sum of x and y 
     */
    public static int sampleMethod()
    {
        ArrayList<String> B = new ArrayList<String>();
        String lastChar = "I";
        B.add("abcd");
        B.add("abdc");
        int count = 0;
        for (String string : B){
            if ((count % 2) == 0){
                for (int j = string.length(); j >= 0; j--){
                    String temp = string.substring(0,j) 
                        + lastChar 
                        + string.substring(j,string.length());
                    System.out.println(temp);
                }
            } else {
                for (int j = 0; j <= string.length(); j++){
                    String temp = string.substring(0,j) 
                        + lastChar 
                        + string.substring(j,string.length());
                    System.out.println(temp);
                }
            }
            count++;
        }
        return lastChar.length();
    }
    
}
