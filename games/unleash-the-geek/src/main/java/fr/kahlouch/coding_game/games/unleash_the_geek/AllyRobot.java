package fr.kahlouch.coding_game.games.unleash_the_geek;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Supplier;

public class AllyRobot extends Robot {
    public static final RobotRole[] ROLES_1 = {RobotRole.DIGGER, RobotRole.TRAPPER, RobotRole.SCOUT};
    public static final RobotRole[] ROLES_2 = {RobotRole.DIGGER, RobotRole.SCOUT, RobotRole.TRAPPER};
    public static final RobotRole[] ROLES_3 = {RobotRole.SCOUT, RobotRole.DIGGER, RobotRole.TRAPPER};
    public static final RobotRole[] ROLES_4 = {RobotRole.SCOUT, RobotRole.TRAPPER, RobotRole.DIGGER};
    public static final RobotRole[] ROLES_5 = {RobotRole.TRAPPER, RobotRole.DIGGER, RobotRole.SCOUT};
    public static final RobotRole[] ROLES_6 = {RobotRole.TRAPPER, RobotRole.SCOUT, RobotRole.DIGGER};

    private RobotRole[] robotRoles;
    private final Map<RobotRole, Supplier<Boolean>> roleFunctionMap = new HashMap<>();
    private Point destination = null;

    public AllyRobot() {
        roleFunctionMap.put(RobotRole.DIGGER, this::dig);
        roleFunctionMap.put(RobotRole.TRAPPER, this::trap);
        roleFunctionMap.put(RobotRole.SCOUT, this::scout);
    }

    public void setRobotRoles(RobotRole[] robotRoles) {
        this.robotRoles = robotRoles;
    }

    public void waitCommand() {
        System.out.println(Command.WAIT + " Nothing to do...");
    }

    public void digCommand(Point point) {
        this.digCommand(point.x, point.y);
    }

    public void digCommand(int x, int y) {
        System.out.println(String.format("%s %s %s %s", Command.DIG, x, y, "NBORE: " + world.getCellAt(new Point(x, y)).getNbOre()));
    }

    public void moveCommand(Point point) {
        this.moveCommand(point.x, point.y);
    }

    public void moveCommand(int x, int y) {
        System.out.println(String.format("%s %s %s", Command.MOVE, x, y));
    }

    public void requestRadarCommand() {
        //!! if not at x=0 then the robot will go at [0;y] first!!
        System.out.println(String.format("%s %s", Command.REQUEST, RobotCarry.RADAR));
        this.world.setRadarAskedBy(id);
    }

    public void requestTrapCommand() {
        //!! if not at x=0 then the robot will go at [0;y] first!!
        System.out.println(String.format("%s %s", Command.REQUEST, RobotCarry.TRAP));
        this.world.setTrapAskedBy(id);
    }

    public void returnBaseCommand() {
        moveCommand(0, y);
    }

    public void act() {
        if (x < 0 || y < 0) {
            waitCommand();
        } else if (this.roleFunctionMap.get(this.robotRoles[0]).get() || this.roleFunctionMap.get(this.robotRoles[1]).get() || this.roleFunctionMap.get(this.robotRoles[2]).get()) {
            System.err.println("MOVE DONE");
        } else {
            waitCommand();
        }
    }

    public boolean scout() {
        if (carry == RobotCarry.RADAR) {
            if (destination != null) {
                if (destination.equals(this)) {
                    destination = null;
                    digCommand(this);
                } else {
                    moveCommand(destination);
                    return true;
                }
                return true;
            } else {
                List<Cell> unknownCells = world.retrieveCellsUnknown();
                unknownCells.sort((a1, a2) -> new Random().nextInt());
                if (unknownCells.size() > 10) {
                    destination = Radar.findTheBestLocationForRadar(world, unknownCells);
                    moveCommand(destination);
                    return true;
                } else {
                    List<Radar> radarsToDetroy = world.retrieveRadars();
                    if (radarsToDetroy.size() > 0) {
                        Radar closestRadarToDestroy = radarsToDetroy.parallelStream().min((c1, c2) -> Double.compare(c1.distance(this), c2.distance(this))).orElse(null);
                        if (closestRadarToDestroy != null) {
                            destination = closestRadarToDestroy;
                            if (destination.equals(this)) {
                                digCommand(destination);
                                destination = null;
                            } else {
                                moveCommand(destination);
                            }
                            return true;
                        }
                        return true;
                    }
                    return false;
                }
            }
        } else if (carry == RobotCarry.NONE && world.getRadarCooldown() == 0 && (world.getRadarAskedBy() == null || world.getRadarAskedBy().equals(id))) {
            requestRadarCommand();
            return true;
        }
        return false;
    }

    public boolean trap() {
        return false;
    }

    public boolean dig() {
        if (carry == RobotCarry.ORE) {
            returnBaseCommand();
            return true;
        } else if (carry == RobotCarry.NONE) {
            if (destination != null && world.getCellAt(destination).getNbOre() > 0) {
                if (destination.equals(this)) {
                    destination = null;
                    digCommand(this);
                } else {
                    moveCommand(destination);
                    return true;
                }
                return true;
            } else {
                List<Cell> cellsWithOre = world.retrieveCellsWithOre();
                if (!cellsWithOre.isEmpty()) {
                    Cell possibleDestination = cellsWithOre.parallelStream().min((c1, c2) -> Double.compare(c1.distance(this), c2.distance(this))).orElse(null);
                    if (possibleDestination != null) {
                        destination = possibleDestination;
                        moveCommand(destination);
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
