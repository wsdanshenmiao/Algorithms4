// Hamming and Manhattan distances.  To measure how close a board is to the goal board, we define two notions of distance.
// The Hamming distance betweeen a board and the goal board is the number of tiles in the wrong position.
// The Manhattan distance between a board and the goal board is the sum of the Manhattan distances
// (sum of the vertical and horizontal distance) from the tiles to their goal positions.

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class Board {
    private int[][] board;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        this.board = new int[tiles.length][tiles.length];
        for (int i = 0; i < dimension(); ++i) {
            for (int j = 0; j < dimension(); ++j) {
                board[i][j] = tiles[i][j];
            }
        }
    }

    // string representation of this board
    public String toString() {
        String ret = Integer.toString(board.length).concat("\n");
        for (int[] row : board) {
            ret = ret.concat(" ");
            for (int tile : row) {
                ret = ret.concat(Integer.toString(tile).concat(" "));
            }
            ret = ret.concat("\n");
        }
        return ret;
    }

    // board dimension n
    public int dimension() {
        return board.length;
    }

    // number of tiles out of place
    public int hamming() {
        int ret = 0;
        int n = dimension();
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                if (board[i][j] != (i * n + j + 1) % (n * n) && board[i][j] != 0) {
                    ++ret;
                }
            }
        }
        return ret;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int ret = 0;
        int goalRow, goalCol;
        for (int i = 0; i < dimension(); ++i) {
            for (int j = 0; j < dimension(); ++j) {
                int tile = board[i][j];
                if (tile != 0) {
                    // 计算当前数字应该在的位置
                    goalRow = (tile - 1) / dimension();
                    goalCol = (tile - 1) % dimension();
                    int row = Math.abs(goalRow - i);
                    int col = Math.abs(goalCol - j);
                    ret += row + col;
                }
            }
        }
        return ret;
    }

    // is this board the goal board?
    public boolean isGoal() {
        boolean ret = true;
        for (int i = 0; i < dimension(); ++i) {
            for (int j = 0; j < dimension(); ++j) {
                if (board[i][j] != (i * dimension() + j + 1) % (dimension() * dimension())) {
                    ret = false;
                    break;
                }
            }
        }
        return ret;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null || this.getClass() != y.getClass()) return false;
        if (this == y) return true;
        Board other = (Board) y;
        return Arrays.deepEquals(this.board, other.board);
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Queue<Board> ret = new Queue<>();
        int n = dimension();
        // 获取白块的位置
        int blankPos = findBlankSquare();

        int[] possiblePos = getPossiblePos(blankPos);
        int[][] tiles = new int[n][n];
        System.arraycopy(board, 0, tiles, 0, n);
        for (int pos : possiblePos) {
            Board nearBoard = new Board(tiles);
            nearBoard.move(pos, blankPos);
            ret.enqueue(nearBoard);
        }

        return ret;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        Board ret = new Board(board);
        // 最小的棋盘为2x2，因此无需判断
        if (board[0][0] != 0 && board[0][1] != 0) {
            ret.move(0, 1);
        }
        else {
            ret.move(2, 3);
        }
        return ret;
    }

    // 查找空白块所在的位置
    private int findBlankSquare() {
        int ret = 0;
        for (int i = 0; i < dimension(); ++i) {
            for (int j = 0; j < dimension(); ++j) {
                if (board[i][j] == 0) {
                    ret = i * dimension() + j;
                    break;
                }
            }
        }
        return ret;
    }

    // 根据坐标获取可能的位置，row[0, n),col[0,n)
    private int[] getPossiblePos(int blankPos) {
        int row = blankPos / dimension();
        int col = blankPos % dimension();
        int[] pos = new int[4];
        int count = 0;
        if ((row - 1) >= 0) {
            pos[count++] = (row - 1) * dimension() + col;
        }
        if ((row + 1) < dimension()) {
            pos[count++] = (row + 1) * dimension() + col;
        }
        if ((col - 1) >= 0) {
            pos[count++] = (col - 1) + row * dimension();
        }
        if ((col + 1) < dimension()) {
            pos[count++] = (col + 1) + row * dimension();
        }
        int[] ret = new int[count];
        for (int i = 0; i < count; ++i) {
            // 标准化坐标
            ret[i] = pos[i];
        }
        return ret;
    }

    // 将某个板移到空格处
    private void move(int pos, int blankPos) {
        int n = dimension();
        int tmp = board[pos / n][pos % n];
        board[pos / n][pos % n] = board[blankPos / n][blankPos % n];
        board[blankPos / n][blankPos % n] = tmp;
    }


    // unit testing (not graded)
    public static void main(String[] args) {
        int n = 3;
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                tiles[i][j] = n * n - (i * n + j + 1);
            }
        }
        // Board board0 = new Board(tiles);
        // StdOut.print(board0);
        // StdOut.println(board0.dimension());
        // StdOut.println(board0.hamming());
        // StdOut.println(board0.manhattan());
        // board0.move(4, 8);
        // for (Board board : board0.neighbors()) {
        //     StdOut.print(board);
        // }
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                tiles[i][j] = (i * n + j + 1) % (n * n);
            }
        }
        Board board1 = new Board(tiles);
        StdOut.println(board1.isGoal());
    }
}
