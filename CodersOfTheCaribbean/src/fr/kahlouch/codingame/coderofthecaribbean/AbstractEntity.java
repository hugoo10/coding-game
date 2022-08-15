package fr.kahlouch.codingame.coderofthecaribbean;

/**
 * Created by hugo on 17/04/2017.
 */
public class AbstractEntity {
    protected Position position;

    public AbstractEntity(Position position) {
        this.position = position;
    }

    public void update(int x, int y, int...args) {
        this.position.setX(x);
        this.position.setY(y);
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
