package ca.cmpt213.as4.model;

public class MazeGame {
    private final int NUMBER_OF_ROWS = 20;
    private final int NUMBER_OF_COLS = 15;
    private final int NUMBER_OF_CHEESE = 5;

    private MazeController mazeCtrl;
    private UI ui;
    private int gameId;
    private boolean isGameWon;
    private boolean isGameLost;

    public MazeGame(int gameId) {
        mazeCtrl = new MazeController(NUMBER_OF_ROWS, NUMBER_OF_COLS, NUMBER_OF_CHEESE);
        ui = new UI(mazeCtrl);
        this.gameId = gameId;
    }

    public int makeMoves(int dirRow, int dirCol) {
        int result = mazeCtrl.moveMouse(dirRow, dirCol);
        ui.updateUI(mazeCtrl);

        validateGameCode(result);

        return result;
    }

    public int moveCats() {
        int result =  mazeCtrl.moveCats();
        validateGameCode(result);

        return result;
    }

    private void validateGameCode(int result) {
        if (result == GameCode.GAME_WON) {
            ui.revealAll();
            isGameWon = true;
        } else if (result == GameCode.GAME_LOST) {
            ui.revealAll();
            isGameLost = true;
        }
    }

    public MazeController getMazeCtrl() {
        return mazeCtrl;
    }

    public boolean isGameWon() {
        return isGameWon;
    }

    public boolean isGameLost() {
        return isGameLost;
    }

    public UI getUI() {
        return ui;
    }

    public int getGameId() {
        return gameId;
    }
}
