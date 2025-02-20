/* *****************************************************************************
 *  Name:              danshenmiao
 *  Coursera User ID:  123456
 *  Last modified:     11/20/2024
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] result;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n < 0 || trials <= 0)
            throw new IllegalArgumentException();

        result = new double[trials];

        for (int i = 0; i < trials; ++i) {
            Percolation p = new Percolation(n);
            while (!p.percolates()) {
                int x1 = StdRandom.uniformInt(1, n + 1);
                int x2 = StdRandom.uniformInt(1, n + 1);
                p.open(x1, x2);
            }
            // StdOut.println(p.numberOfOpenSites());
            result[i] = p.numberOfOpenSites() / Math.pow(n, 2);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(result);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(result);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - stddev();
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + stddev();
    }

    // test client (see below)
    public static void main(String[] args) {
        if (args.length < 2) return;
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats ps = new PercolationStats(n, trials);
        StdOut.println("Mean: " + ps.mean());
        StdOut.println("Stddev: " + ps.stddev());
        StdOut.println("95% confidence interval: " + '[' + ps.confidenceLo() +
                               ',' + ps.confidenceHi() + ']');
    }

}
