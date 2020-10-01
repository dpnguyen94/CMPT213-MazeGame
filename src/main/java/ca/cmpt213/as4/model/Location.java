package ca.cmpt213.as4.model;

public class Location {
    private int row;
    private int col;

    public Location(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }



    public boolean isSame(Location local) {
        return (row == local.getRow() && col == local.getCol());
    }
}
