/**
 * Hery Pacheco Cachon 
 * Project 2
 * Create 02/25/2021
 * This file is meant to represent a landscape/grid which holds our cell objects
 */

 import java.util.ArrayList;
 import java.awt.Graphics;

// This class represents a landscape and hold multiple cell objects in a uniform grid
public class Landscape {

    // Declaring 2d array that will hold our cells
    private Cell[][] grid;
    // Declaring column and row fields
    private int numberRows;
    private int numberColumns;

    // Constructor, allocates a 2d array with given row and column values then it fills
    // the 2d array with cell objects
    public Landscape(int rows, int cols){

        // Initializing row and column fields
        this.numberRows = rows;
        this.numberColumns = cols;

        // Allocating 2d array with given dimensions
        this.grid = new Cell[this.numberRows][this.numberColumns];

        // Fill our 2d array with cells
        for (int i = 0; i < numberRows; i++){
            for (int j = 0; j < numberColumns; j++){

                // Create new cell object 
                Cell cellElement = new Cell();

                // ijth element of grid 2d array is assigned a cell object
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
                Cell cellElement = this.grid[i][j];

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
    public Cell getCell(int row, int col){
        return this.grid[row][col];
    }

    // Method returns an array list of the neighbors of the cell at the [row][col] position.
    // This method will use the Moore neighborhood in preparation for my extension :D
    // Also in preparation for my extension, I will treat the grid as a warped grid
    public ArrayList<Cell> getNeighbors(int row, int col){
        
        // Initializing neighbors arraylist
        ArrayList<Cell> neighbors = new ArrayList<Cell>();

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
            Cell neighbor = this.grid[warpedI][warpedJ];

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
                Cell neighbor = this.grid[warpedI][warpedJ];

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
            Cell neighbor = this.grid[warpedI][warpedJ];

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
                Cell currentCell = this.grid[i][j];
                
                // Drawing our ijth cell
                currentCell.draw(g, i * gridScale, j * gridScale, gridScale);
            }
        }
    }

    // This method advances the game by running the update method on each cell in our grid
    public void advance(){

        // Make a clone of the grid array so we can work on it
        Cell[][] gridClone = new Cell[this.numberRows][this.numberColumns];

        // Iterate over the original grid in order to copy its ijth element
        // Once we do that, we get the ijth's cell neighbor list and update it appropriately
        // Once we update the temporary cell, we assign it to the clone array 
        for (int i = 0; i < this.numberRows; i++){
            for (int j = 0; j < this.numberColumns; j++){

                // Create a new cell object
                Cell tempCell = new Cell();
                // Get ijth cell from the original grid
                Cell originalCell = this.grid[i][j];

                // Set the cells status equal to the one in the ijth position of the original grid
                tempCell.setAlive(originalCell.getAlive());

                // Assigning tempCell to the ijth element in the clone array
                gridClone[i][j] = tempCell;

                // Get the neighbors of the ijth cell in the original grid
                ArrayList<Cell> neighbors = this.getNeighbors(i, j);

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

    // Unit test!
    public static void main(String[] args) {
        
        // Setting up a 4x4 grid since it is easier to check
        Landscape test1 = new Landscape(4,4);
        
        // Grid should all be 0's
        System.out.println(test1);

        // Get list of neighbors of cell at 0,3
        ArrayList<Cell> neighborsTest = test1.getNeighbors(0, 3);

        // Setting those neighbors to 1s to check if they correctly warped
        for (int i = 0; i < neighborsTest.size(); i++){
            neighborsTest.get(i).setAlive(true);
        }

        // See bottom for correct configuration!
        System.out.println(test1);

        // Checking if the update method works, we should get all 0s
        test1.advance();
        System.out.println(test1);
        test1.advance();
        System.out.println(test1);

        // Draw Landscape. Look below for a reference
        LandscapeDisplay display1 = new LandscapeDisplay(test1,20);
    }

 
}

// Initial state should be

/*
    0 0 0 0 
    0 0 0 0
    0 0 0 0 
    0 0 0 0
*/

// State after neighbor check and switch should look like the following

/*
    1 0 1 0
    1 0 1 1
    0 0 0 0
    1 0 1 1
*/

// The display output should be upside down So here is a key of the mirrored version
// Only if you comment out test1.advance(); If you didn't they should be 0s!

/*
    1 1 0 1
    0 0 0 0
    1 1 0 1
    0 1 0 1 
*/