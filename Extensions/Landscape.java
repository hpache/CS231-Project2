/**
 * Henry Pacheco Cachon
 * Project 2 Extension
 * Created 02/26/2021
 * This file is a modified version of the original Landscape class
 * I modified it so that it can work properly with the TuringCell calss
 */

import java.util.ArrayList;
 import java.awt.Graphics;

// This class represents a landscape and hold multiple cell objects in a uniform grid
public class Landscape {

    // Declaring 2d array that will hold our cells
    private TuringCell[][] grid;
    // Declaring column and row fields
    private int numberRows;
    private int numberColumns;

    // Constructor, allocates a 2d array with given row and column values then it fills
    // the 2d array with cell objects with default reaction parameters
    public Landscape(int rows, int cols){

        // Initializing row and column fields
        this.numberRows = rows;
        this.numberColumns = cols;

        // Allocating 2d array with given dimensions
        this.grid = new TuringCell[this.numberRows][this.numberColumns];

        // Fill our 2d array with cells
        for (int i = 0; i < numberRows; i++){
            for (int j = 0; j < numberColumns; j++){

                // Create new cell object 
                TuringCell cellElement = new TuringCell();

                // ijth element of grid 2d array is assigned a cell object
                this.grid[i][j] = cellElement;
            }
        }
    }

    // Constructor allocates a 2d array with given row and column dimensions and then
    // It fills the grid with cell objects with given reaction parameters
    public Landscape(int rows, int cols, float alpha, float beta, float gamma){

        // Initializing row and column fields
        this.numberRows = rows;
        this.numberColumns = cols;
        
        // Allocating 2d array with given dimensions
        this.grid = new TuringCell[this.numberRows][this.numberColumns];

        // Fills grid with cells
        for (int i = 0; i < numberRows; i++){
            for (int j = 0; j < numberColumns; j++){

                // Creating new cell object
                TuringCell cellElement = new TuringCell();
                
                // Giving cellElement given reaction parameters
                cellElement.setAlpha(alpha);
                cellElement.setBeta(beta);
                cellElement.setGamma(gamma);

                // ijth element of grid is assigned a cell object
                this.grid[i][j] = cellElement;
            }
        }
    }

    // Method resets all the cells in our grid
    public void reset(){

        // Iterating over the elements of the grid array
        for (int i = 0; i < this.numberRows; i++){
            for (int j = 0; j < this.numberColumns; j++){

                // picking a cell element at the ijth index
                TuringCell cellElement = this.grid[i][j];

                // Resetting that element
                cellElement.reset();
            }
        }
    }

    // Method returns the number of rows
    public int getRows(){
        return this.numberRows;
    }

    // Method returns the number of columns
    public int getCols(){
        return this.numberColumns;
    }
    
    // Method returns the cell at position [row][col]
    public TuringCell getCell(int row, int col){
        return this.grid[row][col];
    }

    // Method returns an array list of the neighbors of the cell at the [row][col] position.
    // This method will use the Moore neighborhood in preparation for my extension :D
    // Also in preparation for my extension, I will treat the grid as a warped grid
    public ArrayList<TuringCell> getNeighbors(int row, int col){
        
        // Initializing neighbors arraylist
        ArrayList<TuringCell> neighbors = new ArrayList<TuringCell>();

        // Getting the rows and columns that contain the neighbors
        int topRow = row - 1;
        int lowerRow = row + 1;
        int initialColumn = col - 1; 
        int finalColumn = col + 1;

        // Iterate over the neighbors on the top row
        for (int j = initialColumn; j <= finalColumn; j++){

            // Implement grid warping by using mods!
            int warpedI = (topRow + this.numberRows) % this.numberRows;
            int warpedJ = (j + this.numberColumns) % this.numberColumns;
            
            // Picking neighbor cell
            TuringCell neighbor = this.grid[warpedI][warpedJ];

            // Adding neighbor to the arraylist
            neighbors.add(neighbor);
        }

        // Iterate over the neighbors on the same row 
        for (int j = initialColumn; j <= finalColumn; j++){

            // check if j is the same as col so that we don't get the center cell
            if (j != col){

                // Grid warping
                int warpedI = (row + this.numberRows) % this.numberRows;
                int warpedJ = (j + this.numberColumns) % this.numberColumns;

                // Picking the cell neighbor
                TuringCell neighbor = this.grid[warpedI][warpedJ];

                // Adding neighbor to arraylist
                neighbors.add(neighbor);
            }
        }

        // Iterate over the neighbors in the bottom row
        for (int j = initialColumn; j <= finalColumn; j++){

            // Grid warp
            int warpedI = (lowerRow + this.numberRows) % this.numberRows;
            int warpedJ = (j + this.numberColumns) % this.numberColumns;

            // Picking cell neighbor
            TuringCell neighbor = this.grid[warpedI][warpedJ];

            // Adding neighbor to arraylist 
            neighbors.add(neighbor);

        }

        return neighbors;
    }

