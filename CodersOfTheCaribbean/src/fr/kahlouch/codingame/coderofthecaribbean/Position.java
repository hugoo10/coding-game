package fr.kahlouch.codingame.coderofthecaribbean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hugo on 17/04/2017.
 */
public class Position {
    protected  int x;
    protected  int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position position = (Position) o;

        if (x != position.x) return false;
        return y == position.y;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }

    @Override
    public String toString() {
        return "fr.kahlouch.codingame.coderofthecaribbean.Position{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    public boolean getByShip(Position position, Orientation orientation){
        return position.coveredCases(orientation).contains(this);
    }

    public List<Position> coveredCases(Orientation orientation) {
        List<Position> positions = new ArrayList<>();
        positions.add(this);
        switch(orientation) {
            case SOUTH_WEST:
            case NORTH_EAST:
                //down-left
                positions.add(new Position(x + (isEven()?-1:0), y + 1));
                //up-right
                positions.add(new Position(x + (isEven()?0:1), y - 1));
                break;
            case EAST:
            case WEST:
                //left
                positions.add(new Position(x -1, y));
                //right
                positions.add(new Position(x + 1, y));
                break;
            case SOUTH_EAST:
            case NORTH_WEST:
                //up-left
                positions.add(new Position(x + (isEven()?-1:0), y - 1));
                //down-right
                positions.add(new Position(x + (isEven()?0:1), y + 1));
                break;
        }
        return positions;
    }

    public double getDistance(Position position) {
        return Math.sqrt(Math.pow(position.x-this.x,2) + Math.pow(position.y-this.y,2));
    }

    public boolean isEven(){
        return y%2 == 0;
    }
}
