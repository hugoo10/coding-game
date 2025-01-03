package fr.kahlouch.coding_game.games.coder_of_the_caribbean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hugo on 17/04/2017.
 */
public class Ship extends AbstractEntity{
    private Orientation orientation;
    private int speed;
    private int rhumStock;
    public static List<Mine> destroyedMines = new ArrayList<>();

    //Values for team ship
    private Position destination = null;

    public Ship(Position position, int ... args) {
        super(position);
        for(Orientation orientation : Orientation.values()){
            if(orientation.getValue() == args[0]) {
                this.orientation = orientation;
                break;
            }
        }
        this.speed = args[1];
        this.rhumStock = args[2];
    }

    public void update(int x, int y, int...args) {
        super.update(x, y, args);
        for(Orientation orientation : Orientation.values()){
            if(orientation.getValue() == args[0]) {
                this.orientation = orientation;
                break;
            }
        }
        this.speed = args[1];
        this.rhumStock = args[2];
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getRhumStock() {
        return rhumStock;
    }

    public void setRhumStock(int rhumStock) {
        this.rhumStock = rhumStock;
    }

    public Position getDestination() {
        return destination;
    }

    public void setDestination(Position destination) {
        this.destination = destination;
    }
}
