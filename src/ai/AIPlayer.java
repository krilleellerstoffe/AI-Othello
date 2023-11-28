/*
  Author: <Christopher O'Driscoll>
  Id: <al0038>
  Study program: <Artificial Intelligence DA272A>
*/
package ai;

import controller.Controller;
import model.GameBoard;
import model.SquareState;

import java.util.ArrayList;
import java.util.Random;


public class AIPlayer {

    private Controller controller;

    public AIPlayer(Controller controller) {

        this.controller = controller;
    }

    public void nextBestMove() {
        int[] nextBestMove = findBestMoves(controller.getModel().getBoard());
        controller.pressButtonAI(nextBestMove[0], nextBestMove[1]);
        //TODO implement displaying of depth reached; number of nodes examined;
    }
    public void nextRandomMove() {
        int[] randomMove = findRandomMove(controller.getModel().getBoard());
        controller.pressButtonAI(randomMove[0], randomMove[1]);
    }

    private int[] findRandomMove(GameBoard board) {
        ArrayList<int[]> validMoves = new ArrayList<>();
        for (int i = 0; i < board.getSquares().length; i++) {
            for (int j = 0; j < board.getSquares().length; j++) {
                //if the current square is playable
                if (board.getSquare(i, j).getState() == SquareState.Open) {
                    validMoves.add(new int[]{i, j});
                }
            }
        }
        Random random = new Random();
        int randomIndex = random.nextInt(validMoves.size());
        return validMoves.get(randomIndex);
    }

    private int[] findBestMoves(GameBoard currentBoard) {
        GameBoard copyOfCurrentBoard = GameBoard.deepCopy(currentBoard);
        int[] bestMove = new int[2];
        long startTime = System.currentTimeMillis();
        long currentTime = startTime;
        long maxTime = startTime + 5000;
        int depth = 1;

        GameTree tree = new GameTree();
        while(currentTime < maxTime) {
            GameTree.TreeNode root = tree.buildTree(copyOfCurrentBoard, depth, maxTime);
            if (!tree.isMaxDepthReached()) {
                System.out.println("Tree too small to reach new depth. Breaking search.");
                break;
            }
            int[] newBestMove = new int[2];
            try {
                newBestMove = GameTree.findBestMove(root, depth).getLastMove();
            } catch (Exception e) {
                System.out.println("Game tree not complete, time limit reached");
            }
            double timeElapsed = ((double)System.currentTimeMillis() - startTime)/1000;
            System.out.println("Time elapsed: " + timeElapsed + " seconds");
            //only add if tree completed TODO add boolean to tree if complete
            if (timeElapsed < 5.0) {
                bestMove = newBestMove;
                System.out.println("Depth " + depth + " Tree complete");
                System.out.println("Best move is " + (char) ('A' + newBestMove[0]) + ", " + newBestMove[1]);
            }
            else {
                System.out.println("Depth " + depth + " Tree incomplete, discarding results");
            }
            //update conditions TODO break loop if all possible trees made (deeper trees not possible)
            currentTime = System.currentTimeMillis();
            depth++;
        }
        //Discount latest result as it will likely be an incomplete
        return bestMove;
    }
}
