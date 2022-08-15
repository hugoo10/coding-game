package fr.kahlouch.codingame.unleashthegeek;

import java.awt.*;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

public class World {
    public static final int BASE_X = 0;
    private final int width;
    private final int height;
    private final Cell[][] cells;
    private final Map<Integer, Entity> entityMap;
    private int radarCooldown;
    private int trapCooldown;

    private Integer radarAskedBy = null;
    private Integer trapAskedBy = null;

    public Integer getRadarAskedBy() {
        return radarAskedBy;
    }

    public void setRadarAskedBy(Integer radarAskedBy) {
        this.radarAskedBy = radarAskedBy;
    }

    public Integer getTrapAskedBy() {
        return trapAskedBy;
    }

    public void setTrapAskedBy(Integer trapAskedBy) {
        this.trapAskedBy = trapAskedBy;
    }

    public World(final Scanner scanner) {
        this.width = scanner.nextInt();
        this.height = scanner.nextInt();
        System.err.println("WORLD SUCCESSFULLY INITIALIZED - WIDTH: " + this.width + " - HEIGHT: " + this.height);
        this.cells = new Cell[this.width][this.height];
        this.entityMap = new HashMap<>();
        for (int x = 0; x < this.width; ++x) {
            for (int y = 0; y < this.height; ++y) {
                this.cells[x][y] = new Cell(x, y);
            }
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void updateWorld(Scanner scanner) {
        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < width; x++) {
                this.cells[x][y].updateCell(scanner);
                this.cells[x][y].clearEntities();
            }
        }
    }

    public void updateCooldowns(Scanner scanner) {
        this.radarCooldown = scanner.nextInt();
        this.trapCooldown = scanner.nextInt();
        System.err.println("RADAR-COOLDOWN: " + this.radarCooldown + " - TRAP-COOLDOWN: " + this.trapCooldown);
    }

    public void updateEntities(Scanner scanner, int nbEntities) {
        RobotRole[][] robotRolesToAttribute = new RobotRole[5][3];
        robotRolesToAttribute[0] = AllyRobot.ROLES_2;
        robotRolesToAttribute[1] = AllyRobot.ROLES_2;
        robotRolesToAttribute[2] = AllyRobot.ROLES_2;
        robotRolesToAttribute[3] = AllyRobot.ROLES_2;
        robotRolesToAttribute[4] = AllyRobot.ROLES_4;
        int robotAttributed = 0;

        for (int i = 0; i < nbEntities; i++) {
            final int entityId = scanner.nextInt();
            final int entityType = scanner.nextInt();
            if (!entityMap.containsKey(entityId)) {
                entityMap.put(entityId, new EntityBuilder().setId(entityId).setType(entityType).setWorld(this).build());
                if (entityMap.get(entityId) instanceof AllyRobot) {
                    ((AllyRobot) entityMap.get(entityId)).setRobotRoles(robotRolesToAttribute[robotAttributed++]);
                }
            }
            entityMap.get(entityId).updateInfos(scanner);
            int newX = entityMap.get(entityId).x;
            int newY = entityMap.get(entityId).y;
            if (newX >= 0 && newY >= 0) {
                this.cells[newX][newY].addEntity(entityMap.get(entityId));
            }
            entityMap.get(entityId).printDetails();
        }
    }

    public List<AllyRobot> retrieveAllyRobots() {
        return this.entityMap.entrySet()
                .parallelStream()
                .filter(entry -> entry.getValue() instanceof AllyRobot)
                .map(entry -> (AllyRobot) entry.getValue())
                .collect(Collectors.toList());
    }

    public List<Radar> retrieveRadars() {
        return this.entityMap.entrySet()
                .parallelStream()
                .filter(entry -> entry.getValue() instanceof Radar)
                .map(entry -> (Radar) entry.getValue())
                .collect(Collectors.toList());
    }

    public List<Cell> retrieveCellsWithOre() {
        return Arrays.stream(this.cells)
                .flatMap(Arrays::stream)
                .filter(cell -> cell.getNbOre() > 0)
                .collect(Collectors.toList());
    }

    public List<Cell> retrieveCellsUnknown() {
        return Arrays.stream(this.cells)
                .flatMap(Arrays::stream)
                .filter(cell -> cell.getNbOre() < 0 && cell.x > BASE_X)
                .collect(Collectors.toList());
    }

    public List<Cell> retrieveAllCells() {
        return Arrays.stream(this.cells)
                .flatMap(Arrays::stream)
                .collect(Collectors.toList());
    }

    public int getRadarCooldown() {
        return radarCooldown;
    }

    public void setRadarCooldown(int radarCooldown) {
        this.radarCooldown = radarCooldown;
    }

    public int getTrapCooldown() {
        return trapCooldown;
    }

    public void setTrapCooldown(int trapCooldown) {
        this.trapCooldown = trapCooldown;
    }

    public Cell getCellAt(Point point) {
        return this.cells[point.x][point.y];
    }

    public void drawCells() {
        for (int y = 0; y < this.height; ++y) {
            System.err.print("|");
            for (int x = 0; x < this.width; ++x) {
                System.err.print(" ");
                cells[x][y].drawCell();
            }
            System.err.println(" |");
        }
    }

    public Entity getEntityById(int id) {
        return entityMap.get(id);
    }
}
