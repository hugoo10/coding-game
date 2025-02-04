package fr.kahlouch.coding_game.games.mars_lander;

import fr.kahlouch.codingame.marslander.model.Ship;
import fr.kahlouch.codingame.marslander.model.ShipGene;
import fr.kahlouch.genetic.GeneticAlgorithm;
import fr.kahlouch.genetic.population.Population;
import fr.kahlouch.genetic.utils.HistoryType;

import java.io.InputStream;

public class Player {
    private static final String MAP_FILE = "start_speed_wrong_side.txt";

    public static void main(String... args) {
        final InputStream inputStream = Test.class.getClassLoader().getResourceAsStream(MAP_FILE);
        final Resolver resolver = Resolver.getInstance();
        GeneticAlgorithm geneticAlgorithm = resolver.loadData(inputStream, 300D, 90L);
        Population firstGeneration = resolver.firstGeneration();
        do {
            geneticAlgorithm.compute(firstGeneration, HistoryType.NO_HISTORY);
            Ship best = (Ship) geneticAlgorithm.getPreviousBest();
            if (best.getFitness() >= 300) {
                best.getShipStates().stream().skip(1).forEach(ss -> System.out.println(ss.getAngle() + " " + ss.getPower()));
            } else {
                firstGeneration = geneticAlgorithm.getLineage().get(geneticAlgorithm.getLineage().size() - 1);
                firstGeneration.getIndividuals().forEach(individual -> {
                    individual.getChromosome().remove(0);
                    individual.getChromosome().add(new ShipGene(0, 4));
                });
                geneticAlgorithm = resolver.reloadData(inputStream, 300D, 90L);

            }
        } while (true);
    }
}
