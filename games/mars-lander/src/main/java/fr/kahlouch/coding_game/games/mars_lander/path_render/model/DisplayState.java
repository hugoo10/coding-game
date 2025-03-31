package fr.kahlouch.coding_game.games.mars_lander.path_render.model;

import fr.kahlouch.coding_game.games.mars_lander.model.Ship;
import fr.kahlouch.coding_game.games.mars_lander.model.ShipGene;
import fr.kahlouch.coding_game.games.mars_lander.model.ShipPath;
import fr.kahlouch.genetic.algorithm.execution.listener.ExecutionListener;
import fr.kahlouch.genetic.algorithm.vo.Population;

import java.util.ArrayList;
import java.util.List;

public class DisplayState implements ExecutionListener<ShipGene, Ship, ShipPath> {
    private final List<Population<ShipGene, Ship, ShipPath>> bestPopulations;
    private final CircularListIterator<Population<ShipGene, Ship, ShipPath>> bestPopulationIterator;

    private boolean algorithmOver;
    private boolean displayOnlyBest;
    private Population<ShipGene, Ship, ShipPath> populationToDisplay;

    public DisplayState() {
        this.bestPopulations = new ArrayList<>();
        this.bestPopulationIterator = new CircularListIterator<>(this.bestPopulations);
        this.algorithmOver = false;
        this.displayOnlyBest = false;
    }

    public void nextGeneration() {
        if (!this.algorithmOver) return;
        this.populationToDisplay = this.bestPopulationIterator.next();
    }

    public void previousGeneration() {
        if (!this.algorithmOver) return;
        this.populationToDisplay = this.bestPopulationIterator.previous();
    }

    public void toggleDisplayBest() {
        this.displayOnlyBest = !displayOnlyBest;
    }

    public boolean isDisplayOnlyBest() {
        return displayOnlyBest;
    }

    public void firstPopulation() {
        if (!this.algorithmOver) return;
        this.populationToDisplay = this.bestPopulationIterator.first();
    }

    public void lastPopulation() {
        if (!this.algorithmOver) return;
        this.populationToDisplay = this.bestPopulationIterator.last();
    }


    public Population<ShipGene, Ship, ShipPath> getPopulationToDisplay() {
        return this.populationToDisplay;
    }

    @Override
    public void send(Population<ShipGene, Ship, ShipPath> population) {
        this.populationToDisplay = population;
        if (this.bestPopulations.isEmpty()) {
            this.bestPopulations.add(population);
            return;
        }

        final var currentBest = this.bestPopulations.getLast().getBest();
        final var contender = population.getBest();
        if (currentBest.compareTo(contender) < 0) {
            this.bestPopulations.add(population);
        }
    }

    @Override
    public void sendEndSignal() {
        this.algorithmOver = true;
        this.populationToDisplay = this.bestPopulationIterator.last();
    }
}
