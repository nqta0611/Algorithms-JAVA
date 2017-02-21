import java.util.Stack;
import java.util.Scanner;
import java.io.*;
/**
 * EditDistance is a program to compute the optimal sequence alignment of two DNA strings.
 * 
 * @author Anh Nguyen 
 * @version 11/10/2015
 */
public class EditDistance
{
    /**
     * Read in the input file
     */
    public static void main(String[] args) throws IOException {
        //Pass 2 string in the input file to method MinEditedDistance
        if (args.length == 2) {
            Scanner scan = new Scanner(new FileReader(new File(args[0])));
            String x = scan.nextLine();
            String y = scan.nextLine();
            int c = Integer.parseInt(args[1]);
            MinEditedDistance(x, y, c);
        }
    }

    /**
     * Compute the minimum edited distance of 2 string
     */
    public static void MinEditedDistance(String x, String y, int toggle) throws IOException {
        int penaltyGap = 2;
        int penaltySub = 1;
        int penaltyMatch = 0;
        // Initiate the table
        int[][] table = new int[x.length() + 1][y.length() + 1];

        /*CONSTRUCT the table*/
        // The first row - base case when there is no string 2
        for (int i = 1; i <= x.length(); i++)
            table[i][0] = table[i - 1][0] + penaltyGap;
        // The first column - base case when there is no string 1
        for (int j = 1; j <= y.length(); j++)
            table[0][j] = table[0][j - 1] + penaltyGap;

        // Calculate the value from Top, Left, and Diagonal
        // then take the Min of them
        // F[i][j] = Min (F[i-1][j-1] + 0 or 1,
        //                F[i-1][j] + 2, 
        //                F[i][j-1] + 2)
        for (int i = 1; i <= x.length(); i++) {
            for (int j = 1; j <= y.length(); j++) {
                int penaltyDiagonal = (x.charAt(i-1) == y.charAt(j-1)) ? penaltyMatch : penaltySub;
                int fromDiagonalBox = table[i-1][j-1] + penaltyDiagonal;
                int fromLeftBox = table[i][j-1] + penaltyGap; 
                int fromTopBox = table[i-1][j] + penaltyGap;               
                table[i][j] = Math.min(Math.min(fromDiagonalBox, fromLeftBox), fromTopBox);
            }
        }

        /*TRACEBACK the solution*/
        Stack<String> solutionStack = new Stack<String>();
        //Trace back 2 strings from the end of table
        int indexX = x.length();
        int indexY = y.length();
        while( indexX > 0 && indexY > 0) {
            //The MinEditedDistance was from diagonal, case match
            if (x.charAt(indexX-1) == y.charAt(indexY-1))  {
                solutionStack.push(x.charAt(indexX-1) + " " + y.charAt(indexY-1) + " " + "0");
                indexY--;
                indexX--;
            }
            //The MinEditedDistance was from diagonal, case substitute
            else if (table[indexX][indexY] == table[indexX-1][indexY-1] + 1) {
                solutionStack.push(x.charAt(indexX-1) + " " + y.charAt(indexY-1) + " " + "1");
                indexX--;
                indexY--;
            }
            //The MinEditedDistance was from the above box, case gap
            else if (table[indexX][indexY] == table[indexX][indexY-1] + 2) {
                solutionStack.push("-" + " " + y.charAt(indexY-1) + " " + "2");
                indexY--;
            }
            //The MinEditedDistance was from the left box, case gap
            else if (table[indexX][indexY] == table[indexX-1][indexY] + 2) {
                solutionStack.push(x.charAt(indexX-1) + " " + "-" + " " + "2");
                indexX--;
            }
        }
        //Leftover from the first string, there will be all spaces in 2nd string
        while ( indexX > 0) {
            solutionStack.push(x.charAt(indexX-1) + " " + "-" + " " + "2");
            indexX--;
        }
        //Leftover from the 2nd string, there will be all spaces in 1st string
        while (indexY > 0) {
            solutionStack.push("-" + " " + y.charAt(indexY-1) + " " + "2");
            indexY--;
        }
        //Print out solution
        printSolution(solutionStack, table[x.length()][y.length()], toggle);
    }

    /**
     * Print method to print the result to Console,
     * also print the result to the fie output.txt
     */
    private static void printSolution(Stack<String> solutionStack, int minEdited, int toggle) throws IOException{
        PrintWriter out = new PrintWriter(new FileWriter(new File("output.txt")));
        System.out.println("Edit distance = " + minEdited);
        out.println("Edit distance = " + minEdited);
        out.flush();
        if (toggle == 1) {
            while (!solutionStack.isEmpty()) {
                String line = solutionStack.pop();
                System.out.println(line);
                out.println(line);
                out.flush();
            }
       }
    }
}
