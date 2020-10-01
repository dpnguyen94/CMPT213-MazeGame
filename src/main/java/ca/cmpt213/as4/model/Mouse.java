package ca.cmpt213.as4.model;

public class Mouse {
    private Location local;

    public Mouse(int row, int col) {
        this.local = new Location(row, col);
    }

    public Location getLocal() {
        return local;
    }

    public void setLocal(Location local) {
        this.local = local;
    }
}
