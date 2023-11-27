package model;


import java.util.ArrayList;
import java.util.List;

public class GameTree {

    private boolean maxDepthReached;

    public TreeNode buildTree(GameBoard initialBoard, int depth, long maxTime) {
        TreeNode root = new TreeNode(initialBoard);
        maxDepthReached = false;
        populateTree(root, depth, true, maxTime);
        return root;
    }

    private void populateTree(TreeNode node, int depth, boolean maximizingPlayer, long maxTime) {
        if (depth == 0) {
            maxDepthReached = true;
            return;
        }
        if (gameIsOver(node)) {
            return;
        }
        if(System.currentTimeMillis() > maxTime) {
            return;
        }
        GameBoard currentBoard = node.getBoard();
        for (int i = 0; i < currentBoard.getSquares().length; i++) {
            for (int j = 0; j < currentBoard.getSquares().length; j++) {
                //if the current square is playable
                if (currentBoard.getSquare(i, j).getState() == SquareState.Open) {
                    //sake a copy of the current board
                    GameBoard newBoard = GameBoard.deepCopy(currentBoard);
                    //simulate placing a disk
                    placePiece(newBoard, i, j, maximizingPlayer);
                    //create a node using the simulated board
                    TreeNode childNode = new TreeNode(newBoard);
                    //record the move that created the board
                    childNode.setLastMove(new int[]{i,j});
                    //add this simulated board as a child to the current node
                    node.addChild(childNode);
                    //
                    populateTree(childNode, depth - 1, !maximizingPlayer, maxTime);
                }
            }
        }
    }

    private static void placePiece(GameBoard newBoard, int i, int j, boolean maximizingPlayer) {

        newBoard.setSquare(i, j, (maximizingPlayer? SquareState.White : SquareState.Black));
        //search for squares to flip
        newBoard.validMove(i, j, (maximizingPlayer? SquareState.Black : SquareState.White), true);
        //update open squares for next player
        newBoard.updateOpenSquares((maximizingPlayer? SquareState.White : SquareState.Black));
    }

    public static TreeNode findBestMove(TreeNode rootNode, int depth) {
        int bestValue = Integer.MIN_VALUE;
        TreeNode bestMove = null;
        for (TreeNode child : rootNode.getChildren()) {
            int value = alphaBeta(child, depth - 1, Integer.MIN_VALUE, Integer.MAX_VALUE, true);
            if (value > bestValue) {
                bestValue = value;
                bestMove = child;
            }
        }
        return bestMove;
    }

    public static int alphaBeta(TreeNode node, int depth, int alpha, int beta, boolean maximizingPlayer) {
        if (depth == 0 || gameIsOver(node)) {
            return evaluate(node); // Evaluation function for the leaf node
        }

        if (maximizingPlayer) {
            int value = Integer.MIN_VALUE;
            for (TreeNode child : node.getChildren()) {
                value = Math.max(value, alphaBeta(child, depth - 1, alpha, beta, false));
                alpha = Math.max(alpha, value);
                if (alpha >= beta) {
                    break; // Beta cutoff
                }
            }
            return alpha;
        } else {
            int value = Integer.MAX_VALUE;
            for (TreeNode child : node.getChildren()) {
                value = Math.min(value, alphaBeta(child, depth - 1, alpha, beta, true));
                beta = Math.min(beta, value);
                if (alpha >= beta) {
                    break; // Alpha cutoff
                }
            }
            return beta;
        }
    }

    //return the score we want to maximise (AI player is white)
    public static int evaluate(TreeNode node) {
        return node.getBoard().getWhiteScore();
    }

    //game ends if no more moves possible
    public static boolean gameIsOver(TreeNode node) {
        return node.getBoard().getValidMoves() == 0;
    }

    public boolean isMaxDepthReached() {
        return maxDepthReached;
    }

    public static class TreeNode {
        private GameBoard board;
        private List<TreeNode> children;
        private int[] lastMove = new int[2];

        public TreeNode(GameBoard board) {
            this.board = board;
            this.children = new ArrayList<>();
        }

        public GameBoard getBoard() {
            return board;
        }

        public List<TreeNode> getChildren() {
            return children;
        }

        public void addChild(TreeNode childNode) {
            children.add(childNode);
        }

        public int[] getLastMove() {
            return lastMove;
        }

        public void setLastMove(int[] lastMove) {
            this.lastMove = lastMove;
        }
    }
}
