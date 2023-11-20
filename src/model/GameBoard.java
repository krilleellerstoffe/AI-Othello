/*
  Author: <Christopher O'Driscoll>
  Id: <al0038>
  Study program: <Systemutvecklare TGSYA20h>
*/
package model;

public class GameBoard {

    private GameManager model;

    private int size;  //height of game board

    private Square[][] squares; //an array of squares on the board



    public GameBoard(GameManager model, int size) {

        this.model = model;
        this.size = size;
        squares = new Square[size][size];
        setupStartingBoard();
    }

    public GameBoard(GameBoard board) {
        this.model = board.model;
        this.size = board.size;
        this.squares = board.squares;
    }

    public GameBoard() {

        squares = new Square[0][];
        size = 0;
    }


    public static GameBoard deepCopy(GameBoard currentBoard) {
        GameBoard newBoard = new GameBoard();
        Square[][] originalSquares = currentBoard.getSquares();
        int size = currentBoard.getSize();
        Square[][] copiedSquares = new Square[size][size];

        // Perform a deep copy of each Square object
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Square originalSquare = originalSquares[i][j];
                // Assuming Square has a copy constructor or a method to create a deep copy
                copiedSquares[i][j] = new Square(originalSquare); // Create a new Square with copied state
            }
        }

        newBoard.setSquares(copiedSquares);
        newBoard.setSize(size);
        return newBoard;
    }


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

    public Square[][] getSquares() {
        return squares;
    }
    //returns square at x, y
    public Square getSquare(int x,int y) {
        return squares[x][y];
    }

    public void setSquare(int x, int y, SquareState state) {
        squares[x][y] = new Square(state);
    }

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
    //returns true if valid move possible
    public boolean validMove(int i, int j, SquareState oppositeColour, boolean flip) {
        boolean validMove = false;
        //iterate through neighbours
        for (int k = i-1; k <= i+1; k++) {
            for (int l = j-1; l <= j+1; l++) {
                //skip invalid squares
                if (!inBounds(k, l)) {
                    continue;
                }
                //skip own square
                if(k == i && l == j) {
                    continue;
                }
                //skip if neighbour not opposite colour
                if(squares[k][l].getState() != oppositeColour) {
                    continue;
                }
                //found possible valid move, expand in same direction
                int xDirection = (k-i);
                int yDirection = (l-j);
                //check still in bounds of the game board
                while(inBounds(k+xDirection, l+yDirection)) {
                    //while still opposite colour, continue to expand search
                    if(squares[k+xDirection][l+yDirection].getState() == SquareState.Open) {
                        break;
                    }
                    else if (squares[k+xDirection][l+yDirection].getState() == SquareState.Empty) {
                        break;
                    }
                    //if same colour found in direction after that, then move is valid
                    else if(squares[k+xDirection][l+yDirection].getOppositeState() == oppositeColour) {
                        //if flipping to be done, do it now
                        if(flip) {
                            if (!squares[k][l].isFlipped()) {
                                flipSquares(k, l, xDirection, yDirection);
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

    private boolean inBounds(int x, int y) {
        if (x < 0 || x >= size) {
            return false;
        }
        if (y < 0 || y >= size) {
            return false;
        }
        return true;
    }

    public void flipSquares(int k, int l, int xDirection, int yDirection) {
        System.out.println("flipping squares back from "+ (char) ('A' + (k+xDirection)) + " "+ (l+yDirection));
        //turn around and flip colours
        do {
            //create local variables to prevent loop exiting prematurely
            int newXDirection = reverseDirection(xDirection);
            int newYDirection = reverseDirection(yDirection);
            //flip square to desired colour
            System.out.println("flipping " + (char) ('A' + (k+newXDirection)) + " "+ (l+newYDirection));
            squares[k + newXDirection][l + newYDirection].flipColour();
            //now update variables for loop condition
            xDirection = newXDirection;
            yDirection = newYDirection;
        } while (!(xDirection == 0 && yDirection == 0));
    }
    private int expandDirection(int direction) {
        if (direction < 0) {
            return direction-1;
        }
        else if (direction > 0) {
            return direction+1;
        }
        else {
            return direction;
        }
    }
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
}
