package ca.cmpt213.as4.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Control the game and knows formation of maze, location of objects and movement of actors
 */
public class MazeController {
    private int numRows;
    private int numCols;

    private Maze maze;
    private Mouse mouse;
    private List<Cat> cats = new ArrayList<>();
    private Cheese cheese;
    private int numCheese;
    private int cheeseWon;

    public MazeController(int numRows, int numCols, int numCheese) {
        this.numRows = numRows;
        this.numCols = numCols;

        maze = new Maze(numRows, numCols);
        mouse = new Mouse(1, 1);

        cats.add(new Cat(1, numCols - 2));
        cats.add(new Cat(numRows - 2, 1));
        cats.add(new Cat(numRows - 2, numCols - 2));

        this.numCheese = numCheese;
        cheeseWon = 0;
        generateCheese();
    }

    private void generateCheese() {
        while (true) {
            int nextRow = (int) (Math.random() * (numRows - 1));
            int nextCol = (int) (Math.random() * (numCols - 1));

            if (maze.hasWall(nextRow, nextCol) || isOccupied(nextRow, nextCol)) continue;
            else {
                cheese = new Cheese(nextRow, nextCol);
                break;
            }
        }
    }

    private boolean isOccupied(int row, int col) {
        Location cheeseLocal = new Location(row, col);

        if (mouse.getLocal().isSame(cheeseLocal)) {
            return true;
        }

        for (Cat cat: cats) {
            if (cat.getLocal().isSame(cheeseLocal)) {
                return true;
            }
        }

        return false;
    }

    public Location getMouseLocal() {
        return mouse.getLocal();
    }

    public List<Location> getCatsLocal() {
        List<Location> locals = new ArrayList<>();
        for (Cat cat: cats) {
            locals.add(cat.getLocal());
        }

        return locals;
    }

    public Location getCheeseLocal() {
        return cheese.getLocal();
    }

    public boolean[][] getMaze() {
        return maze.getMaze();
    }

    public int moveMouse(int dirRow, int dirCol) {
        int nextRow = mouse.getLocal().getRow() + dirRow;
        int nextCol = mouse.getLocal().getCol() + dirCol;

        Location nextLocal = new Location(nextRow, nextCol);

        if (maze.hasWall(nextRow, nextCol)) {
            return GameCode.INVALID_MOVE_THROUGH_WALLS;
        }

        mouse.setLocal(nextLocal);

        for (Cat cat: cats) {
            if (cat.getLocal().isSame(nextLocal)) {
                return GameCode.GAME_LOST;
            }
        }

        return checkCheese();
    }

    public int moveCats() {
        final int[] dirRow = {0, 0, -1, +1};
        final int[] dirCol = {-1, +1, 0, 0};

        Integer[] order = {0, 1, 2, 3};

        for (Cat cat: cats) {
            int row = cat.getLocal().getRow();
            int col = cat.getLocal().getCol();

            Collections.shuffle(Arrays.asList(order));

            boolean moveFound = false;
            for (int k = 0; k < 4; k ++) {
                int nextRow = row + dirRow[k];
                int nextCol = col + dirCol[k];

                Location local = new Location(nextRow, nextCol);
                if (maze.hasWall(nextRow, nextCol) || cat.getPrevLocal().isSame(local)) continue;

                moveFound = true;
                cat.setPrevLocal(new Location(row, col));
                cat.setLocal(local);
                break;
            }

            if (!moveFound) {
                cat.setLocal(cat.getPrevLocal());
                cat.setPrevLocal(new Location(row, col));
            }

            if (cat.getLocal().isSame(mouse.getLocal())) {
                return GameCode.GAME_LOST;
            }
        }

        return checkCheese();
    }

    private int checkCheese() {
        if (cheese.getLocal().isSame(mouse.getLocal())) {
            cheeseWon ++;
            if (cheeseWon == numCheese) {
                return GameCode.GAME_WON;
            } else {
                generateCheese();
            }
        }

        return GameCode.VALID_MOVE;
    }

    public void setNumCheese(int numCheese) {
        this.numCheese = numCheese;
    }

    public int getNumRows() {
        return numRows;
    }

    public int getNumCols() {
        return numCols;
    }

    public int getNumCheese() {
        return numCheese;
    }

    public int getCheeseWon() {
        return cheeseWon;
    }
}
