import java.util.ArrayList;
/**
 * Generator of combinatorial objects
 * 
 * @author Anh Nguyen
 * @Assignment2
 * @class CPE349-Algorithm Fall 2015
 * @version 10/12/2015
 */
public class CombObjects
{
    /**
     * Generating an ArrayList of strings where each string is a bitstring 
     *              (contains only the characters 0 and 1) 
     * @param size: is the size of bit string.
     */
    public ArrayList<String> getGrayCode(int size)
    {
        ArrayList<String> A = new ArrayList<String>();
        ArrayList<String> temp = new ArrayList<String>();  
        if (size > 0)
        {
            temp = getGrayCode(size-1);
            
            // Take all the subsets of the n-1 items in a Gray code order and prepend 
            // a 0 to create a subset of n items without the n-th item.
            for (int i = 0; i < temp.size(); i++)
            {
                A.add(temp.get(i)+"0");
            }

            // reverse the list of subsets of the n-1 items in a Gray code order and
            // prepend a 1 to them to create subsets of items that do contain the n-th item
            for (int i = temp.size()-1; i >= 0; i--)
            {
                A.add(temp.get(i)+"1");
            }
        }
        else
        {
            A.add("");
        }
        return A;
    }

    /**
     * The permutation that returns an ArrayList of Strings in lexicographic order. 
     * @param str: the input argument which can be assumed to be a string of 
     *             distinct lower case letters (in alphabetical order)
     * @return the ArrayList of lexicographic permutation
     */
    public ArrayList<String> getLexPerm (String str){
        ArrayList<String> A = new ArrayList<String>();
        ArrayList<String> B = new ArrayList<String>();

        // If the string is empty return it
        if (str.length() == 0){
            A.add("");
            return A;
        }

        // Loop through all character positions of the string containing 
        // the characters to be permuted, for each character
        for (int i = 0; i< str.length(); i++){
            
            // Form a simpler word by removing the character
            String oneChar = str.substring(i,i+1);
            String simplerWord = str.substring(0,i) + str.substring(i+1,str.length());
            
            // Generate all permutations of the simpler word recursively
            B = new ArrayList<String>();
            B = getLexPerm(simplerWord);
            
            // Add the removed character to the front of each permutation of the simpler word,
            // and add the resulting permutation to a list
            for (String string : B){
                String temp = oneChar + string;
                A.add(temp);
            }
        }
        
        // Return all these newly constructed permutations
        return A;
    }

    /**
     * Permuation that returns an ArrayList of Strings that satisfy a minimum change requirement. 
     * @param str: the input argument which can be assumed to be a string of 
     *             distinct lower case letters (in alphabetical order). 
     * @return the ArrayList of the permutation with minimum change.
     */
    public ArrayList<String> getMinChgPerm (String str){
        ArrayList<String> A = new ArrayList<String>();
        ArrayList<String> B = new ArrayList<String>();
        
        // If the string is empty return it
        if (str.length() == 0){
            A.add("");
            return A;
        }
        
        // Remove the last character, call it x, of the string
        String x = str.substring(str.length()-1);
        String simplerWord = str.substring(0, str.length()-1);
        
        // Generate all permutations (satisfying min change requirement) of the simpler word
        B = getMinChgPerm(simplerWord);
        
        // Loop over the returned permutations
        int count = 0;
        for (String string : B){
            if ((count % 2) == 0){
                // insert the removed character into a returned permutation 
                // into all possible positions moving right to left
                for (int j = string.length(); j >= 0; j--){
                    String temp = string.substring(0,j) 
                        + x 
                        + string.substring(j,string.length());
                    A.add(temp);
                }
            } else {
                // insert the removed character into the next returned 
                // permutation into all possible positions moving left to right
               
                for (int j = 0; j <= string.length(); j++){
                    String temp = string.substring(0,j) 
                        + x
                        + string.substring(j,string.length());
                    A.add(temp);
                }
            }
            count++;
        }
        
        // Return all these newly constructed permutations
        return A;
    }
}
