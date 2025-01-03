import fr.kahlouch.codingame.marslander.model.ShipGene;
import fr.kahlouch.codingame.marslander.model.factory.ShipGeneFactory;
import fr.kahlouch.genetic.population.Gene;
import org.junit.jupiter.api.Test;

import java.util.Random;

public class TestGene {
    @Test
    public void testBreed() {
        for (ShipGene gene1 : ShipGeneFactory.ALL_EXISTING_GENES_MAP.values()) {
            for (ShipGene gene2 : ShipGeneFactory.ALL_EXISTING_GENES_MAP.values()) {
                breed(gene1, gene2);
            }
        }
    }

    private void breed(ShipGene g1, ShipGene g2) {
        Random r = new Random(System.nanoTime());
        double rn = r.nextDouble();

        Gene[] childs1 = g1.breed(g2, rn);
        Gene[] childs2 = g2.breed(g1, rn);

        System.out.println("===============================================================");
        System.out.println(String.format("%s + %s * %s = %s | %s", g1, g2, rn, childs1[0], childs1[1]));
        System.out.println(String.format("%s + %s * %s = %s |  %s", g2, g1, rn, childs2[1], childs2[0]));
        System.out.println("===============================================================");

    }
}
