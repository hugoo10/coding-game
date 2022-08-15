package fr.kahlouch.marslander.model.factory;

import fr.kahlouch.genetic.factory.AbstractGeneFactory;
import fr.kahlouch.genetic.population.Gene;
import fr.kahlouch.genetic.utils.Constants;
import fr.kahlouch.marslander.model.ShipGene;
import fr.kahlouch.marslander.physics.Acceleration;

import java.util.HashMap;
import java.util.Map;

public class ShipGeneFactory extends AbstractGeneFactory {
    public static Map<String, ShipGene> ALL_EXISTING_GENES_MAP = new HashMap<>();

    static {
        ALL_EXISTING_GENES_MAP.put("-90_0", new ShipGene(-90, 0, new Acceleration(0.0, 0.0)));
        ALL_EXISTING_GENES_MAP.put("-75_0", new ShipGene(-75, 0, new Acceleration(0.0, 0.0)));
        ALL_EXISTING_GENES_MAP.put("-60_0", new ShipGene(-60, 0, new Acceleration(0.0, 0.0)));
        ALL_EXISTING_GENES_MAP.put("-45_0", new ShipGene(-45, 0, new Acceleration(0.0, 0.0)));
        ALL_EXISTING_GENES_MAP.put("-30_0", new ShipGene(-30, 0, new Acceleration(0.0, 0.0)));
        ALL_EXISTING_GENES_MAP.put("-15_0", new ShipGene(-15, 0, new Acceleration(0.0, 0.0)));
        ALL_EXISTING_GENES_MAP.put("0_0", new ShipGene(0, 0, new Acceleration(0.0, 0.0)));
        ALL_EXISTING_GENES_MAP.put("15_0", new ShipGene(15, 0, new Acceleration(-0.0, 0.0)));
        ALL_EXISTING_GENES_MAP.put("30_0", new ShipGene(30, 0, new Acceleration(-0.0, 0.0)));
        ALL_EXISTING_GENES_MAP.put("45_0", new ShipGene(45, 0, new Acceleration(-0.0, 0.0)));
        ALL_EXISTING_GENES_MAP.put("60_0", new ShipGene(60, 0, new Acceleration(-0.0, 0.0)));
        ALL_EXISTING_GENES_MAP.put("75_0", new ShipGene(75, 0, new Acceleration(-0.0, 0.0)));
        ALL_EXISTING_GENES_MAP.put("90_0", new ShipGene(90, 0, new Acceleration(-0.0, 0.0)));
        ALL_EXISTING_GENES_MAP.put("-90_1", new ShipGene(-90, 1, new Acceleration(1.0, 0.0)));
        ALL_EXISTING_GENES_MAP.put("-75_1", new ShipGene(-75, 1, new Acceleration(0.9659258262890683, 0.25881904510252074)));
        ALL_EXISTING_GENES_MAP.put("-60_1", new ShipGene(-60, 1, new Acceleration(0.8660254037844387, 0.49999999999999994)));
        ALL_EXISTING_GENES_MAP.put("-45_1", new ShipGene(-45, 1, new Acceleration(0.7071067811865476, 0.7071067811865475)));
        ALL_EXISTING_GENES_MAP.put("-30_1", new ShipGene(-30, 1, new Acceleration(0.5000000000000001, 0.8660254037844386)));
        ALL_EXISTING_GENES_MAP.put("-15_1", new ShipGene(-15, 1, new Acceleration(0.25881904510252074, 0.9659258262890683)));
        ALL_EXISTING_GENES_MAP.put("0_1", new ShipGene(0, 1, new Acceleration(6.123233995736766E-17, 1.0)));
        ALL_EXISTING_GENES_MAP.put("15_1", new ShipGene(15, 1, new Acceleration(-0.25881904510252085, 0.9659258262890683)));
        ALL_EXISTING_GENES_MAP.put("30_1", new ShipGene(30, 1, new Acceleration(-0.4999999999999998, 0.8660254037844387)));
        ALL_EXISTING_GENES_MAP.put("45_1", new ShipGene(45, 1, new Acceleration(-0.7071067811865475, 0.7071067811865476)));
        ALL_EXISTING_GENES_MAP.put("60_1", new ShipGene(60, 1, new Acceleration(-0.8660254037844387, 0.49999999999999994)));
        ALL_EXISTING_GENES_MAP.put("75_1", new ShipGene(75, 1, new Acceleration(-0.9659258262890682, 0.258819045102521)));
        ALL_EXISTING_GENES_MAP.put("90_1", new ShipGene(90, 1, new Acceleration(-1.0, 1.2246467991473532E-16)));
        ALL_EXISTING_GENES_MAP.put("-90_2", new ShipGene(-90, 2, new Acceleration(2.0, 0.0)));
        ALL_EXISTING_GENES_MAP.put("-75_2", new ShipGene(-75, 2, new Acceleration(1.9318516525781366, 0.5176380902050415)));
        ALL_EXISTING_GENES_MAP.put("-60_2", new ShipGene(-60, 2, new Acceleration(1.7320508075688774, 0.9999999999999999)));
        ALL_EXISTING_GENES_MAP.put("-45_2", new ShipGene(-45, 2, new Acceleration(1.4142135623730951, 1.414213562373095)));
        ALL_EXISTING_GENES_MAP.put("-30_2", new ShipGene(-30, 2, new Acceleration(1.0000000000000002, 1.7320508075688772)));
        ALL_EXISTING_GENES_MAP.put("-15_2", new ShipGene(-15, 2, new Acceleration(0.5176380902050415, 1.9318516525781366)));
        ALL_EXISTING_GENES_MAP.put("0_2", new ShipGene(0, 2, new Acceleration(1.2246467991473532E-16, 2.0)));
        ALL_EXISTING_GENES_MAP.put("15_2", new ShipGene(15, 2, new Acceleration(-0.5176380902050417, 1.9318516525781366)));
        ALL_EXISTING_GENES_MAP.put("30_2", new ShipGene(30, 2, new Acceleration(-0.9999999999999996, 1.7320508075688774)));
        ALL_EXISTING_GENES_MAP.put("45_2", new ShipGene(45, 2, new Acceleration(-1.414213562373095, 1.4142135623730951)));
        ALL_EXISTING_GENES_MAP.put("60_2", new ShipGene(60, 2, new Acceleration(-1.7320508075688774, 0.9999999999999999)));
        ALL_EXISTING_GENES_MAP.put("75_2", new ShipGene(75, 2, new Acceleration(-1.9318516525781364, 0.517638090205042)));
        ALL_EXISTING_GENES_MAP.put("90_2", new ShipGene(90, 2, new Acceleration(-2.0, 2.4492935982947064E-16)));
        ALL_EXISTING_GENES_MAP.put("-90_3", new ShipGene(-90, 3, new Acceleration(3.0, 0.0)));
        ALL_EXISTING_GENES_MAP.put("-75_3", new ShipGene(-75, 3, new Acceleration(2.897777478867205, 0.7764571353075622)));
        ALL_EXISTING_GENES_MAP.put("-60_3", new ShipGene(-60, 3, new Acceleration(2.598076211353316, 1.4999999999999998)));
        ALL_EXISTING_GENES_MAP.put("-45_3", new ShipGene(-45, 3, new Acceleration(2.121320343559643, 2.1213203435596424)));
        ALL_EXISTING_GENES_MAP.put("-30_3", new ShipGene(-30, 3, new Acceleration(1.5000000000000004, 2.598076211353316)));
        ALL_EXISTING_GENES_MAP.put("-15_3", new ShipGene(-15, 3, new Acceleration(0.7764571353075622, 2.897777478867205)));
        ALL_EXISTING_GENES_MAP.put("0_3", new ShipGene(0, 3, new Acceleration(1.8369701987210297E-16, 3.0)));
        ALL_EXISTING_GENES_MAP.put("15_3", new ShipGene(15, 3, new Acceleration(-0.7764571353075626, 2.897777478867205)));
        ALL_EXISTING_GENES_MAP.put("30_3", new ShipGene(30, 3, new Acceleration(-1.4999999999999993, 2.598076211353316)));
        ALL_EXISTING_GENES_MAP.put("45_3", new ShipGene(45, 3, new Acceleration(-2.1213203435596424, 2.121320343559643)));
        ALL_EXISTING_GENES_MAP.put("60_3", new ShipGene(60, 3, new Acceleration(-2.598076211353316, 1.4999999999999998)));
        ALL_EXISTING_GENES_MAP.put("75_3", new ShipGene(75, 3, new Acceleration(-2.8977774788672046, 0.7764571353075631)));
        ALL_EXISTING_GENES_MAP.put("90_3", new ShipGene(90, 3, new Acceleration(-3.0, 3.6739403974420594E-16)));
        ALL_EXISTING_GENES_MAP.put("-90_4", new ShipGene(-90, 4, new Acceleration(4.0, 0.0)));
        ALL_EXISTING_GENES_MAP.put("-75_4", new ShipGene(-75, 4, new Acceleration(3.8637033051562732, 1.035276180410083)));
        ALL_EXISTING_GENES_MAP.put("-60_4", new ShipGene(-60, 4, new Acceleration(3.464101615137755, 1.9999999999999998)));
        ALL_EXISTING_GENES_MAP.put("-45_4", new ShipGene(-45, 4, new Acceleration(2.8284271247461903, 2.82842712474619)));
        ALL_EXISTING_GENES_MAP.put("-30_4", new ShipGene(-30, 4, new Acceleration(2.0000000000000004, 3.4641016151377544)));
        ALL_EXISTING_GENES_MAP.put("-15_4", new ShipGene(-15, 4, new Acceleration(1.035276180410083, 3.8637033051562732)));
        ALL_EXISTING_GENES_MAP.put("0_4", new ShipGene(0, 4, new Acceleration(2.4492935982947064E-16, 4.0)));
        ALL_EXISTING_GENES_MAP.put("15_4", new ShipGene(15, 4, new Acceleration(-1.0352761804100834, 3.8637033051562732)));
        ALL_EXISTING_GENES_MAP.put("30_4", new ShipGene(30, 4, new Acceleration(-1.9999999999999991, 3.464101615137755)));
        ALL_EXISTING_GENES_MAP.put("45_4", new ShipGene(45, 4, new Acceleration(-2.82842712474619, 2.8284271247461903)));
        ALL_EXISTING_GENES_MAP.put("60_4", new ShipGene(60, 4, new Acceleration(-3.464101615137755, 1.9999999999999998)));
        ALL_EXISTING_GENES_MAP.put("75_4", new ShipGene(75, 4, new Acceleration(-3.863703305156273, 1.035276180410084)));
        ALL_EXISTING_GENES_MAP.put("90_4", new ShipGene(90, 4, new Acceleration(-4.0, 4.898587196589413E-16)));
    }

