/**
 * Created by hugo on 17/04/2017.
 */
public enum Moves {
    MOVE, SLOWER, WAIT, FIRE, MINE;

    public void makeMove(int x, int y) {
        System.out.println(this.name() + " " + x + " " + y);
    }

    public void makeMove(Position position) {
        makeMove(position.getX(), position.getY());
    }

    public void makeMove() {
        System.out.println(this.name());
    }
}
