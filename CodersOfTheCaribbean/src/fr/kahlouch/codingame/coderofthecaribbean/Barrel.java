package fr.kahlouch.codingame.coderofthecaribbean;

/**
 * Created by hugo on 17/04/2017.
 */
public class Barrel extends AbstractEntity{
    private int rhumQty;

    public Barrel(Position position, int ... args) {
        super(position);
        this.rhumQty = args[0];
    }

    public int getRhumQty() {
        return rhumQty;
    }

    public void setRhumQty(int rhumQty) {
        this.rhumQty = rhumQty;
    }
}
