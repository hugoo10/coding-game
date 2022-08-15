package fr.kahlouch.codingame.unleashthegeek;

import java.util.Optional;
import java.util.Scanner;

/**
 * Deliver more ore to hq (left side of the map) than your opponent. Use radars to find ore but beware of traps!
 **/
public class Player {
    private String name;
    private int score;

    public Player(String name) {
        this.name = name;
    }

    public void updateScore(Scanner scanner) {
        this.score = scanner.nextInt();
        System.err.println(this.name + " - SCORE: " + this.score);
    }


    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        final World world = new World(in);
        final Player player = new Player("ME");
        final Player opponent = new Player("OPPONENT");
        // game loop
        for (; ; ) {
            player.updateScore(in);
            opponent.updateScore(in);
            world.updateWorld(in);
            final int entityCount = in.nextInt(); // number of entities visible to you
            world.updateCooldowns(in);
            world.updateEntities(in, entityCount);
            world.drawCells();
            Optional.ofNullable(world.getRadarAskedBy()).ifPresent(id -> {
                Entity entity = world.getEntityById(id);
                if(entity.carry == RobotCarry.RADAR) {
                    world.setRadarAskedBy(null);
                }
            });
            Optional.ofNullable(world.getTrapAskedBy()).ifPresent(id -> {
                Entity entity = world.getEntityById(id);
                if(entity.carry == RobotCarry.TRAP) {
                    world.setTrapAskedBy(null);
                }
            });
            world.retrieveAllyRobots().forEach(AllyRobot::act);
        }
    }
}
