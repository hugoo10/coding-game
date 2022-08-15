package fr.kahlouch.marslander.path_render.model;

import fr.kahlouch.genetic.GeneticAlgorithm;
import fr.kahlouch.genetic.population.Population;

import java.util.List;

public class DisplayState {
    private final GeneticAlgorithm geneticAlgorithm;

    private int generationIdx;
    private boolean displayOnlyBest;

    public DisplayState(GeneticAlgorithm geneticAlgorithm) {
        this.generationIdx = 0;
        this.displayOnlyBest = false;
        this.geneticAlgorithm = geneticAlgorithm;
    }

    public void nextGeneration() {
        if (this.displayOnlyBest) {
            this.generationIdx = this.geneticAlgorithm.getBestLineage()
                    .stream()
                    .filter(idx -> idx > generationIdx)
                    .min(Integer::compareTo)
                    .orElse(0);
        } else {
            this.generationIdx++;
            this.generationIdx = (generationIdx + this.geneticAlgorithm.getLineage().size()) % this.geneticAlgorithm.getLineage().size();
        }
    }

    public void previousGeneration() {
        if (displayOnlyBest) {
            List<Integer> bestLineage = this.geneticAlgorithm.getBestLineage();
            this.generationIdx = bestLineage
                    .stream()
                    .filter(idx -> idx < generationIdx)
                    .max(Integer::compareTo)
                    .orElse(bestLineage.get(bestLineage.size() - 1));
        } else {
            this.generationIdx--;
            this.generationIdx = (generationIdx + this.geneticAlgorithm.getLineage().size()) % this.geneticAlgorithm.getLineage().size();
        }

    }


    public void toggleDisplayBest() {
        this.displayOnlyBest = !displayOnlyBest;
    }

    public boolean isDisplayOnlyBest() {
        return displayOnlyBest;
    }

    public void firstGeneration() {
        this.generationIdx = 0;
    }

    public void lastGeneration() {
        this.generationIdx = geneticAlgorithm.getLineage().size() - 1;
    }

    public int getGenerationIdx() {
        return this.generationIdx;
    }


    public Population getCurrentGeneration() {
        return this.geneticAlgorithm.getLineage().get(this.generationIdx);
    }
}
