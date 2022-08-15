package fr.kahlouch.codingame.coderofthecaribbean;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by hugo on 17/04/2017.
 */
public class GameMap {
    public static Map<Integer, Ship> adverse = new TreeMap<>();
    public static Map<Integer, AbstractEntity> entities = new TreeMap<>();
    public static Map<Integer, Ship> teamShip = new TreeMap<>();
    public static Map<Integer, Barrel> barrels = new TreeMap<>();
    public static Map<Integer, Mine> mines = new TreeMap<>();
    public static Map<Integer, CanonBall> canonBall = new TreeMap<>();
    public static int WIDTH = 23;
    public static int HEIGHT = 21;
    public static int MINE_TURN = 4;
    public static int MIN_MINE_DIST = 5;
    public static int MIN_SHOT_DIST = 10;

    public static  AbstractEntity newEntity(String type, int x, int y, int... args) {
        switch (type) {
            case "SHIP":
                return  new Ship(new Position(x, y) , args);
            case "BARREL":
                return new Barrel(new Position(x, y), args);
            case "MINE":
                return new Mine(new Position(x, y));
            case "CANNONBALL":
                return new CanonBall(new Position(x, y));
            default:
                System.err.print("No type found: \""+type+"\"");
                return null;
        }
    }

    public static void updateBarrels(Position position, Orientation orientation) {
        int id = -1;
        for(Map.Entry<Integer, Barrel> entryBarrel : barrels.entrySet()) {
            Barrel barrel = entryBarrel.getValue();
            if(barrel.getPosition().getByShip(position, orientation)) {
                id = entryBarrel.getKey();
                break;
            }
        }
        if(id != -1) {
            barrels.remove(id);
            entities.remove(id);
        }
    }

    public static void move(Ship ship) {
        if(ship.getDestination() == null || ship.getDestination().equals(ship.getPosition())) {
            if(!barrels.isEmpty()) {
                stratRecolt(ship);
            } else {
                waitAdv(ship);
            }
        }
        else {
            Moves.MOVE.makeMove(ship.getDestination());
        }
    }

    public static void waitAdv(Ship ship) {
        Ship adv = adverse.values().iterator().next();
        if(adv.getPosition().getDistance(ship.getPosition()) < MIN_SHOT_DIST) {
            Moves.FIRE.makeMove(adv.getPosition());
        } else {
            if(ship.getSpeed() > 0) {
                Moves.SLOWER.makeMove();
            }
            else {
                Moves.WAIT.makeMove();
            }
        }
    }
    public static void stratAgress(Ship ship) {
        Ship adv = adverse.values().iterator().next();
        if(adv.getPosition().getDistance(ship.getPosition()) > 3) {
            Moves.MOVE.makeMove(adv.getPosition());
        }
        else {
            Moves.FIRE.makeMove(adv.getPosition());
        }
    }

    public static void stratRecolt(Ship ship) {
        double minDist = Double.MAX_VALUE;
        Mine toDestroy = null;
        if(!mines.isEmpty()) {
            for (Mine mine : mines.values()) {
                if(Ship.destroyedMines.contains(mine)) continue;
                double dist = mine.getPosition().getDistance(ship.getPosition());
                if (toDestroy == null || minDist > dist) {
                    if(dist < MIN_SHOT_DIST) {
                        toDestroy = mine;
                        minDist = toDestroy.getPosition().getDistance(ship.getPosition());
                    }
                }
            }
        }
        if(toDestroy != null){
            Ship.destroyedMines.add(toDestroy);
            Moves.FIRE.makeMove(toDestroy.getPosition());
        }
        else {
            Barrel target = null;
            for (Barrel barrel : barrels.values()) {
                if (target == null || minDist > barrel.getPosition().getDistance(ship.getPosition())) {
                    target = barrel;
                    minDist = target.getPosition().getDistance(ship.getPosition());
                }
            }
            ship.setDestination(target.getPosition());
            Moves.MOVE.makeMove(ship.getDestination());
        }
    }
}
