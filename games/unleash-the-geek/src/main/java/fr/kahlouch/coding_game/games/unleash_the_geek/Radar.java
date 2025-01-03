package fr.kahlouch.coding_game.games.unleash_the_geek;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class Radar extends Entity {
    private static List<Point> pointsCoverdedRelatively = Arrays.asList(new Point(0, -4), new Point(0, -3), new Point(0, -2), new Point(0, -1), new Point(0, 1), new Point(0, 2), new Point(0, 3), new Point(0, 4),
            new Point(-4, 0), new Point(-3, 0), new Point(-2, 0), new Point(-1, 0), new Point(1, 0), new Point(2, 0), new Point(3, 0), new Point(4, 0),
            new Point(1, 1), new Point(-1, -1), new Point(-1, 1), new Point(1, -1),
            new Point(2, 2), new Point(-2, -2), new Point(-2, 2), new Point(2, -2),
            new Point(-1, -3), new Point(-1, -2), new Point(-1, 2), new Point(-1, 3),
            new Point(1, -3), new Point(1, -2), new Point(1, 2), new Point(1, 3),
            new Point(-3, -1), new Point(-2, -1), new Point(2, -1), new Point(3, -1),
            new Point(-3, 1), new Point(-2, 1), new Point(2, 1), new Point(3, 1)
    );

    public static long newFound(Point radarPosition, List<Cell> cells) {
        return cells.parallelStream().filter(cell -> pointsCoverdedRelatively.contains(cell)).count();
    }

    public static Cell findTheBestLocationForRadar(World world, List<Cell> unknownCells) {
        return unknownCells.parallelStream().limit(30).max((c1, c2) ->
                Long.compare(newFound(c1, unknownCells), newFound(c2, unknownCells))
        ).orElse(null);
    }

    public static Cell findTheBestLocationForRadar2(World world, List<Cell> unknownCells) {
        return world.retrieveAllCells().parallelStream().max((c1, c2) ->
                Long.compare(newFound(c2, unknownCells), newFound(c1, unknownCells))
        ).orElse(null);
    }
}
