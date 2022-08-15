import java.util.*;
import java.util.Map.Entry;

public class GameMaster {
	public Map<Integer , Action> actionsMap;
	public int ghostCap = 0;

	public GameMaster(){
		this.actionsMap = new TreeMap<Integer, Action>();
	}

	public void analyseWorld(){
		final List<Ghost> toDelete = new ArrayList<Ghost>();
		for(final Ghost ghost : World.ghosts.values()){
			if(World.showyGhosts.containsKey(ghost.id))continue;
			for(final Buster buster : World.allies.values()){
				if(buster.coord.distance(ghost.coord) < World.SIGHT_RADIUS){
					toDelete.add(ghost);
				}
			}
		}
		for(final Ghost ghost : toDelete){
			World.ghosts.remove(ghost.id);
		}
	}

	public void chooseActions(){
		final Set<Buster> availableBusters = new HashSet<Buster>();
		for(final Buster buster : World.allies.values()){
			final Buster.State state = buster.getState();
			if(state == Buster.State.NEUTRAL){
				availableBusters.add(buster);
			}else{
				if(state == Buster.State.CARRY){
					if(buster.canRelease()){
						this.actionsMap.put(buster.id, release());
					}
					else{
						this.actionsMap.put(buster.id, goHome());
					}
				}
				else if(state == Buster.State.STUNNED){
					this.actionsMap.put(buster.id, stay(buster));
				}
				else if(state == Buster.State.AIMING){
					final Buster stunnableBuster = getCloserBusterStunnable(buster);
					if(buster.isWeaponLoaded() && stunnableBuster != null){
						this.actionsMap.put(buster.id, stunBuster(stunnableBuster));
					}else{
						final Ghost fastest = getFastestBustableGhost(buster);
						this.actionsMap.put(buster.id, bustGhost(fastest));
					}
				}
			}
		}

		for(final Ghost ghost : getInterestingGhosts(World.showyGhosts)){
			if(availableBusters.isEmpty()) break;
			int numberOfBuster = 0;
			for(final Action action : this.actionsMap.values()){
				if(action.actionType == Action.ActionType.BUST && action.parameters.equals(""+ghost.id)){
					++numberOfBuster;
				}
			}
			if(ghost.value == 0 || ghost.value >= 2 * numberOfBuster){
				final List<Buster> helping = new ArrayList<Buster>();
				int newB = 0;
				for(final Buster buster : availableBusters){
					if(ghost.value + newB < 2 * (numberOfBuster + newB))break;
					if(buster.isInRange(ghost)){
						this.actionsMap.put(buster.id, bustGhost(ghost));
					}else{
						this.actionsMap.put(buster.id, moveTo(ghost.coord));
					}
					helping.add(buster);
					++newB;
				}
				availableBusters.removeAll(helping);
			}
		}
		for(final Buster ennemy : World.showyEnnemies.values()){
			if(availableBusters.isEmpty()) break;
			if(ennemy.getState() == Buster.State.CARRY || ennemy.getState() == Buster.State.AIMING ){
				final List<Buster> stunner = new ArrayList<Buster>();
				for(final Buster buster : availableBusters){
					if(buster.isInRange(ennemy)){
						this.actionsMap.put(buster.id, stunBuster(ennemy));
						stunner.add(buster);
						break;
					}
				}
				availableBusters.removeAll(stunner);
			}
		}
		final Map<Integer, Ghost> map = new TreeMap<Integer, Ghost>();
		map.putAll(World.ghosts);
		for(final Integer ghostId : World.showyGhosts.keySet()){
			map.remove(ghostId);
		}
		for(final Ghost ghost : getInterestingGhosts(map)){
			if(availableBusters.isEmpty()) break;
			for(final Buster buster : availableBusters){
				this.actionsMap.put(buster.id, moveTo(ghost.coord));
				break;
			}
		}
		for(final Buster buster : availableBusters){
			this.actionsMap.put(buster.id, moveRandom(buster));
		}
	}

	public List<Ghost> getInterestingGhosts(final Map<Integer, Ghost> map){
		final List<Ghost> interesting = new ArrayList<Ghost>();
		for(final Ghost ghost : map.values()){
			if(ghost.state <= 3 && World.CURRENT_TIME >= World.TURN_BEFORE_3){
				interesting.add(ghost);
			}
			else if(ghost.state <= 15 && World.CURRENT_TIME >= World.TURN_BEFORE_15){
				interesting.add(ghost);
			}
			else if(ghost.state <= 40 && World.CURRENT_TIME >= World.TURN_BEFORE_40){
				interesting.add(ghost);
			}
		}
		return interesting;
	}
	public Ghost getFastestBustableGhost(final Buster buster){
		Ghost fastestBustable = null;
		for(final Ghost ghost : World.showyGhosts.values()){
			if(buster.isInRange(ghost) && (fastestBustable == null || fastestBustable.state > ghost.state)){
				fastestBustable = ghost;
			}
		}
		return fastestBustable;
	}

	public Buster getCloserBusterStunnable(final Buster buster){
		for(final Buster ennemy : World.showyEnnemies.values()){
			if(ennemy.isInRange(buster)){
				return ennemy;
			}
		}
		return null;
	}

	public void executeActions(){
		if(this.actionsMap.size() != World.BUSTER_NB){
			System.err.println("Tous les busters n'ont pas d'action attribu�e");
			return;
		}
		for(final Entry<Integer, Action> actionEntry : this.actionsMap.entrySet()){
			actionEntry.getValue().execute();
		}
		this.actionsMap.clear();
	}

	public Action bustGhost(final Ghost ghost){
		return new Action(Action.ActionType.BUST,""+ghost.id, "GHOST " + ghost.id);
	}

	public Action stunBuster(final Buster buster){
		return new Action(Action.ActionType.STUN,""+buster.id, "BUST " + buster.id);
	}

	public Action moveTo(final Coord coord, final String comment){
		return new Action(Action.ActionType.MOVE, coord.toIntString(), comment);
	}

	public Action moveTo(final Coord coord){
		return moveTo(coord, null);
	}

	public Action stay(final Buster buster){
		return moveTo(buster.coord);
	}

	public Action release(){
		return new Action(Action.ActionType.RELEASE, null, null);
	}

	public Action goHome(){
		return new Action(Action.ActionType.MOVE, World.ALLY_HOME.toIntString());
	}

	public Coord randomPointWithinTriangle(final Coord a, final Coord b, final Coord c){
		final double r1 = Math.random();
		final double r2 = Math.random();
		final Coord p = new Coord();

		p.x = (1 - Math.sqrt(r1)) * a.x + (Math.sqrt(r1) * (1 - r2)) * b.x + (Math.sqrt(r1) * r2) * c.x;
		p.y = (1 - Math.sqrt(r1)) * a.y + (Math.sqrt(r1) * (1 - r2)) * b.y + (Math.sqrt(r1) * r2) * c.y;

		return p;
	}

	public Action moveRandom(final Buster buster){
		if(buster.direction == null){
			buster.direction = randomEnnemySide();
		}
		return moveTo(buster.direction);
	}

	public Coord randomEnnemySide(){
		return randomPointWithinTriangle(World.BOTTOM_LEFT, World.TOP_RIGHT, World.ENNEMY_HOME);
	}

	public Coord randomAllySide(){
		return randomPointWithinTriangle(World.BOTTOM_LEFT, World.TOP_RIGHT, World.ALLY_HOME);
	}
}
