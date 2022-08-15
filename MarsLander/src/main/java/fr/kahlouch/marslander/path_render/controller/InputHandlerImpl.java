package fr.kahlouch.marslander.path_render.controller;

import fr.kahlouch.gameresources.input_handling.ICommand;
import fr.kahlouch.gameresources.input_handling.InputHandler;
import fr.kahlouch.marslander.path_render.controller.command.*;
import fr.kahlouch.marslander.path_render.model.DisplayState;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyEvent;

public class InputHandlerImpl extends InputHandler {
    private final DisplayState viewer;

    public InputHandlerImpl(DisplayState viewer) {
        this.viewer = viewer;
    }

    protected ICommand convertInputToCommand(InputEvent inputEvent) {
        ICommand command = null;
        if (inputEvent instanceof KeyEvent keyEvent) {
            command = switch (keyEvent.getCode()) {
                case RIGHT -> new NextGenerationCommand(this.viewer);
                case LEFT -> new PreviousGenerationCommand(this.viewer);
                case B -> new ToggleBestCommand(this.viewer);
                case END -> new LastGenerationCommand(this.viewer);
                case HOME -> new FirstGenerationCommand(this.viewer);
                default -> null;
            };
        }
        return command;
    }
}