    public int convertAngleDoubleToInt(Double angle) {
        int angleInt = ((Long) Math.round(angle)).intValue();
        if (angleInt <= -90) return -90;
        if (angleInt >= 90) return 90;
        if (angleInt <= -75) {
            return Math.abs(-90 - angleInt) < Math.abs(-75 - angleInt) ? -90 : -75;
        } else if (angleInt <= -60) {
            return Math.abs(-75 - angleInt) < Math.abs(-60 - angleInt) ? -75 : -60;
        } else if (angleInt <= -45) {
            return Math.abs(-60 - angleInt) < Math.abs(-45 - angleInt) ? -60 : -45;
        } else if (angleInt <= -30) {
            return Math.abs(-45 - angleInt) < Math.abs(-30 - angleInt) ? -45 : -30;
        } else if (angleInt <= -15) {
            return Math.abs(-30 - angleInt) < Math.abs(-15 - angleInt) ? -30 : -15;
        } else if (angleInt <= 0) {
            return Math.abs(-15 - angleInt) < Math.abs(-angleInt) ? -15 : 0;
        } else if (angleInt <= 15) {
            return Math.abs(-angleInt) < Math.abs(15 - angleInt) ? 0 : 15;
        } else if (angleInt <= 30) {
            return Math.abs(15 - angleInt) < Math.abs(30 - angleInt) ? 15 : 30;
        } else if (angleInt <= 45) {
            return Math.abs(30 - angleInt) < Math.abs(45 - angleInt) ? 30 : 45;
        } else if (angleInt <= 60) {
            return Math.abs(45 - angleInt) < Math.abs(60 - angleInt) ? 45 : 60;
        } else if (angleInt <= 75) {
            return Math.abs(60 - angleInt) < Math.abs(75 - angleInt) ? 60 : 75;
        }
        return Math.abs(75 - angleInt) < Math.abs(90 - angleInt) ? 75 : 90;
    }

