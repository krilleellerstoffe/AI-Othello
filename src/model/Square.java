/*
  Author: <Christopher O'Driscoll>
  Id: <al0038>
  Study program: <Systemutvecklare TGSYA20h>
*/
package model;


public class Square {

    private SquareState state;

    public Square(SquareState state) {
        this.state = state;
    }

    public SquareState getState() {
        return state;
    }

    public void setState(SquareState state) {
        this.state = state;
    }

    public SquareState getOppositeState() {
        if(state == SquareState.Black) {
            return SquareState.White;
        }
        else if (state == SquareState.White) {
            return  SquareState.Black;
        }
        else {
            return state;
        }
    }
}
