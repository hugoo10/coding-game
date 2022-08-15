import java.util.*;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
public class Player {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int nbFloors = in.nextInt(); // number of floors
        int width = in.nextInt(); // width of the area
        int nbRounds = in.nextInt(); // maximum number of rounds
        int exitFloor = in.nextInt(); // floor on which the exit is found
        int exitPos = in.nextInt(); // position of the exit on its floor
        int nbTotalClones = in.nextInt(); // number of generated clones
        int nbAdditionalElevators = in.nextInt(); // ignore (always zero)
        int nbElevators = in.nextInt(); // number of elevators
        
        Map<Integer, Set<Integer>> elevators = new TreeMap<Integer, Set<Integer>>();
        for (int i = 0; i < nbElevators; i++) {
            int elevatorFloor = in.nextInt(); // floor on which this elevator is found
            int elevatorPos = in.nextInt(); // position of the elevator on its floor
            if(!elevators.containsKey(elevatorFloor)){
            	elevators.put(elevatorFloor, new TreeSet<Integer>());
            }
            elevators.get(elevatorFloor).add(elevatorPos);
        }

        // game loop
        while (true) {
            int cloneFloor = in.nextInt(); // floor of the leading clone
            int clonePos = in.nextInt(); // position of the leading clone on its floor
            String direction = in.next(); // direction of the leading clone: LEFT or RIGHT
            
            System.err.println("Message: " + cloneFloor + " :: " + exitFloor + " :: " + direction);
            if(direction.equalsIgnoreCase("LEFT")){
            	if(exitFloor == cloneFloor){
            		if(exitPos > clonePos){
            			System.out.println("BLOCK");
            		}else{
            			System.out.println("WAIT");
            		}
            	}
            	else{
	            	Set<Integer> elevatorPos = elevators.get(cloneFloor);
	            	String a = "WAIT";
	            	for(Integer pos : elevatorPos){
	            		if(pos > clonePos){
	            			a = "BLOCK";
	            			break;
	            		}
	            	}
	            	System.out.println(a);
            	}
            }
            else if(direction.equalsIgnoreCase("RIGHT")){
            	if(exitFloor == cloneFloor){
            		if(exitPos < clonePos){
            			System.out.println("BLOCK");
            		}else{
            			System.out.println("WAIT");
            		}
            	}
            	else{
	            	Set<Integer> elevatorPos = elevators.get(cloneFloor);
	            	String a = "WAIT";
	            	for(Integer pos : elevatorPos){
	            		if(pos < clonePos){
	            			a = "BLOCK";
	            			break;
	            		}
	            	}
	            	System.out.println(a);
            	}
            }else{
                System.out.println("WAIT");
            }
        }
    }
    
}