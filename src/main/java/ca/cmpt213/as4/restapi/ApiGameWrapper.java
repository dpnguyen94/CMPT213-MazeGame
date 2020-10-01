package ca.cmpt213.as4.restapi;

import ca.cmpt213.as4.model.MazeController;
import ca.cmpt213.as4.model.MazeGame;

public class ApiGameWrapper {
    public int gameNumber;
    public boolean isGameWon;
    public boolean isGameLost;
    public int numCheeseFound;
    public int numCheeseGoal;

    public static ApiGameWrapper makeFromGame(MazeGame game, int id) {
        ApiGameWrapper wrapper = new ApiGameWrapper();
        wrapper.gameNumber = id;

        MazeController mazeCtrl = game.getMazeCtrl();

        wrapper.isGameWon = game.isGameWon();
        wrapper.isGameLost = game.isGameLost();
        wrapper.numCheeseFound = mazeCtrl.getCheeseWon();
        wrapper.numCheeseGoal = mazeCtrl.getNumCheese();

        return wrapper;
    }
}