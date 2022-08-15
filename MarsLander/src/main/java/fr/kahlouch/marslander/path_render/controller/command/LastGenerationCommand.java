package main.java.fr.kahlouch.marslander.path_render.controller.command;

import fr.kahlouch.gameresources.input_handling.ICommand;
import fr.kahlouch.marslander.path_render.model.DisplayState;

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
