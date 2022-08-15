package main.java.fr.kahlouch.marslander.path_render.component.graphics;


import fr.kahlouch.gameresources.graphics._2d.Graphics2D;
import fr.kahlouch.marslander.model.Ship;
import fr.kahlouch.marslander.model.ShipState;
import fr.kahlouch.marslander.model.World;
import fr.kahlouch.marslander.path_render.model.DisplayState;
import fr.kahlouch.marslander.physics.Position;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class MarsGraphicsComponent {
    private static final float factor = 0.15f;

    public void render(DisplayState displayState, Graphics2D graphics) {
        //centerCamera(graphics);
        drawBackground(graphics);
        drawGenerationNb(displayState, graphics);
        drawSurface(graphics);
        drawPaths(displayState, graphics);
    }

    private void centerCamera(Graphics2D graphics) {
        final var dimension = World.getInstance().getUpperRight();
        graphics.setCameraPosition(0, 0, -500);
    }

    private void drawBackground(Graphics2D graphics) {
        final var dimension = World.getInstance().getUpperRight();
        final var rectangle = new Rectangle(dimension.getX() * factor, dimension.getY() * factor, Color.BLACK);
        graphics.draw(rectangle);
    }

    private void drawGenerationNb(DisplayState displayState, Graphics2D graphics) {
        final var dimension = World.getInstance().getUpperRight();
        final var generationNb = new Text();
        generationNb.setFont(Font.font(null, FontWeight.BOLD, null, 20));
        generationNb.setFill(Color.ORANGE);
        generationNb.setX(dimension.getX() * factor);
        generationNb.setY(dimension.getY() * factor);
        generationNb.setText(displayState.getGenerationIdx() + 1 + "");
        graphics.draw(generationNb);
    }

    private void drawSurface(Graphics2D graphics) {
        convertPointsToLines(World.getInstance().getSurface(), Color.RED)
                .forEach(graphics::draw);
    }

    private void drawPaths(DisplayState displayState, Graphics2D graphics) {
        if (displayState.isDisplayOnlyBest()) {
            displayState.getCurrentGeneration().getBest()
                    .map(Ship.class::cast)
                    .ifPresent(best -> convertShipStatesToLines(best.getShipStates(), getColorByMark(best)).forEach(graphics::draw));
        } else {
            displayState.getCurrentGeneration().getIndividuals().forEach(individual -> {
                if (individual instanceof Ship ship) {
                    convertShipStatesToLines(ship.getShipStates(), getColorByMark(ship))
                            .forEach(graphics::draw);
                }
            });
        }
    }

    private List<Node> convertPointsToLines(List<Position> points, Color color) {
        List<Node> lines = new ArrayList<>();
        for (var i = 1; i < points.size(); ++i) {
            var line = fromPositionsToLine(points.get(i - 1), points.get(i));
            line.setStroke(color);
            line.setStrokeWidth(1);
            lines.add(line);
        }
        return lines;
    }


    private Color getColorByMark(Ship chromosome) {
        if (chromosome.getFitness() >= 300) {
            return Color.WHITE;
        } else if (chromosome.getFitness() >= 200) {
            return Color.GREEN;
        } else if (chromosome.getFitness() >= 100) {
            return Color.BLUE;
        }
        return Color.RED;
    }

    private List<Node> convertShipStatesToLines(List<ShipState> shipStates, Color color) {
        List<Node> lines = new ArrayList<>();
        for (var i = 1; i < shipStates.size(); ++i) {
            var previous = shipStates.get(i - 1);
            var next = shipStates.get(i);
            var line = fromPositionsToLine(previous.getPosition(), next.getPosition());
            line.setStroke(color);
            line.setStrokeWidth(1);
            lines.add(line);
        }
        return lines;
    }

    private Line fromPositionsToLine(Position from, Position to) {
        final var line = new Line();
        line.setStartX(from.getX() * factor);
        line.setStartY((World.getInstance().getUpperRight().getY() - from.getY()) * factor);
        line.setEndX(to.getX() * factor);
        line.setEndY((World.getInstance().getUpperRight().getY() - to.getY()) * factor);
        return line;
    }
}
