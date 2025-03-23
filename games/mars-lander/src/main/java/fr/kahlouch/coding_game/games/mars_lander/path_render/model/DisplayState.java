package fr.kahlouch.coding_game.games.mars_lander.path_render.model;

import fr.kahlouch.genetic.algorithms._genetic.GeneticAlgorithmExecutionHistory;
import fr.kahlouch.genetic.population.EvaluatedGeneration;

public class DisplayState {
    private final GeneticAlgorithmExecutionHistory history;

    private CircularListIterator<EvaluatedGeneration> lineageIterator;
    private EvaluatedGeneration currentGeneration;
    private boolean displayOnlyBest;

    public DisplayState(GeneticAlgorithmExecutionHistory history) {
        this.history = history;
        this.displayOnlyBest = false;

        this.lineageIterator = new CircularListIterator<>(history.getLineage());
        nextGeneration();
    }

    public void nextGeneration() {
        this.currentGeneration = this.lineageIterator.next();

    }

    public void previousGeneration() {
        this.currentGeneration = this.lineageIterator.previous();
    }

    public void toggleDisplayBest() {
        this.displayOnlyBest = !displayOnlyBest;
        if(displayOnlyBest) {
            this.lineageIterator = new CircularListIterator<>(history.getLineage());
        } else {
            this.lineageIterator = new CircularListIterator<>(history.getBestLineage());
        }
        nextGeneration();
    }

    public boolean isDisplayOnlyBest() {
        return displayOnlyBest;
    }

    public void firstGeneration() {
        this.currentGeneration = this.lineageIterator.first();
    }

    public void lastGeneration() {
        this.currentGeneration = this.lineageIterator.last();
    }

    public EvaluatedGeneration getCurrentGeneration() {
        return this.currentGeneration;
    }
}
