package fr.kahlouch.codingame.marslander.path_render.controller.command;

import fr.kahlouch.codingame.marslander.path_render.model.DisplayState;
import fr.kahlouch.gameresources.input_handling.ICommand;

public class FirstGenerationCommand implements ICommand {
    private final DisplayState displayState;

    public FirstGenerationCommand(DisplayState displayState) {
        this.displayState = displayState;
    }

    @Override
    public void execute() {
        this.displayState.firstGeneration();
    }

    @Override
    public void undo() {

    }
}
