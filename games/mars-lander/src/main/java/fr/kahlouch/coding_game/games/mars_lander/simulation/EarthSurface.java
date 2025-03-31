package fr.kahlouch.coding_game.games.mars_lander.simulation;

public enum EarthSurface {
    FLAT("flat.txt"),
    START_RIGHT_SIDE("start_speed_right_side.txt"),
    START_WRONG_SIDE("start_speed_wrong_side.txt"),
    DEEP("deep.txt"),
    HIGH("high.txt"),
    GROTTE_RIGHT("grotte_right.txt"),
    GROTTE_LEFT("grotte_wrong.txt"),
    HARDCORE("hardcore.txt"),
    HARDEST("hardest.txt");

    private final String filename;

    EarthSurface(String filename) {
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }
}
