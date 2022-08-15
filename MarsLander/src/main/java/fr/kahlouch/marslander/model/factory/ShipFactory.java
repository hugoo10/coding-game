package fr.kahlouch.marslander.model.factory;

import fr.kahlouch.genetic.factory.AbstractGeneFactory;
import fr.kahlouch.genetic.factory.AbstractIndividualFactory;
import fr.kahlouch.genetic.population.Gene;
import fr.kahlouch.genetic.population.Individual;
import fr.kahlouch.marslander.model.Ship;

import java.util.List;

public class ShipFactory extends AbstractIndividualFactory {

    public ShipFactory(int chromosomeSize, AbstractGeneFactory geneFactory) {
        super(chromosomeSize, geneFactory);
    }

    @Override
    public Individual create(List<Gene> chromosome) {
        return new Ship(chromosome);
    }
}
