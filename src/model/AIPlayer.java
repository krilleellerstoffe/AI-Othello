/*
  Author: <Christopher O'Driscoll>
  Id: <al0038>
  Study program: <Systemutvecklare TGSYA20h>
*/
package model;

import controller.Controller;

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
        //controller.pressButtonAI(x, y);
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
            try {
                bestMove = GameTree.findBestMove(root, depth).getLastMove();
            } catch (Exception e) {
                System.out.println("Game tree not complete, time limit reached");
            }
            System.out.println("Best move at depth " + depth + " is " + (char) ('A' + bestMove[0]) + ", " + bestMove[1]);
            System.out.println("Time elapsed: " + ((double)System.currentTimeMillis() - startTime)/1000 + " seconds");
            //update conditions
            currentTime = System.currentTimeMillis();
            depth++;
        }
        return bestMove;
    }
}
