package ca.cmpt213.as4.model;

public class Cheese {
    private Location local;

    public Cheese(int row, int col) {
        this.local = new Location(row, col);
    }

    public Location getLocal() {
        return local;
    }
}
