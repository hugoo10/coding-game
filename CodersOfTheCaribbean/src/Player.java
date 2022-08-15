import java.util.Scanner;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class Player {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);

        // game loop
        while (true) {
            int myShipCount = in.nextInt(); // the number of remaining ships
            int entityCount = in.nextInt(); // the number of entities (e.g. ships, mines or cannonballs)
            for (int i = 0; i < entityCount; i++) {
                int entityId = in.nextInt();
                String entityType = in.next();
                int x = in.nextInt();
                int y = in.nextInt();
                int arg1 = in.nextInt();
                int arg2 = in.nextInt();
                int arg3 = in.nextInt();
                int arg4 = in.nextInt();

                AbstractEntity entity = GameMap.entities.get(entityId);
                if(entity == null) {
                    entity = GameMap.newEntity(entityType, x, y, arg1, arg2, arg3, arg4);
                    GameMap.entities.put(entityId, entity);
                    if(entity instanceof Ship) {
                        if(arg4 == 1) {
                            GameMap.teamShip.put(entityId, (Ship) entity);
                        } else {
                            GameMap.adverse.put(entityId, (Ship) entity);
                        }
                    }
                    else if(entity instanceof Barrel) {
                        GameMap.barrels.put(entityId, (Barrel) entity);
                    }
                    else if(entity instanceof Mine) {
                        GameMap.mines.put(entityId, (Mine) entity);
                    }
                    else if(entity instanceof CanonBall) {
                        GameMap.canonBall.put(entityId, (CanonBall) entity);
                    }
                } else {
                    if(entity instanceof Ship) {
                        GameMap.updateBarrels(entity.getPosition(), ((Ship) entity).getOrientation());
                    }
                    entity.update(x, y, arg1, arg2, arg3, arg4);
                }
            }
            for (Ship ship : GameMap.teamShip.values()) {
                GameMap.move(ship);
            }
        }
    }
}
