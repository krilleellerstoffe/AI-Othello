/*
  Author: <Christopher O'Driscoll>
  Id: <al0038>
  Study program: <Systemutvecklare TGSYA20h>
*/
package model;

import controller.Controller;

import java.util.Random;

public class AIPlayer {

    private Controller controller;
    private GameManager model;


    public AIPlayer(Controller controller, GameManager model) {

        this.controller = controller;
        this.model = model;
    }

    public void nextBestMove() {
        Controller controllerCopy = new Controller(controller);
        int[] nextBestMove = findBestMoves(controllerCopy);
        //controller.pressButtonAI(x, y);
    }

    private int[] findBestMoves(Controller controllerCopy) {
        GameBoard board = controllerCopy.getModel().getBoard();
        TreeNode root = GameTree.buildTree(board, 3);
        int[] bestMove = GameTree.findBestMove(root, 2).getLastMove();
        System.out.println("Best move is " + bestMove[0] + ", " + bestMove[1]);
        return bestMove;
    }
}