    // This method loops through all of the cells in our grid and draws them
    public void draw( Graphics g, int gridScale){

        // Iterating over all the grid elements
        for (int i = 0; i < this.numberRows; i++){
            for (int j = 0; j < this.numberColumns; j++){

                // Picking out our ijth cell
                TuringCell currentCell = this.grid[i][j];
                
                // Drawing our ijth cell
                currentCell.draw(g, i * gridScale, j * gridScale, gridScale);
            }
        }
    }

    // This method advances the game by running the update method on each cell in our grid
    public void advance(){

        // Make a clone of the grid array so we can work on it
        TuringCell[][] gridClone = new TuringCell[this.numberRows][this.numberColumns];

        // Iterate over the original grid in order to copy its ijth element
        // Once we do that, we get the ijth's cell neighbor list and update it appropriately
        // Once we update the temporary cell, we assign it to the clone array 
        for (int i = 0; i < this.numberRows; i++){
            for (int j = 0; j < this.numberColumns; j++){

                // Create a new cell object
                TuringCell tempCell = new TuringCell();
                // Get ijth cell from the original grid
                TuringCell originalCell = this.grid[i][j];

                // Get original concentrations
                ArrayList<Float> originalConcentrations = originalCell.getConcentrations();

                // Set the cells status equal to the one in the ijth position of the original grid
                tempCell.setConcentrations(originalConcentrations.get(0), originalConcentrations.get(1), originalConcentrations.get(2));

                // Giving the temporary cell the same reaction parameters as the original cell
                tempCell.setAlpha( originalCell.getAlpha() );
                tempCell.setBeta( originalCell.getBeta() );
                tempCell.setGamma( originalCell.getGamma() );

                // Assigning tempCell to the ijth element in the clone array
                gridClone[i][j] = tempCell;

                // Get the neighbors of the ijth cell in the original grid
                ArrayList<TuringCell> neighbors = this.getNeighbors(i, j);

                // Update the ijth tempCell with the neighbors
                tempCell.updateState(neighbors); 
            }
        }

        // Once we are done updating the cells we just set this.grid equal to the clone array
        this.grid = gridClone;
    }

    // Method formats our grid object in a nice string
    public String toString(){

        // Initializing output string
        String output = "";

        // Iterating over 2d array
        for (int i = 0; i < this.numberRows; i++){
            for (int j = 0; j < this.numberColumns; j++){
                output += this.grid[i][j] + " ";
            }
            output += "\n";
        }

        return output;
    }

    // Unit Test!!
    public static void main(String[] args) {
        
        Landscape grid1 = new Landscape(5, 5);

        // toString method won't look nice because of the tuples
        System.out.println(grid1);

        // Picking inidivual cell to focus on
        TuringCell cell1 = grid1.getCell(2, 3);
        System.out.println(cell1);

        // Getting neighbors of cell1
        ArrayList<TuringCell> neighbors = grid1.getNeighbors(2, 3);
        System.out.println(neighbors);

        // Advance the grid
        grid1.advance();
        
        // Print out cell1 now
        cell1 = grid1.getCell(2, 3);
        System.out.println(cell1);

        // See the current landscape
        LandscapeDisplay display1 = new LandscapeDisplay(grid1, 8);
    }
}