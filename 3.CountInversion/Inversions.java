/*
 * @author Anh Nguyen
 * @class CPE349 
 * @version 10/22/2015
 */

public  class Inversions
{
    /**
     * 
     */
    public int invCounter(int ranking[])
    {  
        // Return 0 when array is divided to smallest possible size
        if (ranking.length <= 1){
            return 0;
        }
        
        // Divide Step
        // Divide the array into 2 sub array 
        int mid = ranking.length / 2;
        int[] left = new int[mid];
        int[] right = new int[ranking.length - mid];
        
        for (int i = 0; i < mid; i++) {
            left[i] = ranking[i];
        }
        for (int i = 0; i < ranking.length - mid; i++) {
            right[i] = ranking[mid + i];
        }
        
        // Conquer Step
        // Call the recursion on each sub array
        int countl = invCounter(left);
        int countR = invCounter(right);
        
        // Combine Step
        // Merge 2 sub arrays together, count the inversion while merging
        int countM = merge (left, right, ranking);

        return (countl + countR + countM);  
    }

    /**
     * Merging 2 array, and count the number of inversions
     * @param left is the first array to merge
     * @param right is the second array to merge
     * @param result is the destination array
     * @return number of inversion
     */
    private int merge (int left[], int right[], int result[])
    {
        int l = 0;
        int r = 0;
        int rs = 0;
        int count = 0;
        
        // Traversing both arrays
        while ((l < left.length) && (r < right.length) )
        {
            // Add all element from the left array which is smaller than the first element of the right array
            if ( left[l] <= right[r] )
                result [rs] = left[l++];
            // Add all element from the right array which is smaller than the first element of the left array,
            // also count the inversion
            else 
            {
                result [rs] = right[r++];
                count += left.length - l;
            }
            rs++;
        }

        // Add all elements left over to the result array
        if ( l == left.length ) {
            while(r < right.length){
                result [rs++] = right[r++];
            }
        }
        if ( r == right.length ) {
            while(l < left.length){
                result [rs++] = left[l++];
            }
        }
        return count;
    }
}
