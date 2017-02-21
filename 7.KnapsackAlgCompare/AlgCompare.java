import java.util.*;
import java.io.*;
/** Knapsack Algorithm Compare
 * 
 * @author Anh Nguyen
 * @version 11/30/2015
 */
public class AlgCompare
{
    int totalItem; int capacity;
    item[] itemArray;
    
    /** Inner class item which represent an item with value, weight, and its index in the test file */
    public class item implements Comparable{
        int index; int value; int weight;
        public item (int ind, int val, int wei) {
            index = ind; value = val; weight = wei;
        }
        public int compareTo(Object other) {
            return (double)(this.value)/(double)(this.weight) < (double)(((item)other).value)/(double)(((item)other).weight) ?
                1 : (double)(this.value)/(double)(this.weight) > (double)(((item)other).value)/(double)(((item)other).weight) ? -1 : 0;
        }
    }

    /** Read the test file */
    public void readFile(String fileName) throws IOException{
        Scanner sc = new Scanner(new File(fileName));
        totalItem = sc.nextInt();
        itemArray = new item[totalItem];
        for (int i = 0; i < totalItem; i ++){
            itemArray[i] = new item(sc.nextInt(), sc.nextInt(), sc.nextInt());
        }
        capacity = sc.nextInt();
    }

    /** Helper method to generate the list of bit string */
    private ArrayList<String> getGrayCode(int size) {
        ArrayList<String> A = new ArrayList<String>();
        ArrayList<String> temp = new ArrayList<String>();  
        if (size > 0) {
            temp = getGrayCode(size-1);         
            for (int i = 0; i < temp.size(); i++)
                A.add(temp.get(i)+"0");
            for (int i = temp.size()-1; i >= 0; i--)
                A.add(temp.get(i)+"1");
        }
        else
            A.add("");
        return A;
    }
    
    /** First Algorithm - Brute Fore */
    public void fullEnumeration() {
        long timming = System.nanoTime();
        ArrayList<String> grayCode = getGrayCode(20);
        int[] weightOfEachSolution = new int[grayCode.size()];
        int[] valueOfEachSolution = new int[grayCode.size()];
        int bestSolution = 0;
        // calculate the total value & total weight of each candidate solution
        for (int i = 0; i < grayCode.size(); i ++) {
            String str = grayCode.get(i);
            for (int j = 0; j < str.length(); j++)
                if (str.charAt(j) == '1') {
                    weightOfEachSolution[i] += itemArray[j].weight;
                    valueOfEachSolution[i] += itemArray[j].value;
                }
        }
        // find the best solution from the total value & total weight calculated above
        for(int i = 0; i < grayCode.size(); i ++)
            if (weightOfEachSolution[i] <= capacity && valueOfEachSolution[i] >= valueOfEachSolution[bestSolution])
                bestSolution = i;
        timming = (System.nanoTime() - timming)/1000;
        // Print result
        System.out.println("Using Brute force the best feasible solution found: "
            + valueOfEachSolution[bestSolution] + " " +  weightOfEachSolution[bestSolution] + "\t(" + timming + "us)");
        String str = grayCode.get(bestSolution);
        for (int j = 0; j < str.length(); j++)
            if (str.charAt(j) == '1')
                System.out.print(itemArray[j].index + " ");
        System.out.println();
    }

