package fr.kahlouch.coding_game.games.mars_lander.path_render;


import fr.kahlouch.coding_game.games.mars_lander.model.Ship;
import fr.kahlouch.coding_game.games.mars_lander.model.ShipGene;
import fr.kahlouch.coding_game.games.mars_lander.model.ShipPath;
import fr.kahlouch.coding_game.games.mars_lander.path_render.component.graphics.MarsGraphicsComponent;
import fr.kahlouch.coding_game.games.mars_lander.path_render.controller.InputHandlerImpl;
import fr.kahlouch.coding_game.games.mars_lander.path_render.model.DisplayState;
import fr.kahlouch.gameresources.graphics._2d.Graphics2D;
import fr.kahlouch.gameresources.input_handling.ICommand;
import fr.kahlouch.gameresources.pattern.game_loop.CatchUpGameLoop;
import fr.kahlouch.genetic.algorithm.execution.listener.ExecutionListener;
import fr.kahlouch.genetic.algorithm.vo.Population;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class Mars extends CatchUpGameLoop implements ExecutionListener<ShipGene, Ship, ShipPath> {
    private final DisplayState displayState;
    private final InputHandlerImpl inputHandler;
    private final Graphics2D graphics;
    private final MarsGraphicsComponent marsGraphicsComponent;

    public Mars(Stage stage) {
        super(60);
        this.displayState = new DisplayState();
        this.inputHandler = new InputHandlerImpl(displayState);
        this.graphics = Graphics2D.builder(stage)

                .build();
        this.graphics.show();
        this.marsGraphicsComponent = new MarsGraphicsComponent();
        stage.addEventHandler(KeyEvent.KEY_PRESSED, inputHandler::handleInput);
        stage.setMinWidth(1400);
        stage.setMinHeight(800);
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

    @Override
    public void send(Population<ShipGene, Ship, ShipPath> population) {
        this.displayState.send(population);
    }

    @Override
    public void sendEndSignal() {
        this.displayState.sendEndSignal();
    }
}
