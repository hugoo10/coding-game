package fr.kahlouch.coding_game.games.skynet;

import java.util.*;
import java.util.Map.Entry;

public class Player {

    public static Map<Integer, Set<Integer>> way1 = new TreeMap<Integer, Set<Integer>>();
    public static Set<Integer> gateways = new TreeSet<Integer>();

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int N = in.nextInt(); // the total number of nodes in the level, including the gateways
        int L = in.nextInt(); // the number of links
        int E = in.nextInt(); // the number of exit gateways

        for (int i = 0; i < L; i++) {
            int N1 = in.nextInt(); // N1 and N2 defines a link between these nodes
            int N2 = in.nextInt();
            if(!way1.containsKey(N1)){
                way1.put(N1, new TreeSet<Integer>());
            }
            way1.get(N1).add(N2);
            if(!way1.containsKey(N1)){
                way1.put(N1, new TreeSet<Integer>());
            }
            way1.get(N1).add(N2);
            if(!way1.containsKey(N2)){
                way1.put(N2, new TreeSet<Integer>());
            }
            way1.get(N2).add(N1);

            System.err.println("Message: " + N1 + " :: " + N2);
        }
        for (int i = 0; i < E; i++) {
            int EI = in.nextInt(); // the index of a gateway node
            gateways.add(EI);
            System.err.println("Message1: " + EI);
        }

        // game loop
        while (true) {
            int SI = in.nextInt(); // The index of the node on which the Skynet agent is positioned this turn
            System.err.println("Message2: " + SI);
            // Write an action using System.out.println()
            // To debug: System.err.println("Debug messages...");

            Entry<Integer, Integer> entry = getShortestPathToWP(SI).get(0);
            way1.get(entry.getKey()).remove(entry.getValue());
            if(way1.get(entry.getKey()).isEmpty()) way1.remove(entry.getKey());

            way1.get(entry.getValue()).remove(entry.getKey());
            if(way1.get(entry.getValue()).isEmpty()) way1.remove(entry.getValue());
            // Example: 0 1 are the indices of the nodes you wish to sever the link between
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
    }

    public static List<Entry<Integer, Integer>> getShortestPathToWP(int position){
        List<Entry<Integer, Integer>> list = new ArrayList<Entry<Integer, Integer>>();
        //int shortest = -1;

        Set<Integer> agentNext =  way1.get(position);
        if(agentNext.size() == 1){
            list.add(new AbstractMap.SimpleEntry<Integer,Integer>(position, agentNext.iterator().next()));
            return list;
        }


        for(Integer wp : gateways){
            if(agentNext.contains(wp)){
                list.add(new AbstractMap.SimpleEntry<Integer,Integer>(position, wp));
                return list;
            }
        }

        for(Integer wp : gateways){
            for(Integer r : (way1.get(wp)==null?new TreeSet<Integer>():way1.get(wp))){
                if(agentNext.contains(r)){
                    list.add(new AbstractMap.SimpleEntry<Integer,Integer>(position, r));
                    return list;
                }
            }
        }


        list.add(new AbstractMap.SimpleEntry<Integer,Integer>(position, agentNext.iterator().next()));
        return list;
    }
}