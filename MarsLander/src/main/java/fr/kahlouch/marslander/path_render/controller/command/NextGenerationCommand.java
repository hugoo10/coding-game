package fr.kahlouch.marslander.path_render.controller.command;

import fr.kahlouch.gameresources.input_handling.ICommand;
import fr.kahlouch.marslander.path_render.model.DisplayState;

public class NextGenerationCommand implements ICommand {
    private final DisplayState displayState;

    public NextGenerationCommand(DisplayState displayState) {
        this.displayState = displayState;
    }

    @Override
    public void execute() {
        this.displayState.nextGeneration();
    }

    @Override
    public void undo() {

    }
}
