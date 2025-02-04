package fr.kahlouch.coding_game.games.coder_of_the_caribbean;

/**
 * Created by hugo on 17/04/2017.
 */
public enum Orientation {
    EAST(0), NORTH_EAST(1), NORTH_WEST(2), WEST(3), SOUTH_WEST(4), SOUTH_EAST(5);

    private int value;
    Orientation(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
