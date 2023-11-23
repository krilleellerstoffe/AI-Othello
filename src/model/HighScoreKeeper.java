/*
  Author: <Christopher O'Driscoll>
  Id: <al0038>
  Study program: <Systemutvecklare TGSYA20h>
*/
package model;

public class HighScoreKeeper {

    private final int[] highScores;
    private final String[] highScoresNames;

    public HighScoreKeeper(int size){

        highScores = new int[10];   //10 best scores
        highScoresNames = new String[10];   //player names for the 10 best scores
        initialiseHighScores(size);
    }
    //makes a top 10 list with worst scores possible
    private void initialiseHighScores(int size){
        for(int i = 0; i< highScores.length; i++){
            highScores[i] = 0;
            highScoresNames[i] = "A A A";
        }
    }
    //organises the high score list by score (ascending)(bubblesort)
    //TODO reverse order of list
    public void sortHighScores(){
        boolean changeMade;
        int sorted = 1;
        do{
            changeMade = false;                          //reset every loop (prevents continuous loop)
            for(int i = 0; i < highScores.length-sorted; i++){    //checks up to sorted values in array
                if(highScores[i] > highScores[i+1]){
                    int tempScore = highScores[i];
                    String tempName = highScoresNames[i];
                    highScores[i] = highScores[i+1];
                    highScores[i+1] = tempScore;
                    highScoresNames[i] = highScoresNames[i+1];
                    highScoresNames[i+1] = tempName;
                    changeMade = true;
                }
            }
            sorted++;
        }while (changeMade);
    }
    //checks if new score would make it into the top ten
    public boolean checkHighScore(int guesses){

        boolean newHighScore = false;
        if(guesses < highScores[9])
            newHighScore = true;
        return newHighScore;
    }
    //inserts a new high score and reorganises them
    public void setNewHighScore(String name, int score){

        highScores[9] = score;
        highScoresNames[9] = name;
        sortHighScores();
    }
    //returns a list of the current 10 best player names and their scores
    public String[] getHighScores(){
        String[] nameAndScore = new String[highScores.length];
        for (int i = 0; i < highScores.length; i++){
            nameAndScore[i] = String.format("%d - %s", highScores[i], highScoresNames[i]);
        }
        return nameAndScore;
    }
}
