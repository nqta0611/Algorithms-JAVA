/**
 * A simulator to find the Maximum Total Value of a weighted intevals schedule
 * @author Anh Nguyen 
 * @version 11/05/2015
 */
public class WgtIntScheduler
{
    /** Find the set of tasks which has maximum total value with
     * constraint that no task overlapping other
     */
    public int[] getOptSet (int[] stime, int[] ftime, int[] weight) {
        // Build a list of Tasks - O(n)
        Task[] allTask = new Task[ftime.length + 1];
        for (int i = 0; i < ftime.length; i++)
            allTask[i + 1] = new Task(stime[i], ftime[i], weight[i], i+1);
        allTask[0] = new Task(0,0,0,0); // Base case
        java.util.Arrays.sort(allTask); // sort all task in order of ascending finish time - O(nlogn)

        // Compute the previous compatible task - O(nlogn)
        int[] prev = new int[allTask.length];
        for (int i = 0; i < allTask.length; i++) {
            prev[i] = 0;
            outloop:
            for (int j = i-1; j >= 0 ; j--)
                if (allTask[i].startTime >= allTask[j].endTime){
                    prev[i] = j;
                    break outloop;
                }
        }
        
        // compute the maximum total value, also keep track of where that value came from - O(n)
        int[] totalValue = new int[allTask.length]; // Store F(i) - max total value
        int[] traceTable = new int[allTask.length]; // Store where this total value came from
        totalValue[0] = 0; // Base case
        for (int i = 1; i < allTask.length; i++) {
            int x = (allTask[i].value + totalValue[prev[i]]);
            int y = totalValue[i-1];
            // compute F(i) = max {v(i) + F(prev(i)) , F(i-1)}
            if (x > y) {
                totalValue[i] = x;
                traceTable[i] = prev[i];
            }
            else {
                totalValue[i] = y;
                traceTable[i] = -1;
            }        
        }
        
        // Trace back all the task in the maximum total value set, using the traceTable above - O(n)
        java.util.ArrayList<Integer> solution = new java.util.ArrayList<Integer>();
        int index = allTask.length - 1; 
        int previousIndex = traceTable[index]; 
        while (previousIndex != 0) {
            if (previousIndex > 0) {
                solution.add(allTask[index].indexInArray);
                index = traceTable[index];
            }
            else
                index--;
            previousIndex = traceTable[index];
        }
        solution.add(allTask[index].indexInArray);
        // convert solution from ArrayList to Array
        int[] toReturn = new int[solution.size()];
        for (int i = 0; i < solution.size(); i++)
            toReturn[i] = solution.get(i);     
        java.util.Arrays.sort(toReturn);
        
        return toReturn;
    }
   
    /** Inner class Task represent a task of this problem
     * A Task contain 4 attributes: start time, end time, value, and its index in the given array
     */
    public class Task implements Comparable{
        public int startTime,endTime,value,indexInArray;
        public Task(int startT, int endT, int val, int ind) {
            startTime = startT;
            endTime = endT;
            value = val;
            indexInArray = ind;
        }     
        public int compareTo(Object other) {
            int result = 0;
            result = (this.endTime > ((Task)other).endTime)? 1 : 
             (this.endTime == ((Task)other).endTime) ? 0 : -1;
            return result;
        }
    }
}
