/**
 * Henry Pacheco Cachon
 * Project 2 
 * Created: 02/25/2021
 * This file simulates the Game of Life 
 */

import java.util.Random;

// Class simulates the game of life using the Landscape and LandscapeDisplay classes.
public class LifeSimulation {

    public static void main(String[] args) {

        // Initializing our 100 x 100 grid
        Landscape scape = new Landscape(100,100);

        // Initialize random generator and density 
        Random randomGenerator = new Random();
        double density = 0.3;

        // initialize the grid to be 30% alive
        for (int i = 0; i < scape.getRows(); i++) {
            for (int j = 0; j < scape.getCols(); j++ ) { 
                scape.getCell( i, j ).setAlive( randomGenerator.nextDouble() <= density );
            }
        }

        LandscapeDisplay display1 = new LandscapeDisplay(scape, 8);

        // Simulating 1000 generations
        for (int i = 0; i < 300; i++){
            display1.saveImage( "data/life_frame_" + String.format( "%03d", i ) + ".png" );
            scape.advance();
            display1.repaint();
            try {
                Thread.sleep(25);
            } catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }
    
}
