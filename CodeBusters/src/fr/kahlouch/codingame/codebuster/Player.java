package fr.kahlouch.codingame.codebuster;

import fr.kahlouch.codingame.codebuster.test.Buster;
import fr.kahlouch.codingame.codebuster.test.GameMaster;
import fr.kahlouch.codingame.codebuster.test.Ghost;
import fr.kahlouch.codingame.codebuster.test.World;

import java.util.Scanner;

/**
 * Send your busters out into the fog to trap ghosts and bring them home!
 **/
public class Player {

	public static void main(final String args[]) {
		final Scanner in = new Scanner(System.in);
		World.BUSTER_NB = in.nextInt(); // the amount of busters you control
		World.GHOST_NB = in.nextInt(); // the amount of ghosts on the map
		World.MY_TEAM = in.nextInt(); // if this is 0, your base is on the top left of the map, if it is one, on the bottom right
		if(World.MY_TEAM == 0){
			World.ALLY_HOME = World.TOP_LEFT;
			World.ENNEMY_HOME = World.BOTTOM_RIGHT;
		}else{
			World.ALLY_HOME = World.BOTTOM_RIGHT;
			World.ENNEMY_HOME = World.TOP_LEFT;
		}
		final GameMaster gameMaster = new GameMaster();
		// game loop 
		while (true) {
			++World.CURRENT_TIME;
			World.clearShowies();
			final int entities = in.nextInt(); // the number of busters and ghosts visible to you
			for (int i = 0; i < entities; i++) {
				final int entityId = in.nextInt(); // buster id or ghost id
				final int x = in.nextInt();
				final int y = in.nextInt(); // position of this buster / ghost
				final int entityType = in.nextInt(); // the team id if it is a buster, -1 if it is a ghost.
				final int state = in.nextInt(); // For busters: 0=idle, 1=carrying a ghost.
				final int value = in.nextInt(); // For busters: fr.kahlouch.code_buster.Ghost id being carried. For ghosts: number of busters attempting to trap this ghost.

				if(entityType == -1){
					Ghost ghost;
					if((ghost = World.ghosts.get(entityId)) == null){
						ghost = new Ghost(entityId, x, y, state, value);
						World.ghosts.put(entityId, ghost);
					}
					else{
						ghost.update(x, y, state, value);
					}
					World.showyGhosts.put(entityId, ghost);
					World.allSeenGhosts.add(entityId);
				}
				else if(entityType == World.MY_TEAM){
					Buster ally;
					if((ally = World.allies.get(entityId)) == null){
						ally = new Buster(entityId, x, y, state, value, Buster.Team.ALLY);
						World.allies.put(entityId , ally);
					}else{
						ally.update(x, y, state, value);
					}
				}else{
					Buster ennemy;
					if((ennemy = World.ennemies.get(entityId)) == null){
						ennemy = new Buster(entityId, x, y, state, value, Buster.Team.ENNEMY);
						World.ennemies.put(entityId , ennemy);
					}else{
						ennemy.update(x, y, state, value);
					}
					World.showyEnnemies.put(entityId, ennemy);
				}
			}
			gameMaster.analyseWorld();
			gameMaster.chooseActions();
			gameMaster.executeActions();
		}
	}
}