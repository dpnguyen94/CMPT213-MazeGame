package ca.cmpt213.as4.model;

import java.util.List;

/**
 * UI class to store mapping information of the maze
 */

public class UI {
    private int numRows;
    private int numCols;

    private boolean[][] revealed;
    private boolean[][] isWalls;

    private Location mouseLocal;
    private List<Location> catsLocal;
    private Location cheeseLocal;

    private char[][] map;

    private final int[] dirRow = {0, 0, -1, +1, -1, +1, -1, +1};
    private final int[] dirCol = {-1, +1, -1, +1, 0, 0, +1, -1};

    public UI(MazeController mazeCtrl) {
        numRows = mazeCtrl.getNumRows();
        numCols = mazeCtrl.getNumCols();
        isWalls = mazeCtrl.getMaze();

        revealed = new boolean[numRows][numCols];
        map = new char[numRows][numCols];

        //Reveal all borders
        for (int i = 0; i < mazeCtrl.getNumRows(); i ++) {
            for (int j = 0; j < mazeCtrl.getNumCols(); j ++) {
                if (i == 0 || i == mazeCtrl.getNumRows() - 1 || j == 0 || j == mazeCtrl.getNumCols() - 1) {
                    revealed[i][j] = true;
                } else {
                    revealed[i][j] = false;
                }
            }
        }

        updateUI(mazeCtrl);
        populateMap();
    }

    public void revealAll() {
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                revealed[i][j] = true;
            }
        }
        populateMap();
    }

    public void updateUI(MazeController mazeCtrl) {
        mouseLocal = mazeCtrl.getMouseLocal();
        catsLocal = mazeCtrl.getCatsLocal();
        cheeseLocal = mazeCtrl.getCheeseLocal();

        revealed[mouseLocal.getRow()][mouseLocal.getCol()] = true;
        for (int k = 0; k < 8; k ++) {
            int row = mouseLocal.getRow() + dirRow[k];
            int col = mouseLocal.getCol() + dirCol[k];

            revealed[row][col] = true;
        }

        populateMap();
    }

    public boolean[][] getRevealed() {
        return revealed;
    }

    private void populateMap() {
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                if (revealed[i][j]) {
                    if (isWalls[i][j]) {
                        map[i][j] = '#';
                    } else if (mouseLocal.isSame(new Location(i, j))) {
                        map[i][j] = '@';
                    } else {
                        map[i][j] = ' ';
                    }
                } else {
                    map[i][j] = '.';
                }
            }
        }

        for (Location local: catsLocal) {
            int row = local.getRow();
            int col = local.getCol();

            if (local.isSame(mouseLocal)) {
                map[row][col] = 'X';
            } else {
                map[row][col] = '!';
            }
        }

        if (!cheeseLocal.isSame(mouseLocal)) {
            map[cheeseLocal.getRow()][cheeseLocal.getCol()] = '$';
        }
    }

    public void printMap() {

        for (int i = 0; i < numRows; i ++) {
            for (int j = 0; j < numCols; j ++) {
                System.out.print(map[i][j]);
            }
            System.out.println();
        }
    }
}
