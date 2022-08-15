
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Cell extends Point {
    private int nbOre = -1;
    private boolean hole;
    private List<Entity> entities;

    public Cell(int x, int y) {
        super(x, y);
        this.entities = new ArrayList<>();
    }

    public void updateCell(Scanner scanner) {
        final String ore = scanner.next(); // amount of ore or "?" if unknown
        if (ore.equals("?")) {
            if(nbOre < 0) {
                this.nbOre = -1;
            }
        } else {
            this.nbOre = Integer.parseInt(ore);
        }
        this.hole = (1 == scanner.nextInt()); // 1 if cell has a hole
    }

    public void clearEntities() {
        this.entities.clear();
    }

    public boolean containsEntities() {
        return !this.entities.isEmpty();
    }

    public void addEntity(Entity entity) {
        this.entities.add(entity);
    }

    public int getNbOre() {
        return nbOre;
    }

    public boolean isHole() {
        return hole;
    }

    public void drawCell() {
        if (hole && nbOre == 0) {
            System.err.print("*");
        } else if(nbOre < 0) {
            System.err.print("?");
        }else{
            System.err.print(nbOre);
        }
    }
}
