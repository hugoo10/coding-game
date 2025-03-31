package fr.kahlouch.coding_game.games.mars_lander.path_render.controller.command;


import fr.kahlouch.coding_game.games.mars_lander.path_render.model.DisplayState;
import fr.kahlouch.gameresources.input_handling.ICommand;

public class FirstGenerationCommand implements ICommand {
    private final DisplayState displayState;

    public FirstGenerationCommand(DisplayState displayState) {
        this.displayState = displayState;
    }

    @Override
    public void execute() {
        this.displayState.firstPopulation();
    }

    @Override
    public void undo() {

    }
}
