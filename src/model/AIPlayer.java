/*
  Author: <Christopher O'Driscoll>
  Id: <al0038>
  Study program: <Systemutvecklare TGSYA20h>
*/
package model;

import controller.Controller;

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
        //TODO implement displaying of depth reached; number of nodes examined;
    }

    private int[] findBestMoves(GameBoard currentBoard) {
        GameBoard board = GameBoard.deepCopy(currentBoard);
        GameTree.TreeNode root = GameTree.buildTree(board, 3);
        int[] bestMove = GameTree.findBestMove(root, 2).getLastMove();
        System.out.println("Best move is " + (char) ('A' + bestMove[0]) + ", " + bestMove[1]);
        return bestMove;
    }
}
