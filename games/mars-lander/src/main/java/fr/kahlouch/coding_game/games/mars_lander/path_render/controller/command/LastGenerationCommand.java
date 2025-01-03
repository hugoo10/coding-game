package fr.kahlouch.coding_game.games.mars_lander.path_render.controller.command;

import fr.kahlouch.codingame.marslander.path_render.model.DisplayState;
import fr.kahlouch.gameresources.input_handling.ICommand;

public class LastGenerationCommand implements ICommand {
    private final DisplayState displayState;

    public LastGenerationCommand(DisplayState displayState) {
        this.displayState = displayState;
    }

    @Override
    public void execute() {
        this.displayState.lastGeneration();
    }

    @Override
    public void undo() {

    }
}
