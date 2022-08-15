
public abstract class Entity {
	public int id;
	public Coord coord;
	public int state;
	public int value;
	public int updateTime;

	public Entity(final int id, final int x, final int y, final int state, final int value){
		this.id = id;
		this.coord = new Coord(x, y);
		this.state = state;
		this.value = value;
		this.updateTime = World.CURRENT_TIME;
	}

	public void update(final int x, final int y, final int state, final int value){
		this.coord.x = x;
		this.coord.y = y;
		this.state = state;
		this.value = value;
		this.updateTime = World.CURRENT_TIME;
		updateHookPoint();
	}

	public void updateHookPoint() {}
}
