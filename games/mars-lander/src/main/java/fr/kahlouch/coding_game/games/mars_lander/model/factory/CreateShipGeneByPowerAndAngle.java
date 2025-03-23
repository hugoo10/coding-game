package fr.kahlouch.coding_game.games.mars_lander.model.factory;

import fr.kahlouch.genetic.factory.CustomGeneCreationInput;

public record CreateShipGeneByPowerAndAngle(double power, double angle) implements CustomGeneCreationInput {
}
