package model;


public class GameTree {

    public static TreeNode buildTree(GameBoard initialBoard, int depth) {
        TreeNode root = new TreeNode(initialBoard);
        populateTree(root, depth, true);
        return root;
    }
    public static TreeNode findBestMove(TreeNode rootNode, int depth) {
        int bestValue = Integer.MIN_VALUE;
        TreeNode bestMove = null;

        for (TreeNode child : rootNode.getChildren()) {
            int value = alphaBeta(child, depth - 1, Integer.MIN_VALUE, Integer.MAX_VALUE, false);
            if (value > bestValue) {
                bestValue = value;
                bestMove = child;
            }
        }

        return bestMove;
    }
    private static void populateTree(TreeNode node, int depth, boolean maximizingPlayer) {
        if (depth == 0 || gameIsOver(node)) {
            System.out.println("Max depth reached");
            return;
        }
        GameBoard currentBoard = node.getBoard();
        for (int i = 0; i < currentBoard.getSquares().length; i++) {
            for (int j = 0; j < currentBoard.getSquares().length; j++) {
                if (currentBoard.getSquares()[i][j].getState() == SquareState.Open) {
                    GameBoard newBoard = new GameBoard(currentBoard); // Make a copy of the current board
                    placePiece(newBoard, i, j, maximizingPlayer);
                    System.out.println("open square, making new board");
                    TreeNode childNode = new TreeNode(newBoard);
                    childNode.setLastMove(new int[]{i,j});
                    node.addChild(childNode);

                    populateTree(childNode, depth - 1, !maximizingPlayer); // Recursively populate the tree
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

    public static int alphaBeta(TreeNode node, int depth, int alpha, int beta, boolean maximizingPlayer) {
        if (depth == 0 || gameIsOver(node)) {
            return evaluate(node); // Evaluation function for the leaf node
        }

        if (maximizingPlayer) {
            int value = Integer.MIN_VALUE;
            for (TreeNode child : node.getChildren()) {
                value = Math.max(value, alphaBeta(child, depth - 1, alpha, beta, false));
                alpha = Math.max(alpha, value);
                if (beta <= alpha) {
                    break; // Beta cutoff
                }
            }
            return value;
        } else {
            int value = Integer.MAX_VALUE;
            for (TreeNode child : node.getChildren()) {
                value = Math.min(value, alphaBeta(child, depth - 1, alpha, beta, true));
                beta = Math.min(beta, value);
                if (beta <= alpha) {
                    break; // Alpha cutoff
                }
            }
            return value;
        }
    }

    public static int evaluate(TreeNode node) {
        //assume white is the score we want to maximise
        return node.getBoard().getWhiteScore();
    }

    public static boolean gameIsOver(TreeNode node) {
        //game over if no more moves possible
        System.out.println("Valid moves for this node = " + node.getBoard().getValidMoves());
        return node.getBoard().getValidMoves() == 0;
    }
}
