package main.java.fr.kahlouch.marslander.path_render;

import fr.kahlouch.gameresources.graphics._2d.Graphics2D;
import fr.kahlouch.gameresources.input_handling.ICommand;
import fr.kahlouch.gameresources.pattern.game_loop.CatchUpGameLoop;
import fr.kahlouch.genetic.GeneticAlgorithm;
import fr.kahlouch.marslander.path_render.component.graphics.MarsGraphicsComponent;
import fr.kahlouch.marslander.path_render.controller.InputHandlerImpl;
import fr.kahlouch.marslander.path_render.model.DisplayState;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class Mars extends CatchUpGameLoop {
    private final DisplayState displayState;
    private final InputHandlerImpl inputHandler;
    private final Graphics2D graphics;
    private final MarsGraphicsComponent marsGraphicsComponent;

    public Mars(GeneticAlgorithm geneticAlgorithm, Stage stage) {
        super(60);
        this.displayState = new DisplayState(geneticAlgorithm);
        this.inputHandler = new InputHandlerImpl(displayState);
        this.graphics = Graphics2D.builder(stage)

                .build();
        this.graphics.show();
        this.marsGraphicsComponent = new MarsGraphicsComponent();
        stage.addEventHandler(KeyEvent.KEY_PRESSED, inputHandler::handleInput);
    }


    @Override
    protected void render() {
        graphics.startDraw();
        this.marsGraphicsComponent.render(this.displayState, this.graphics);
        graphics.endDraw();
    }

    @Override
    protected void update() {
    }

    @Override
    protected void processInput() {
        ICommand toExecute;
        while ((toExecute = this.inputHandler.nextCommand()) != null) {
            toExecute.execute();
        }
    }
}
