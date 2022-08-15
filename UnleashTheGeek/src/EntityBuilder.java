public class EntityBuilder {
    private int id;
    private int type;
    private World world;

    public EntityBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public EntityBuilder setType(int type) {
        this.type = type;
        return this;
    }

    public EntityBuilder setWorld(World world) {
        this.world = world;
        return this;
    }

    public Entity build() {
        final Entity entity;
        switch (this.type) {
            case 0:
                entity = new AllyRobot();
                break;
            case 1:
                entity = new EnemyRobot();
                break;
            case 2:
                entity = new Radar();
                break;
            case 3:
                entity = new Trap();
                break;
            default:
                entity = null;
        }
        entity.setId(this.id);
        entity.setWorld(this.world);
        return entity;
    }
}
