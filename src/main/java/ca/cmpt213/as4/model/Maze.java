package ca.cmpt213.as4.model;

import java.util.Arrays;
import java.util.Collections;

/**
 * Store information of the maze and methods to generate maze using DFS
 */

public class Maze {
    private boolean[][] isWalls;

    public Maze(int numRows, int numCols) {
        isWalls = new boolean[numRows][numCols];
        initializeMaze(numRows, numCols);
        generateMaze(numRows, numCols);
    }

    /**
        Create maze border and initialize rooms
     */
    private void initializeMaze(int numRows, int numCols) {
        for (int i = 0; i < numRows; i ++) {
            for (int j = 0; j < numCols; j++) {
                if (i % 2 == 1 && j % 2 == 1) {
                    isWalls[i][j] = false;
                } else {
                    isWalls[i][j] = true;
                }
            }
        }

        //Make sure borders are walls
        for (int i = 0; i < numRows; i ++) {
            isWalls[i][numCols - 1] = true;
        }

        for (int j = 0; j < numCols; j ++) {
            isWalls[numRows - 1][j] = true;
        }
    }

    /**
     * Recursively remove walls and create paths
     */
    private void generateMaze(int numRows, int numCols) {
        class DFS {
            private boolean[][] visited = new boolean[numRows][numCols];

            private void findPath(int row, int col) {
                final int[] dirRow = {0, 0, -2, +2};
                final int[] dirCol = {-2, +2, 0, 0};

                visited[row][col] = true;

                Integer[] order = {0, 1, 2, 3};
                Collections.shuffle(Arrays.asList(order));

                for (int k = 0; k < 4; k ++) {
                    int nextRow = row + dirRow[order[k]];
                    int nextCol = col + dirCol[order[k]];

                    if (nextRow < 0 || nextRow >= numRows - 1 || nextCol < 0 || nextCol >= numCols - 1 || visited[nextRow][nextCol]) continue;

                    isWalls[row + dirRow[order[k]] / 2][col + dirCol[order[k]] / 2] = false;
                    findPath(nextRow, nextCol);
                }
            }
        }

        new DFS().findPath(1, 1);

        //Create more paths in maze randomly
        for (int i = 1; i < numRows - 1; i ++) {
            for (int j = 1; j < numCols - 1; j ++) {
                if ((isWalls[i][j] && isWalls[i][j - 1] && isWalls[i][j + 1]) || (isWalls[i][j] && isWalls[i - 1][j] && isWalls[i + 1][j]))  {
                    //30-70 chance that the wall would be removed
                    int rand = (int) (Math.random() * 10) + 1;
                    if (rand <= 3) {
                        isWalls[i][j] = false;
                    }
                }
            }
        }

        //Empty corners
        isWalls[1][numCols - 2] = false;
        isWalls[numRows - 2][1] = false;
        isWalls[numRows - 2][numCols - 2] = false;
    }

    public boolean hasWall(int row, int col) {
        return isWalls[row][col];
    }

    public boolean[][] getMaze() {
        return isWalls;
    }

}