    public int convertPowerDoubleToInt(Double power) {
        int powerInt = ((Long) Math.round(power)).intValue();
        if (powerInt <= 0) return 0;
        return Math.min(powerInt, 4);
    }

    public static ShipGene getGeneByAngleAndPower(int angle, int power) {
        return new ShipGene(angle, power);
    }

    public static ShipGene getGeneByAngleAndPowerFromMap(int angle, int power) {
        return ALL_EXISTING_GENES_MAP.get(angle + "_" + power);
    }

    @Override
    public ShipGene create(Object... params) {
        final Double angle = (double) params[0];
        final Double power = (double) params[1];
        return getGeneByAngleAndPowerFromMap(convertAngleDoubleToInt(angle), convertPowerDoubleToInt(power));
    }

    @Override
    public ShipGene createRandom() {
        return create((double) Constants.RANDOM_GEN.nextInt(181) - 90, (double) Constants.RANDOM_GEN.nextInt(5));
    }

    @Override
    public Gene createFromGaussian(Gene gene, double gaussian) {
        ShipGene shipGene = (ShipGene) gene;
        double gaussianArranged = gaussian * 2 - 1;
        return create(gaussianArranged * 90 + shipGene.getAngle(), gaussianArranged * 2 + shipGene.getPower());
    }
}