    /** Second algorithm - Greedy */
    public void greedyAlgorithm() {
        long timming = System.nanoTime();
        item[] copyItems = new item[totalItem];
        int totalValue = 0;
        int totalWeight = 0;
        int index = 0;
        int[] resultIndex = new int[totalItem];
        // get the copy array of items 
        for (int i = 0; i < totalItem; i ++)
            copyItems[i] = itemArray[i];
        // sort the array of item in order of descending ratio of value/weight
        // the ratio of value/weight define how dense that item is. The more dense, the better.
        Arrays.sort(copyItems);
        // Keep picking items from the sorted array utill sack is full
        while (index < totalItem) {
            if(totalWeight + copyItems[index].weight <= capacity){
                resultIndex[index] = copyItems[index].index;
                totalValue += copyItems[index].value;
                totalWeight += copyItems[index].weight;
            }
            index++;
        }
        timming = (System.nanoTime() - timming)/1000;
        // print result 
        System.out.println("Greedy solution (not necessarily optimal): " + totalValue + " " + totalWeight + "\t(" + timming + "us)");
        Arrays.sort(resultIndex);
        for (int ind : resultIndex)
            if (ind != 0)
                System.out.print(ind + " " );
        System.out.println();
    }
    
    /** Third algorithm - Dynamic programming */
    public void dynamicAlgorithm() {
        long timming = System.nanoTime();
        int[][] table = new int[totalItem + 1][capacity + 1];
        // construct the table
        for (int i = 1; i < totalItem + 1 ; i ++){
            table[i][0] = 0; // Base case
            for (int j = 1; j < capacity+1 ; j++) {
                table [0][j] = 0;  // Base case
                if (itemArray[i-1].weight <= j)  // able to pick item
                    table[i][j] = Math.max(itemArray[i-1].value + table[i-1][j - itemArray[i-1].weight] ,
                                           table[i-1][j]);
                else
                    table[i][j] = table[i-1][j];  // Not able to pick item
            }
        }
        
        int[] resultIndex = new int[totalItem];
        int rowIndex = totalItem; 
        int columnIndex = capacity;
        int totalValue = table[rowIndex][columnIndex];
        int totalWeight = 0;
        // trace back
        while (rowIndex != 0 && columnIndex != 0) {
            if (table[rowIndex][columnIndex] != table[rowIndex-1][columnIndex]) {
                resultIndex[rowIndex-1] = rowIndex;
                columnIndex -= itemArray[rowIndex-1].weight;
                totalWeight += itemArray[rowIndex-1].weight;
            }
            rowIndex -= 1;
        }
        timming = (System.nanoTime() - timming)/1000;
        // Print result
        System.out.println("Dynamic Programming solution: " + totalValue + " " + totalWeight + "\t(" + timming + "us)");
        for (int i : resultIndex) 
            if (i != 0)
                System.out.print(i + " " );
        System.out.println();
    }
    
    /** Fourth algorithm - Branch & Bound */
    public void BBAlgorithm(){
        long timming = System.nanoTime();
        long start = System.nanoTime();
        Node solution = null;
        // get the copy array of items 
        item[] copyItems = new item[totalItem];
        for (int i = 0; i < totalItem; i ++)
            copyItems[i] = itemArray[i];
        
        Arrays.sort(copyItems); // sort the array of item in order of descending ratio of value/weight
        
        Queue<Node> myQ = new PriorityQueue<Node>();
        // Constructing the tree
        Node root = new Node(0, 0, upperBound(copyItems, ""));
        int treeHeight = 0;
        myQ.add(root);
        // Keep building tree until tree's height = total number of items, or found a solution
        while (treeHeight < totalItem) {
            // pop the front node in the queue which has highest upper bound.
            solution = myQ.remove();
            treeHeight = solution.partialSol.length();
            // check if we get to the leaf of tree, means we found a solution
            if (treeHeight == totalItem)
                break;
            // build right child
            makeRightNode(solution, copyItems, treeHeight);
            myQ.add(solution.right);
            // not found solution yet, keep building left child if possible
            if(makeLeftNode(solution, copyItems, treeHeight))
                myQ.add(solution.left);
            // Limit runtime = 30sec
            if ((int)((System.nanoTime() - start)/1000000000) > 35)
                break;
        }
        
        timming = (System.nanoTime() - timming)/1000;
        // Trace back solution using the String partialSolution stored in the Node
        int[] resultIndex = new int[totalItem];
        int totalWeightSolution = 0;
        for (int i = 0; i < solution.partialSol.length(); i++)
            if (solution.partialSol.charAt(i) == '1') {
                resultIndex[i] = copyItems[i].index;
                totalWeightSolution += copyItems[i].weight;
            }
        Arrays.sort(resultIndex);
        // Print result
        System.out.println("Using Branch and Bound the best feasible solution found: " 
            + solution.totalV + " " + totalWeightSolution + "\t(" + timming + "us)");
        int line = 0;
        for (int i: resultIndex) {
            if (i != 0)
                System.out.print(i + " " );
        }
        System.out.println();
    }
    
