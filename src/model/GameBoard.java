/*
  Author: <Christopher O'Driscoll>
  Id: <al0038>
  Study program: <Artificial Intelligence DA272A>
*/
package model;

public class GameBoard {

    private GameManager model;

    private int size;  //height of game board
    private Square[][] squares; //an array of squares on the board

    private boolean playerTurn;

    public GameBoard(GameManager model, int size) {

        this.model = model;
        this.size = size;
        squares = new Square[size][size];
        playerTurn = true;
        setupStartingBoard();
    }
    //constructor for en empty board
    public GameBoard() {
        squares = new Square[0][];
        size = 0;
    }
    //create a copy of a given board
    public static GameBoard deepCopy(GameBoard currentBoard) {
        //first create an empty board
        GameBoard newBoard = new GameBoard();
        //now get the squares from the board we want to copy
        Square[][] originalSquares = currentBoard.getSquares();
        //initialise the array
        int size = currentBoard.getSize();
        Square[][] copiedSquares = new Square[size][size];
        // Perform a deep copy of each Square object
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Square originalSquare = originalSquares[i][j];
                copiedSquares[i][j] = new Square(originalSquare);
            }
        }
        boolean playerTurn = currentBoard.isPlayerTurn();
        //now add variables to new board
        newBoard.setSquares(copiedSquares);
        newBoard.setSize(size);
        newBoard.setPlayerTurn(playerTurn);
        return newBoard;
    }
    //sets all squares as empty, then places starting discs and updates the valid moves
    private void setupStartingBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                setSquare(i,j, SquareState.Empty);
            }
        }
        setSquare((size/2)-1, (size/2)-1, SquareState.Black);
        setSquare(size/2, (size/2)-1, SquareState.White);
        setSquare((size/2)-1, size/2, SquareState.White);
        setSquare(size/2, size/2, SquareState.Black);
        updateOpenSquares(SquareState.White);
    }
    //returns the grid of squares
    public Square[][] getSquares() {
        return squares;
    }
    //returns square at x, y
    public Square getSquare(int x,int y) {
        return squares[x][y];
    }
    //updates square at x, y
    public void setSquare(int x, int y, SquareState state) {
        squares[x][y] = new Square(state);
    }
    //check how many valid moves are available
    public int getValidMoves() {
        int validMoves = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (squares[i][j].getState() == SquareState.Open) {
                    validMoves++;
                }
            }
        }
        return validMoves;
    }
    //get number of white discs on board
    public int getWhiteScore() {
        int whiteScore = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (squares[i][j].getState() == SquareState.White) {
                    whiteScore++;
                }
            }
        }
        return whiteScore;
    }
    //get number of black discs on board
    public int getBlackScore() {
        int blackScore = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (squares[i][j].getState() == SquareState.Black) {
                    blackScore++;
                }
            }
        }
        return blackScore;
    }
    // check for valid moves
    public int updateOpenSquares(SquareState stateToLookFor) {
        int validMoves = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                squares[i][j].setFlipped(false);
                //first reset all current valid moves (for opposite player)
                if(squares[i][j].getState() == SquareState.Open) {
                    squares[i][j].setState(SquareState.Empty);
                }
                //now check that square is not occupied
                if(squares[i][j].getState() != SquareState.Empty) {
                    continue;
                }
                //split to recursive method to check if valid move possible
                if (validMove(i, j, stateToLookFor, false)) {
                    squares[i][j].setState(SquareState.Open);
                    validMoves++;
                }
            }
        }
        return validMoves;
    }
    //returns true if valid move possible at square x, y
    public boolean validMove(int x, int y, SquareState oppositeColour, boolean flip) {
        boolean validMove = false;
        //iterate through neighbours
        for (int i = x-1; i <= x+1; i++) {
            for (int j = y-1; j <= y+1; j++) {
                //skip invalid squares
                if (!inBounds(i, j)) {
                    continue;
                }
                //skip own square
                if(i == x && j == y) {
                    continue;
                }
                //skip if neighbour not opposite colour
                if(squares[i][j].getState() != oppositeColour) {
                    continue;
                }
                //found possible valid move, expand in same direction
                int xDirection = (i-x);
                int yDirection = (j-y);
                //check still in bounds of the game board
                while(inBounds(i+xDirection, j+yDirection)) {
                    //while still opposite colour, continue to expand search
                    if(squares[i+xDirection][j+yDirection].getState() == SquareState.Open) {
                        break;
                    }
                    else if (squares[i+xDirection][j+yDirection].getState() == SquareState.Empty) {
                        break;
                    }
                    //if same colour found in direction after that, then move is valid
                    else if(squares[i+xDirection][j+yDirection].getOppositeState() == oppositeColour) {
                        //if flipping to be done, do it now
                        if(flip) {
                            if (!squares[i][j].isFlipped()) {
                                flipSquares(i, j, xDirection, yDirection);
                            }
                        }
                        //update valid condition
                        validMove = true;
                    }
                    //update directions
                    xDirection = expandDirection(xDirection);
                    yDirection = expandDirection(yDirection);

                }
            }
        }
        return validMove;
    }
    //check square exists on board
    private boolean inBounds(int x, int y) {
        if (x < 0 || x >= size) {
            return false;
        }
        if (y < 0 || y >= size) {
            return false;
        }
        return true;
    }
    //Flip squares back to disc at x, y
    public void flipSquares(int x, int y, int xDirection, int yDirection) {
        //turn around and flip colours
        do {
            //create local variables to prevent loop exiting prematurely
            int newXDirection = reverseDirection(xDirection);
            int newYDirection = reverseDirection(yDirection);
            //flip square to desired colour
            squares[x + newXDirection][y + newYDirection].flipColour();
            //now update variables for loop condition
            xDirection = newXDirection;
            yDirection = newYDirection;
        } while (!(xDirection == 0 && yDirection == 0));
    }
    //return next square's coordinates for a given direction
    private int expandDirection(int direction) {
        if (direction < 0) {
            return direction - 1;
        }
        else if (direction > 0) {
            return direction + 1;
        }
        else {
            return direction;
        }
    }
    //return previous square's coordinates for a given direction
    private int reverseDirection(int direction) {
        if (direction < 0) {
            return direction+1;
        }
        else if (direction > 0) {
            return direction-1;
        }
        else {
            return direction;
        }
    }
    public void setSquares(Square[][] squares) {
        this.squares = squares;
    }
    public void setSize(int size) {
        this.size = size;
    }
    public int getSize() {
        return size;
    }

    public boolean isPlayerTurn() {
        return playerTurn;
    }

    public void setPlayerTurn(boolean playerTurn) {
        this.playerTurn = playerTurn;
    }
}
