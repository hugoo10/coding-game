package fr.kahlouch.coding_game.games.mars_lander.model;

import javafx.geometry.Point2D;

import java.util.List;

public record ShipPath(List<ShipState> shipStates, Point2D intersectSurfacePoint) {
}
