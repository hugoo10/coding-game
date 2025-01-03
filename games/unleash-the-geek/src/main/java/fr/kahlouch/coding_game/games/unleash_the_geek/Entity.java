package fr.kahlouch.coding_game.games.unleash_the_geek;

import java.awt.*;
import java.util.Scanner;

public abstract class Entity extends Point {
    protected int id;
    protected RobotCarry carry;
    protected World world;

    public void setId(int id) {
        this.id = id;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public int getId() {
        return id;
    }

    public RobotCarry getCarry() {
        return carry;
    }

    public void updateInfos(Scanner scanner) {
        this.x = scanner.nextInt();
        this.y = scanner.nextInt(); // position of the entity
        switch (scanner.nextInt()) {
            case -1:
                this.carry = RobotCarry.NONE;
                break;
            case 2:
                this.carry = RobotCarry.RADAR;
                break;
            case 3:
                this.carry = RobotCarry.TRAP;
                break;
            case 4:
                this.carry = RobotCarry.ORE;
                break;
            default:
                this.carry = null;
        }
    }

    public void printDetails() {
        System.err.println(this.id + " - " + this.getClass() + " - [" + this.x + ";" + this.y + "] - " + this.carry);
    }
}
