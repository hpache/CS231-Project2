/**
 * Henry Pacheco Cachon
 * Project 2
 * Created 02/25/2021
 * The purpose of this file is to make a class that represents a cell in our grid
 */

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

// This class represents a cell object in our grid
public class Cell {

    // Declaring the boolean that has the life state of the cell
    private Boolean state;

    // Constructor, this initializes a cell object with default state as dead (false)
    public Cell(){
        this.state = false;
    }

    // Constructor that initializies cell with state alive
    public Cell(boolean alive){
        this.state = alive;
    }

    // This method returns the state of the cell
    // true -> alive and false -> dead
    public boolean getAlive(){
        return this.state;
    }

    // This method sets the state of the cell as the given input parameter
    public void setAlive(boolean alive){
        this.state = alive;
    }

    // This method resets the state of the cell to the default state (dead)
    public void reset(){
        this.state = false;
    }

    // This method draws a cell object
    public void draw(Graphics g, int x, int y, int scale){
        
        // Draw rectangle representing cell at position x,y
        g.drawRect(x,y,scale,scale);
        // If the cell is alive the color will be darkGray
        if (this.state){
            g.setColor(Color.black);
        }
        else{
            g.setColor(Color.white);
        }

        // Fill the oval at x,y with our current color
        g.fillRect(x, y, scale, scale);

    }

    // This method updates the status of the cell depending on the status of its neighbors
    public void updateState(ArrayList<Cell> neighbors){

        // If this cell is alive do the following
        if (this.state){

            // Initializing counting variable.
            int alive = 0;

            // Iterating over every neighbor and checking the status
            for(int i = 0; i < neighbors.size(); i++){

                // Get a neighbor cell
                Cell neighborCell = neighbors.get(i);

                // If the neighbor cell is alive, then add one to the alive count
                if (neighborCell.getAlive()){
                    alive += 1;
                }
            }

            // If the alive count is not equal to 2 or 3, kill the cell
            if (alive >= 2 && alive <= 3){
                this.state = true;
            }
            else{
                this.state = false;
            }
        }
        
        // If the cell is not alive do the following
        else{

            // Initialzing counting variable
            int alive = 0;

            // Iterating over every neighbor and checking their status
            for (int i = 0; i < neighbors.size(); i++){

                // Get neighbor cell
                Cell neighborCell = neighbors.get(i);

                // If the neighbor cell is alive then add one to the count
                if (neighborCell.getAlive()){
                    alive += 1;
                }
            }

            // If the alive count is exactly 3 then revive the cell
            if (alive == 3){
                this.state = true;
            }
        }
    }

    // This method overrides the default toString() method and returns information
    // of the cell's current state
    public String toString(){

        // If cell is alive return 1
        if (this.state){
            return 1 + "";
        }

        // If the cell is dead return 0
        else{
            return 0 + "";
        }
    }

    // Unit test!
    public static void main(String[] args) {

        // Initializing cell object with default state
        Cell cell1 = new Cell();
        // Initialzing cell object with true as a state
        Cell cell2 = new Cell(true);

        // Testing the getAlive method for cell1, must return false
        System.out.println(cell1.getAlive());

        //Setting the state for cell1 to alive and printing it, must return true!
        cell1.setAlive(true);
        System.out.println(cell1.getAlive());

        // Testing toString method for both cells, must get 1 for both
        System.out.println(cell1);
        System.out.println(cell2);

        // Reseting both cells and getting status, both must be false
        cell1.reset();
        cell2.reset();

        System.out.println(cell1.getAlive());
        System.out.println(cell2.getAlive());
    }
} 