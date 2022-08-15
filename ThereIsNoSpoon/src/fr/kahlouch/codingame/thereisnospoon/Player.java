package fr.kahlouch.codingame.thereisnospoon;

import java.util.Scanner;

/**
 * Don't let the machines win. You are humanity's last hope...
 **/
public class Player {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int width = in.nextInt(); // the number of cells on the X axis
        int height = in.nextInt(); // the number of cells on the Y axis
        in.nextLine();
        int tab[][] = new int[width][height];
        for (int i = 0; i < height; i++) {
            String line = in.nextLine(); // width characters, each either 0 or .
            System.err.println(line);
            for(int j = 0; j < width; ++j){
                tab[j][i] = line.substring(j, j+1).equals("0")?0:-1;
            }
        }

        // Write an action using System.out.println()
        // To debug: System.err.println("Debug messages...");
        for(int i = 0; i < width;++i){
        	for(int j = 0; j< height; ++j){
        		if(tab[i][j]==0){
        			int rightX = -1;
        			int rightY = -1;
        			int bottomX = -1; 
        			int bottomY = -1;
        			
        			for(int k = i+1; k < width; ++k){
        			    if(tab[k][j] == 0){
        			        rightX = k;
        			        rightY = j;
        			        break;
        			    }
        			}
        			for(int k = j+1; k < height; ++k){
        			    if(tab[i][k] == 0){
        			        bottomX = i;
        			        bottomY = k;
        			        break;
        			    }
        			}
        			System.out.println(i + " " + j + " " + rightX + " " + rightY + " " + bottomX + " " + bottomY);
        		}
        	}
        }
    }
}