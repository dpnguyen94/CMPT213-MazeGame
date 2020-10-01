package ca.cmpt213.as4.restapi;

import ca.cmpt213.as4.model.MazeController;
import ca.cmpt213.as4.model.MazeGame;
import ca.cmpt213.as4.model.UI;

import java.util.List;

public class ApiBoardWrapper {
    public int boardWidth;
    public int boardHeight;
    public ApiLocationWrapper mouseLocation;
    public ApiLocationWrapper cheeseLocation;
    public List<ApiLocationWrapper> catLocations;
    public boolean[][] hasWalls;
    public boolean[][] isVisible;

    public static ApiBoardWrapper makeFromGame(MazeGame game) {
        ApiBoardWrapper wrapper = new ApiBoardWrapper();

        MazeController mazeCtrl = game.getMazeCtrl();
        UI ui = game.getUI();

        wrapper.boardHeight = mazeCtrl.getNumRows();
        wrapper.boardWidth = mazeCtrl.getNumCols();

        wrapper.mouseLocation = ApiLocationWrapper.makeFromCellLocation(mazeCtrl.getMouseLocal());
        wrapper.cheeseLocation = ApiLocationWrapper.makeFromCellLocation(mazeCtrl.getCheeseLocal());
        wrapper.catLocations = ApiLocationWrapper.makeFromCellLocations(mazeCtrl.getCatsLocal());

        wrapper.hasWalls = mazeCtrl.getMaze();
        wrapper.isVisible = ui.getRevealed();

        return wrapper;
    }
}