package fr.kahlouch.codingame.codebuster.test;

public class Buster extends Entity{
	public Team team;
	public int reloadTime = 0;
	public Coord direction;
	public Ghost aimedGhost;

	public Buster(final int id, final int x, final int y, final int state, final int value, final Team team) {
		super(id, x, y, state, value);
		this.team = team;
		this.direction = null;
		this.aimedGhost = null;
	}

	public State getState(){
		if(state == 0){
			return State.NEUTRAL;
		}
		else if(state == 1){
			return State.CARRY;
		}
		else if(state == 2){
			return State.STUNNED;
		}
		else if(state == 3){
			return State.AIMING;
		}
		return null;
	}

	@Override
	public void updateHookPoint(){
		if(this.reloadTime > 0){
			--this.reloadTime;
		}
		if(this.direction != null && this.direction.distance(this.coord) < 100){
			this.direction = null;
		}
		if(getState() == State.CARRY){
			World.ghosts.remove(this.value);
		}
		if(this.aimedGhost != null){
			if(World.ghosts.containsKey(this.aimedGhost.id)){
				if(this.coord.distance(this.aimedGhost.coord) < World.SIGHT_RADIUS && !World.showyGhosts.containsKey(this.aimedGhost.id)){
					World.ghosts.remove(this.aimedGhost.id);
					this.aimedGhost = null;	
				}
			}else{
				this.aimedGhost = null;
			}
		}
	}

	public enum Team{
		ALLY, ENNEMY;
	}

	public enum State{
		NEUTRAL, CARRY, STUNNED, AIMING;
	}

	public boolean isWeaponLoaded(){
		return this.reloadTime == 0;
	}

	public boolean isSameTeam(final Buster buster){
		return buster.team == this.team;
	}
	public boolean isInRange(final Buster buster){
		return this.coord.distance(buster.coord) <= World.MAX_STUN_DIST;
	}

	public boolean canStun(final Buster buster){
		return getState() != State.STUNNED &&!isSameTeam(buster) && isWeaponLoaded() && isInRange(buster);
	}

	public boolean isInRange(final Ghost ghost){
		final double distance = this.coord.distance(ghost.coord);
		return World.MIN_BUST_DIST <= distance && distance <= World.MAX_BUST_DIST;
	}

	public boolean canBust(final Ghost ghost){
		final State state = getState();
		return state != State.STUNNED && isInRange(ghost);
	}

	public boolean canRelease(){
		return getState() == State.CARRY && this.coord.distance(World.ALLY_HOME) <= World.MAX_RELEASE_DIST;
	}

	public boolean isCloseToAdverseHome(){
		if(this.team == Team.ENNEMY){
			return this.coord.distance(World.ALLY_HOME) <= World.TOO_CLOSE_DIST;
		}else{
			return this.coord.distance(World.ENNEMY_HOME) <= World.TOO_CLOSE_DIST;
		}
	}
}
