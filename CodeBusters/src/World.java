import java.util.*;

public class World {
	public static int BUSTER_NB;
	public static int GHOST_NB;
	public static int MY_TEAM;

	//MAP DETAILS
	public static Coord TOP_LEFT = new Coord(0, 0);
	public static Coord TOP_RIGHT = new Coord(16000, 0);
	public static Coord BOTTOM_LEFT = new Coord(0, 9000);
	public static Coord BOTTOM_RIGHT = new Coord(16000, 9000);
	public static Coord CENTER = new Coord(8000, 4500);
	public static Coord ALLY_HOME;
	public static Coord ENNEMY_HOME;

	//IMPORTANT DISTANCES
	public static int MAX_RELEASE_DIST = 1600;
	public static int MIN_BUST_DIST = 400;
	public static int MAX_BUST_DIST = 1760;
	public static int MAX_MOVE_DIST = 800;
	public static int MAX_STUN_DIST = 1760;
	public static int SIGHT_RADIUS = 2200;

	//ALL ENTITIES
	public static Map<Integer, Buster> allies = new TreeMap<Integer, Buster>();
	public static Map<Integer, Buster> ennemies = new TreeMap<Integer, Buster>();
	public static Map<Integer, Ghost> ghosts = new TreeMap<Integer, Ghost>();
	public static Map<Integer, Buster> showyEnnemies = new TreeMap<Integer, Buster>();
	public static Map<Integer, Ghost> showyGhosts = new TreeMap<Integer, Ghost>();
	public static Set<Coord> probable = new HashSet<Coord>();
	public static Set<Integer> allSeenGhosts = new TreeSet<Integer>();

	//TIME
	public static int CURRENT_TIME = 0;
	public static int TURN_NB = 250;
	public static int RELOAD_DURATION = 20;
	public static int STUN_DURATION = 10;
	public static int TURN_BEFORE_3 = 0;
	public static int TURN_BEFORE_15 = 10;
	public static int TURN_BEFORE_40 = 100;

	//MY VALUES
	public static int TOO_CLOSE_DIST = 2000;
	public static Map<Integer, Coord> interestPoints = new TreeMap<Integer, Coord>();

	public static void clearShowies(){
		showyEnnemies.clear();
		showyGhosts.clear();
	}
}
