/*
  Author: <Christopher O'Driscoll>
  Id: <al0038>
  Study program: <Systemutvecklare TGSYA20h>
*/
package model;

public class GameBoard {

    private GameManager model;

    private final int size;  //height of game board

    private final Square[][] squares; //an array of squares on the board

    private int whiteScore;
    private int blackScore;


    public GameBoard(GameManager model, int size) {

        this.model = model;
        this.size = size;
        squares = new Square[size][size];
        setupStartingBoard();
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


    public int getWhiteScore() {
        return whiteScore;
    }

    public void setWhiteScore(int whiteScore) {
        this.whiteScore = whiteScore;
    }

    public int getBlackScore() {
        return blackScore;
    }

    public void setBlackScore(int blackScore) {
        this.blackScore = blackScore;
    }

    // check for valid moves
    public void updateOpenSquares(SquareState stateToLookFor) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                //first reset all current valid moves (for opposite player)
                if(squares[i][j].getState() == SquareState.Open) {
                    squares[i][j].setState(SquareState.Empty);
                }
                //now check that square is not occupied
                if(squares[i][j].getState() != SquareState.Empty) {
                    continue;
                }
                //split to recursive method to check if valid move possible
                if (validMove(i, j, stateToLookFor)) {
                    squares[i][j].setState(SquareState.Open);
                }

            }
        }
    }
    //returns true if valid move possible
    private boolean validMove(int i, int j, SquareState stateToLookFor) {
        //check if any of its neighbours are opposite colour
        System.out.println("checking valid moves for " +i+ ", " +j);
        for (int k = i-1; k <= i+1; k++) {
            for (int l = j-1; l <= j+1; l++) {
                //skip invalid squares
                if (!inBounds(k, l)) {
                    System.out.println("out of bounds " +k+ ", " +l);
                    continue;
                }
                //skip own square
                if(k == i && l == j) {
                    System.out.println("own square " +k+ ", " +l);
                    continue;
                }
                if(squares[k][l].getState() != stateToLookFor) {
                    System.out.println("neighbour not opposite colour at " +k+ ", " +l+ " , continuing");
                    continue;
                }

                    System.out.println("found opposite colour at " +k+ ", " +l+ " expanding");
                    //found possible valid move, expand in same direction
                    int xDirection = (k-i);
                    int yDirection = (l-j);
                    //check still in bounds of the game board
                    while(inBounds(k+xDirection, l+yDirection)) {
                        System.out.println(" checking " +(k+xDirection)+ ", " +(l+yDirection));
                        //while still opposite colour, continue to expand search
                        if(squares[k+xDirection][l+yDirection].getState() == stateToLookFor) {
                            System.out.println(stateToLookFor + " found, expanding ");
                            xDirection += xDirection;
                            yDirection += yDirection;
                        }
                        //if same colour found in direction after that, then move is valid
                        else if(squares[k+xDirection][l+yDirection].getOppositeState() == stateToLookFor) {
                            //no need to check further if valid move found
                            System.out.println("valid move at " + i + ", " + j);
                            return true;
                        }
                        else {
                            System.out.println("no valid move at " + i +", "+ j);
                            return false;
                        }
                    }
                }

            }

        return false;
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

}
