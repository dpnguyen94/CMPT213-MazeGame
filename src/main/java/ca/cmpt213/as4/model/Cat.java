package ca.cmpt213.as4.model;

public class Cat {
    private Location local;
    private Location prevLocal;

    public Cat(int row, int col) {
        this.local = new Location(row, col);
        this.prevLocal = new Location(row, col);
    }

    public Location getLocal() {
        return local;
    }

    public Location getPrevLocal() {
        return prevLocal;
    }

    public void setLocal(Location local) {
        this.local = local;
    }

    public void setPrevLocal(Location prevLocal) {
        this.prevLocal = prevLocal;
    }
}
