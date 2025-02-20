/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    // 优先队列中节点储存的信息
    private class BoardNode implements Comparable<BoardNode> {
        private Board board;
        private int moves;
        private int priority;
        // 沿途构造链表，方便回溯,同时优化性能
        private BoardNode pre;

        public BoardNode(Board board, int moves, BoardNode pre) {
            this.board = board;
            this.moves = moves;
            this.pre = pre;
            this.priority = moves + board.manhattan();
        }


        public int compareTo(BoardNode other) {
            return this.priority - other.priority;
        }
    }

    private BoardNode goalNode;
    private boolean isSolvable;
    private int moves;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException("initial is null");
        }

        MinPQ<BoardNode> minPQ = new MinPQ<>();
        MinPQ<BoardNode> twinMinPQ = new MinPQ<>();

        minPQ.insert(new BoardNode(initial, 0, null));
        // 创建一个孪生棋盘，防止出现无解的情况
        twinMinPQ.insert(new BoardNode(initial.twin(), 0, null));

        BoardNode min = minPQ.delMin(), twinMin = twinMinPQ.delMin();
        for (; !min.board.isGoal() && !twinMin.board.isGoal();
             min = minPQ.delMin(), twinMin = twinMinPQ.delMin()) {
            // 将所有可能性插入优先队列
            for (Board board : min.board.neighbors()) {
                // 有可能遇到回到前一次位置的情况
                if (min.pre == null || !board.equals(min.pre.board)) {
                    minPQ.insert(new BoardNode(board, min.moves + 1, min));
                }
            }
            // 处理孪生棋盘
            for (Board board : twinMin.board.neighbors()) {
                if (twinMin.pre == null || !board.equals(twinMin.pre.board)) {
                    twinMinPQ.insert(new BoardNode(board, twinMin.moves + 1, twinMin));
                }
            }
        }
        if (min.board.isGoal()) {
            isSolvable = true;
            moves = min.moves;
            goalNode = min;
        }
        else {
            isSolvable = false;
            moves = -1;
            goalNode = twinMin;
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return isSolvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable) return null;

        // 由于算法最后获取的是目标，因此使用堆栈
        Stack<Board> ret = new Stack<>();
        BoardNode boardNode = goalNode;
        for (; boardNode != null; boardNode = boardNode.pre) {
            ret.push(boardNode.board);
        }
        return ret;
    }

    // test client (see below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

}
