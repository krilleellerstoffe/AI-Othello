/*
  Author: <Christopher O'Driscoll>
  Id: <al0038>
  Study program: <Systemutvecklare TGSYA20h>
*/
package model;

import controller.Controller;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.jcabi.aspects.Timeable;

public class AIPlayer {

    private Controller controller;

    private volatile boolean timeUp = false;

    private ExecutorService executor = Executors.newSingleThreadExecutor();

    public AIPlayer(Controller controller) {

        this.controller = controller;
    }

    public void nextBestMove() {
        int[] nextBestMove = findBestMoves(controller.getModel().getBoard());
        controller.pressButtonAI(nextBestMove[0], nextBestMove[1]);
        //TODO implement displaying of depth reached; number of nodes examined;
    }

    @Timeable(limit = 5, unit = TimeUnit.SECONDS)
    private int[] findBestMoves(GameBoard currentBoard) {
        GameBoard copyOfCurrentBoard = GameBoard.deepCopy(currentBoard);
        int[] bestMove = new int[2];
        long startTime = System.currentTimeMillis();
        long currentTime = startTime;
        long maxTime = startTime + 5000;
        int depth = 1;

        while(currentTime < maxTime) {
            GameTree.TreeNode root = GameTree.buildTree(copyOfCurrentBoard, depth, maxTime);
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
