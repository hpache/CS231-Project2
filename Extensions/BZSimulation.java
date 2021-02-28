/**
 * Henry Pacheco Cachon
 * Project 2 Extension
 * Created 02/26/2021
 * This file runs our simulation of the BZ Reaction.
 * The reason why the window is smaller is purely to make the gifs smaller and easier to download
 */

public class BZSimulation {

    public static void main(String[] args) {

        if (args.length == 0){

            // Initializing grid and display
            Landscape grid = new Landscape(200, 200);
            LandscapeDisplay display1 = new LandscapeDisplay(grid, 4);

            // Simulating 200 iterations, a pattern should appear at the 20th iteration
            for (int i = 0; i < 200; i++){
                grid.advance();
                display1.repaint();
                try {
                    Thread.sleep(25);
                } 
                catch(InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
        }

        if (args.length == 3){

            // Parsing input parameters 
            float alpha = Float.parseFloat(args[0]);
            float beta = Float.parseFloat(args[1]);
            float gamma = Float.parseFloat(args[2]);

            // Initializing grid with given parameters
            Landscape grid = new Landscape(200, 200, alpha, beta, gamma);

            // Initializing display
            LandscapeDisplay display1 = new LandscapeDisplay(grid, 2);

            // Simulating 150 iterations, just in case some patterns take longer to appear
            for (int i = 0; i < 150; i++){
                display1.saveImage( "data/BZ_frame_" + String.format( "%03d",i ) + ".png");
                grid.advance();
                display1.repaint();
                try{
                    Thread.sleep(250);
                }
                catch(InterruptedException ex){
                    Thread.currentThread().interrupt();
                }
            }
        }

        if (args.length > 0 && args.length != 3){
            System.out.println("Usage: java BZSimulation [alpha] [beta] [gamma] to save files");
            System.out.println("Or");
            System.out.println("Usage: java BZSimulation to just run the simulation at default values" );
        }

    }
    
}