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
        int[] nextBestMove = findBestMoves(controller.getModel().getBoard());
        //controller.pressButtonAI(x, y);
    }

    private int[] findBestMoves(GameBoard currentBoard) {
        GameBoard board = GameBoard.deepCopy(currentBoard);
        TreeNode root = GameTree.buildTree(board, 3);
        int[] bestMove = GameTree.findBestMove(root, 2).getLastMove();
        System.out.println("Best move is " + bestMove[0] + ", " + bestMove[1]);
        return bestMove;
    }
}
