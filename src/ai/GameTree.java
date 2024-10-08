/*
  Author: <Christopher O'Driscoll>
  Id: <al0038>
  Study program: <Artificial Intelligence DA272A>
*/
package ai;


import model.GameBoard;
import model.SquareState;

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
                    placeDisc(newBoard, i, j, maximizingPlayer);
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

    private static void placeDisc(GameBoard newBoard, int i, int j, boolean maximizingPlayer) {
        //Set colour of disc to place according to whose turn it is
        SquareState maximisingColour = (newBoard.isBlacksTurn()? SquareState.Black : SquareState.White);
        SquareState minimisingColour = (newBoard.isBlacksTurn()? SquareState.White : SquareState.Black);
        //place disc on selected square
        newBoard.setSquare(i, j, (maximizingPlayer? maximisingColour : minimisingColour));
        //search for squares to flip
        newBoard.validMove(i, j, (maximizingPlayer? minimisingColour : maximisingColour), true);
        //update open squares for next player
        newBoard.updateOpenSquares((maximizingPlayer? maximisingColour : minimisingColour));
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
        //Get score at leaf node
        if (depth == 0 || gameIsOver(node)) {
            return evaluate(node);
        }
        if (maximizingPlayer) {
            //initialise new alpha value to max
            int newAlpha = Integer.MIN_VALUE;
            //iterate through children
            for (TreeNode child : node.getChildren()) {
                //get minimum alpha value from child's children (recursive)
                newAlpha = Math.max(newAlpha, alphaBeta(child, depth - 1, alpha, beta, false));
                //replace with new value if bigger
                alpha = Math.max(alpha, newAlpha);
                if (alpha >= beta) {
                    break;
                }
            }
            return alpha;
        } else {
            //initialise new beta value to max
            int newBeta = Integer.MAX_VALUE;
            //iterate through children
            for (TreeNode child : node.getChildren()) {
                //get minimum beta value from child's children (recursive)
                newBeta = Math.min(newBeta, alphaBeta(child, depth - 1, alpha, beta, true));
                //replace with new value if bigger
                beta = Math.min(beta, newBeta);
                if (alpha >= beta) {
                    break;
                }
            }
            return beta;
        }
    }

    //return the score we want to maximise
    public static int evaluate(TreeNode node) {
        if(node.getBoard().isBlacksTurn()) {
            return node.getBoard().getBlackScore();
        }
        else {
            return node.getBoard().getWhiteScore();
        }
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
