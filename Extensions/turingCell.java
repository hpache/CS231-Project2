/**
 * Henry Pacheco Cachon
 * Project 2 Extension
 * Created 02/26/2021
 * This file is for a cell object to be used in continuous CA
 * The cell will have 3 values containing information of chemical concentrations
 * The cell will be updated following the BZ reactions as a model for Turing Pattern formation
 */

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

// This class is going to represent a turing cell. Basically contious information of
// the concentration of chemicals A,B,C. To be used for continuous CA
public class turingCell {

    //Declaring chemical fields
    private float concentrationA;
    private float concentrationB;
    private float concentrationC;
    // Declaring reaction parameters
    private float alpha;
    private float beta;
    private float gamma;
    //Initializing random generator
    private Random randomGenerator = new Random();

    // Constructor method initializes chemical fields with random values as a default
    public turingCell(){

        // Initializing chemicals with random floats from 0 to 1
        this.concentrationA = randomGenerator.nextFloat();
        this.concentrationB = randomGenerator.nextFloat();
        this.concentrationC = randomGenerator.nextFloat();

        // Reaction parameters set to 1 by default
        this.alpha = 1;
        this.beta = 1;
        this.gamma = 1;
        
    }

    // Constructor method initializes chemical field with given values
    // Values must be from 0 to 1
    public turingCell(float a, float b, float c){

        // If one of the concentrations is greater than 1 or less than 0 then
        // concentrations will be constrained!
        if ((a > 1 || b > 1 || c > 1) || (a < 0 || b < 0 || c < 0)){
            
            // Constraining mechanism 
            // If a is < 0, then it will be constrained to 0
            // If a > 1, then it will be constrained to 1
            a = Math.min( Math.max(a,0f), 1f);
            b = Math.min( Math.max(b,0f), 1f);
            c = Math.min( Math.max(c,0f), 1f); 

            // Initialize chemicals with given parameters
            this.concentrationA = a;
            this.concentrationB = b;
            this.concentrationC = c;
        }
        else{

            // Initializing chemicals with given parameters
            this.concentrationA = a;
            this.concentrationB = b;
            this.concentrationC = c;
        }

        // Reaction parameters set to 1 by default
        this.alpha = 1;
        this.beta = 1;
        this.gamma = 1;
    }

    // This method resets all the concentrations to random floats from 0 to 1
    public void reset(){
        this.concentrationA = randomGenerator.nextFloat();
        this.concentrationB = randomGenerator.nextFloat();
        this.concentrationC = randomGenerator.nextFloat();
    }

    // This method returns the concentration of chemicals a,b,c in the cell as an array list
    // The returned arraylist is of the form [concentrationA, concentrationB, concentrationC]
    public ArrayList<Float> getConcentrations(){
        
        // Allocating concentration ArrayList
        ArrayList<Float> concentrations = new ArrayList<Float>();

        // Assigning concentrations to ArrayList indices
        concentrations.add(this.concentrationA);
        concentrations.add(this.concentrationB);
        concentrations.add(this.concentrationC);

        return concentrations;
    }

    // This method returns alpha
    public float getAlpha(){
        return this.alpha;
    }
    
    // This method returns beta
    public float getBeta(){
        return this.beta;
    }

    // This method returns gamma
    public float getGamma(){
        return this.gamma;
    }

    // This method sets the concentrations of the chemicals
    // Must be a float with value between 0 and 1
    public void setConcentrations(float a, float b, float c){

        // If one of the concentrations is not between 0 or 1 then constrain it
        if ((a > 1 || b > 1 || c > 1) || (a < 0 || b < 0 || c < 0)){
            
            // Constraining chemicals not between 0 or 1
            a = Math.min( Math.max(a,0f), 1f);
            b = Math.min( Math.max(b,0f), 1f);
            c = Math.min( Math.max(c,0f), 1f); 

            // Setting concentrations to given parameters
            this.concentrationA = a;
            this.concentrationB = b;
            this.concentrationC = c;
        }
        else{

            // Setting concentrations to given parameters 
            this.concentrationA = a;
            this.concentrationB = b;
            this.concentrationC = c;
        }

    }

