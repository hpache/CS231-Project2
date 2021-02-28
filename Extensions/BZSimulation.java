public class BZSimulation {

    public static void main(String[] args) {

        if (args.length == 0){

            // Initializing grid and display
            Landscape grid = new Landscape(100, 100);
            LandscapeDisplay display1 = new LandscapeDisplay(grid, 8);

            // Simulating 50 iterations, a pattern should appear at the 20th iteration
            for (int i = 0; i < 50; i++){
                //display1.saveImage( "data/BZ_frame_" + String.format( "%03d", i ) + ".png" );
                grid.advance();
                display1.repaint();
                try {
                    Thread.sleep(250);
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
            Landscape grid = new Landscape(100, 100, alpha, beta, gamma);

            // Initializing display
            LandscapeDisplay display1 = new LandscapeDisplay(grid, 8);

            // Simulating 60 iterations, just in case some patterns take longer to appear
            for (int i = 0; i < 60; i++){
                //display1.saveImage( "data/BZ_frame_" + String.format( "%03d",i ) + ".png");
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
            System.out.println("Usage: java BZSimulation [alpha] [beta] [gamma]");
            System.out.println("Or");
            System.out.println("Usage: java BZSimulation");
        }

    }
    
}