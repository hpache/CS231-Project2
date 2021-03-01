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
public class TuringCell {

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
    // Initialzing limits for our constrain method
    private float maxConcentration = 0.8f;
    private float minConcentration = 0;

    // Constructor method initializes chemical fields with random values as a default
    public TuringCell(){

        // Initializing chemicals with random floats from 0 to 0.8
        this.concentrationA = randomGenerator.nextFloat();
        this.concentrationB = randomGenerator.nextFloat();
        this.concentrationC = randomGenerator.nextFloat();

        // Reaction parameters set to 1 by default
        this.alpha = 1;
        this.beta = 1;
        this.gamma = 1;
        
    }

    // Constructor method initializes chemical fields with given values.
    // Values must be floats between 0 and 0.8
    public TuringCell(float a, float b, float c){

        // If one of the concentrations is greater than 0.8 or less than 0 then
        // concentrations will be constrained!
        if ((a > 1 || b > 1 || c > 1) || (a < 0 || b < 0 || c < 0)){
            
            // constrain values that are not between 0 and 1
            a = this.limit(a);
            b = this.limit(b);
            c = this.limit(c);

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

    // This method resets all the concentrations to random floats from 0 to 0.8
    public void reset(){
        this.concentrationA = randomGenerator.nextFloat();
        this.concentrationB = randomGenerator.nextFloat();
        this.concentrationC = randomGenerator.nextFloat();
    }

    // This method returns a constrained value if it is not between 0 and 0.8.
    // value < 0 returns 0
    // value > 0.8 returns 0.8
    private float limit(float value){

        // Constrain using min and max methods
        value = Math.min( Math.max(value, this.minConcentration), this.maxConcentration);

        return value;
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
    // Must be a float with value between 0 and 0.8
    public void setConcentrations(float a, float b, float c){

        // If one of the concentrations is not between 0 or 0.8 then constrain it
        if ((a > 1 || b > 1 || c > 1) || (a < 0 || b < 0 || c < 0)){
            
            // Constraining chemicals not between 0 or 1
            a = this.limit(a);
            b = this.limit(b);
            c = this.limit(c);

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

    // This method draws the cell. The color of the cell is set
    // Using the HSB model which is why concentrations must be floats between 0 and 0.8
    // Currently the color changes as a function of concentration A, but this 
    // can be changed to any concentration.
    public void draw(Graphics g, int x, int y, int scale){

        // Draw an oval representing a cell at position x,y, scaled by scale
        g.drawRect(x, y, scale, scale);

        // Setting the color in accordance to concentration A
        // Using HSB model of color
        Color cellColor = Color.getHSBColor(1.0f, 0.9f, this.concentrationA);
        g.setColor(cellColor);

        // Fill oval object at x,y with current collor 
        g.fillRect(x, y, scale, scale);
    }

    // This method returns an array with  the average concentration
    // of the neighboring cells including the current cell
    private float[] average(ArrayList<TuringCell> neighbors){

        // Initializing variables for the total concentration of each chemical
        float totalConcentrationA = 0;
        float totalConcentrationB = 0;
        float totalConcentrationC = 0;

        // Iterating over the neighbors
        for (int i = 0; i < neighbors.size(); i++){

            // Get the cell from the neighbors
            TuringCell currentCell = neighbors.get(i);

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

        // constrain the averages
        averageConcentrationA = this.limit(averageConcentrationA);
        averageConcentrationB = this.limit(averageConcentrationB);
        averageConcentrationC = this.limit(averageConcentrationC);

        // Allocating array with average concentrations
        float[] averages = {averageConcentrationA, averageConcentrationB, averageConcentrationC};

        return averages;
    }

    // This method calculates new concentrations based on the
    // BZ reaction. It then outputs the new concentrations in an array of the form {A,B,C}.
    // The input is a neighbors ArrayList since it calls on the average method.
    private float[] BZ(ArrayList<TuringCell> neighbors){

        // Finds average concentrations by calling on the average method
        float[] concentrations = this.average(neighbors);

        // Getting concentrations from the output of the average method
        float A = concentrations[0];
        float B = concentrations[1];
        float C = concentrations[2];

        // Calculating new concentrations 
        float newA = A + A * (this.alpha * B - this.gamma * C);
        float newB = B + B * (this.beta * C - this.alpha * A);
        float newC = C + C * (this.gamma * A - this.beta * B);

        // Allocating array with new concentrations
        float[] newConcentrations = {newA, newB, newC};

        return newConcentrations;
    }

    // This method updates the state of the turing cell. 
    public void updateState(ArrayList<TuringCell> neighbors){

        // Calculating new concentrations based on the neighbors
        float[] newConcentration = this.BZ(neighbors);

        // Assign calculated concentrations to cell's concentration
        this.concentrationA = newConcentration[0];
        this.concentrationB = newConcentration[1];
        this.concentrationC = newConcentration[2];
    }

    public String toString(){

        // The cell will be represented in string format as the concentration of chemical A
        String output = "%s";
        output = String.format(output, this.concentrationA);

        return output;
    }

    // Unit test
    public static void main(String[] args) {
        
        // Cell with random concentrations between 0 and 1
        TuringCell cell1 = new TuringCell();
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
        TuringCell cell2 = new TuringCell(0.1f, 0.5f, 1f);
        // Output should be (0.1, 0.5, 1.0)
        System.out.println(cell2);

        // Cell with given concentrations with invalid values
        TuringCell cell3 = new TuringCell(0.1f,10f,-20f);
        // Output should be (0.1, 1.0, 0.0)
        System.out.println(cell3);
    }
}