package fr.kahlouch.coding_game.games.code_buster.test;

import java.util.HashSet;
import java.util.Set;

public class Ghost extends Entity{
	public Ghost(final int id, final int x, final int y, final int state, final int value) {
		super(id, x, y, state, value);
	}

	public Set<Buster> getAlliesAiming(){
		final Set<Buster> busters = new HashSet<Buster>();
		for(final Buster buster : World.allies.values()){
			if(buster.getState() == Buster.State.AIMING && buster.value == this.id){
				busters.add(buster);
			}
		}
		return busters;
	}
}
