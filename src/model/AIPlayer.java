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


    public AIPlayer(Controller controller,GameManager model) {

        this.controller = controller;
        this.model = model;
    }

}