    // This method sets the alpha parameter
    public void setAlpha(float a){
        this.alpha = a;
    }

    // This method sets the beta parameter
    public void setBeta(float b){
        this.beta = b;
    }

    // This method sets the gamma parameter
    public void setGamma(float g){
        this.gamma = g;
    }

    // This method draws the cell in accordance with its concentration of chemical A (could be any though)
    public void draw(Graphics g, int x, int y, int scale){

        // Draw an oval representing a cell at position x,y, scaled by scale
        g.drawOval(x, y, scale, scale);

        // Setting the color in accordance to concentration a
        // Using HSB model of color
        Color cellColor = Color.getHSBColor(0f, 0f, this.concentrationA);
        g.setColor(cellColor);

        // Fill oval object at x,y with current collor 
        g.fillOval(x, y, scale, scale);
    }

    // This method updates the state of the turing cell!
    public void updateState(ArrayList<turingCell> neighbors){

        // Initializing variables for the total concentration of all the cells
        // Calculation involves the current concentration of the cell as well!
        float totalConcentrationA = this.concentrationA;
        float totalConcentrationB = this.concentrationB;
        float totalConcentrationC = this.concentrationC;

        // Iterating over the neighbors
        for (int i = 0; i < neighbors.size(); i++){

            // Get the cell from the neighbors
            turingCell currentCell = neighbors.get(i);

            // Get concentration from the currentCell
            ArrayList<Float> cellConcentration = currentCell.getConcentrations();

            // Adding concentrations to appropiate variables
            totalConcentrationA += cellConcentration.get(0);
            totalConcentrationB += cellConcentration.get(1);
            totalConcentrationC += cellConcentration.get(2);
        }

        // Average concentrations over 9 cells
        float averageConcentrationA = totalConcentrationA / 9;
        float averageConcentrationB = totalConcentrationB / 9;
        float averageConcentrationC = totalConcentrationC / 9;

        // Constrain the concentrations
        averageConcentrationA = Math.min( Math.max(averageConcentrationA,0), 1);
        averageConcentrationB = Math.min( Math.max(averageConcentrationB,0), 1);
        averageConcentrationC = Math.min( Math.max(averageConcentrationC,0), 1);

        // Apply BZ reaction model to the average concentrations
        float newConcentrationA = averageConcentrationA + averageConcentrationA * (this.alpha*averageConcentrationB - this.gamma*averageConcentrationC);
        float newConcentrationB = averageConcentrationB + averageConcentrationB * (this.beta*averageConcentrationC - this.alpha*averageConcentrationA);
        float newConcentrationC = averageConcentrationC + averageConcentrationC * (this.gamma*averageConcentrationA - this.beta*averageConcentrationB);

        // Assign calculated concentrations to cells concentration
        this.concentrationA = newConcentrationA;
        this.concentrationB = newConcentrationB;
        this.concentrationC = newConcentrationC;
    }

    public String toString(){

        // The cell will be represented in string format as a tuple containing 
        // concentrations of chemical A, B, C in that order
        String output = "(%s, %s, %s)";
        output = String.format(output, this.concentrationA, this.concentrationB, this.concentrationC);

        return output;
    }

    // Unit test
    public static void main(String[] args) {
        
        // Cell with random concentrations between 0 and 1
        turingCell cell1 = new turingCell();
        // ArrayList containing cell's concentration
        ArrayList<Float> cell1Concentrations = cell1.getConcentrations();

        // Checking that getConcentrations() returns chemicals in the correct order
        System.out.println(cell1Concentrations);
        System.out.println(cell1);

        // Setting cell1 concentrations with invalid values to confirm that the
        // constaining mechanims works. Output should be (1, 0, 1)
        cell1.setConcentrations(2f, -1f, 1.1f);
        System.out.println(cell1);

        // Cell with given concentrations all valid values
        turingCell cell2 = new turingCell(0.1f, 0.5f, 1f);
        // Output should be (0.1, 0.5, 1.0)
        System.out.println(cell2);

        // Cell with given concentrations with invalid values
        turingCell cell3 = new turingCell(0.1f,10f,-20f);
        // Output should be (0.1, 1.0, 0.0)
        System.out.println(cell3);
    }
    
}