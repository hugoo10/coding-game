package fr.kahlouch.coding_game.games.mars_lander.path_render.controller.command;


import fr.kahlouch.coding_game.games.mars_lander.path_render.model.DisplayState;
import fr.kahlouch.gameresources.input_handling.ICommand;

public class PreviousGenerationCommand implements ICommand {
    private final DisplayState displayState;

    public PreviousGenerationCommand(DisplayState displayState) {
        this.displayState = displayState;
    }

    @Override
    public void execute() {
        this.displayState.previousGeneration();
    }

    @Override
    public void undo() {

    }
}