    /** Helper method to build the left child of a Node, return false if making this child overfill the sack*/
    private boolean makeLeftNode(Node parent, item[] items, int itemIndex) {
        if (parent.totalW + items[itemIndex].weight <= capacity) {
            String partialSol = parent.partialSol + "1";
            parent.left = new Node(parent.totalV + items[itemIndex].value,
                             parent.totalW + items[itemIndex].weight, 
                             upperBound(items, partialSol));
            parent.left.partialSol = partialSol; 
            return true;
        }
        return false;
    }
    
    /** Helper method to build the right child of a Node */
    private void makeRightNode(Node parent, item[] items, int itemIndex) {
        String partialSol = parent.partialSol + "0";  
        parent.right = new Node(parent.totalV, parent.totalW, upperBound(items, partialSol));
        parent.right.partialSol = parent.partialSol + "0";
    }
    
    /** Helper method to calculate upperbound */
    private double upperBound(item[] items, String partialSolution){
        double upperBound = 0;
        int totalWeight = 0;
        int index = 0;
        // Sum up all possible whole-items
        for (index = 0; index < partialSolution.length(); index++)
            if (partialSolution.charAt(index) == '1') {
                upperBound += (double)(items[index].value);
                totalWeight += items[index].weight;
            }
        while (index < items.length && totalWeight + items[index].weight <= capacity) {
            upperBound += (double)(items[index].value);
            totalWeight += items[index].weight;
            index++;
        }
        // Adding the fractional item,then return it
        if (index < items.length)
            upperBound += (double)((capacity-totalWeight) * items[index].value / items[index].weight);
        return upperBound;
    }
    
    /** Inner class Node to construc the tree when doing Branch & Bound algorithm 
     *  child left means picking that item, child right mean not picking that item
     */
    public class Node implements Comparable {
        int totalV; int totalW; double upperBound; String partialSol = "";
        Node left; Node right;
        public Node (int value, int weight, double bound) {
            totalV = value; totalW = weight; upperBound = bound;
        }
        public int compareTo(Object other) {
            return (this.upperBound > ((Node)other).upperBound) ? -1 :
                   (this.upperBound < ((Node)other).upperBound) ? 1 : 0;
        }
    }
    
    public void runAllTest() throws IOException{
        
        System.out.println(" ...easy20.txt...");
        this.readFile("easy20.txt");
        this.fullEnumeration();
        this.greedyAlgorithm();
        this.dynamicAlgorithm();
        BBAlgorithm();
        
        System.out.println("\n ...easy50.txt...");
        this.readFile("easy50.txt");
        this.greedyAlgorithm();
        this.dynamicAlgorithm();
        BBAlgorithm();
        
        System.out.println("\n ...hard50.txt...");
        this.readFile("hard50.txt");
        this.greedyAlgorithm();
        this.dynamicAlgorithm();
        BBAlgorithm();
        
        System.out.println("\n ...easy200.txt...");
        this.readFile("easy200.txt");
        this.greedyAlgorithm();
        this.dynamicAlgorithm();
        BBAlgorithm();
        
        System.out.println("\n ...hard200.txt...");
        this.readFile("hard200.txt");
        this.greedyAlgorithm();
        this.dynamicAlgorithm();
        BBAlgorithm();
        
    }
}
