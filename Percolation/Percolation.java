/* *****************************************************************************
 *  Name:              danshenmiao
 *  Coursera User ID:  123456
 *  Last modified:     11/20/2024
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] grid;
    private WeightedQuickUnionUF uf;
    private int openCount;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n < 0) {
            throw new IllegalArgumentException();
        }
        openCount = 0;
        // init member
        grid = new boolean[n][n];
        // 创建两个虚拟节点
        uf = new WeightedQuickUnionUF(n * n + 2);
        // 将第一个虚拟节点和第一行链接
        for (int i = 0; i < n; ++i) {
            uf.union(n * n, i);
        }
        int lastRow = n * (n - 1);
        // 第二个虚拟节点和最后一行链接
        for (int i = 0; i < n; ++i) {
            uf.union(n * n + 1, lastRow + i);
        }

        // init grid
        for (int i = 0; i < grid.length; ++i) {
            for (int j = 0; j < grid.length; ++j) {
                grid[i][j] = false;
            }
        }
    }


    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (outOfRange(row, col, 1, grid.length)) {
            throw new IllegalArgumentException();
        }
        row = row - 1;
        col = col - 1;
        if (grid[row][col]) return;

        int currPos = row * grid.length + col;
        if (row > 0 && grid[row - 1][col])
            uf.union(currPos, currPos - grid.length);    // union up
        if (row < grid.length - 1 && grid[row + 1][col])
            uf.union(currPos, currPos + grid.length);   // union down
        if (col > 0 && grid[row][col - 1])
            uf.union(currPos, currPos - 1);    // union left
        if (col < grid.length - 1 && grid[row][col + 1])
            uf.union(currPos, currPos + 1);   // union right

        grid[row][col] = true;
        openCount++;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (outOfRange(row, col, 1, grid.length)) {
            throw new IllegalArgumentException();
        }
        row = row - 1;
        col = col - 1;
        return grid[row][col];
    }

    // is the site (row, col) full?
    // 当当前点与第一行某一点联通时当前点是满的
    public boolean isFull(int row, int col) {
        if (outOfRange(row, col, 1, grid.length)) {
            throw new IllegalArgumentException();
        }
        row = row - 1;
        col = col - 1;
        if (!grid[row][col]) return false;
        int n = grid.length;
        if (uf.find(n * n) == uf.find(row * n + col)) {
            return true;
        }
        return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openCount;
    }

    // does the system percolate?
    // 当第一行某一点与最后一行某一点联通时渗透
    public boolean percolates() {
        int n = grid.length;
        if (uf.find(n * n) == uf.find(n * n + 1))
            return true;
        return false;
    }

    // test client (optional)
    public static void main(String[] args) {
    }

    private boolean outOfRange(int row, int col, int min, int max) {
        return !(min <= row && row <= max && min <= col && col <= max);
    }

}
