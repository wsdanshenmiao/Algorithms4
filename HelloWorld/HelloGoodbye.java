/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

public class HelloGoodbye {
    public static void main(String[] args) {
        if (args.length > 1) {
            StdOut.println("Hello " + args[0] + " and " + args[1] + ".");
            StdOut.println("Goodbye " + args[1] + " and " + args[0] + ".");
        }
    }
}
