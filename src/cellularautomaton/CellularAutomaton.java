
package cellularautomaton;
 
import java.awt.*; //needed for graphics
import java.util.ArrayList;
import javax.swing.*; //needed for graphics
import static javax.swing.JFrame.EXIT_ON_CLOSE; //needed for graphics
import java.util.Random;
 
public class CellularAutomaton extends JFrame {
   
    Random r = new Random();
 
    //Sets up generation variables
    int numGenerations = 600;
    int currGeneration = 1;
    
    //Screen set up
    int width = 800;
    int height = 800;
    int borderWidth = 50;
    int numCellsX = 60;
    int numCellsY = 60;
    
    int cellWidth = (width - 2 * borderWidth)/numCellsX;
    int labelX = width / 2;
    int labelY = borderWidth;
 
    boolean cells[][] = new boolean [numCellsX][numCellsY]; //garbage or not garbage
    boolean cellsOnNext[][] = new boolean [numCellsX][numCellsY];
   
    int direction[][] = new int [numCellsX][numCellsY]; //sets direction as array of integers
   
    int colour[][] = new int[numCellsX][numCellsY]; //sets colours as integers
    int colourNext[][] = new int[numCellsX][numCellsY]; //sets colours as integers
   
    //Cell colors
    Color oceanColor = new Color (49, 111, 255);
    Color garbageColor = new Color (0, 0, 0);
    Color level1 = new Color(90, 135, 178);
    Color level2 = new Color(124, 133, 113);
    Color level3 = new Color(144, 144, 66);
    Color level4 = new Color(150, 118, 55);
   
   
    public void plantFirstGeneration() {
        
        getDirection();
        plantOceanCells();
        
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[0].length; j++) {
               
                //only plants garbage cells from the sides of screen
                if (i == 0 || j == 0 || i == numCellsY - 1 || j == numCellsX - 1) {
                    int garbageProb = r.nextInt(2); //probability of planting a garbage cell is 1/3
                    if (garbageProb == 1) {
                        cells[i][j] = true; //plants garbage
                        colour[i][j] = 1; //sets cell as garbage cell color
                    }
                   
                    else {
                        cells[i][j] = false; //plants unpolluted water cell
                        colour[i][j] = 0; //sets sell as unpolluted water color
                    }
                }
            }            
        }
    }
   
    public void plantOceanCells() {
        for (int i = 0; i < numCellsX; i++) {
            for (int j = 0; j < numCellsY; j++) {
                cells[i][j] = false;
                colour[i][j] = 0;
            }
        }
    }
    
    //Generate directions for each cell
    public void getDirection() {
        
        //up is 0, down is 1, right is 2, left is 3
        for (int i = 0; i < direction.length; i++) {
            for (int j = 0; j < direction.length; j++) {
                
                ArrayList<Integer> directionList = new ArrayList(); //sets up directions as a list
                
                if (i == 0) {
                    directionList.add(1); //goes down for all values in which y = 0
                    
                    if (j == 0) { //top left corner
                        directionList.add(2);
                        }
                    else if ( j == numCellsX - 1) { //top right corner
                        directionList.add(3);
                        }
                    else { //all other top cells
                        directionList.add(2);
                        directionList.add(3);
                        }
                    }
               
                else if ( i == numCellsY - 1) {
                    directionList.add(0); //goes up for all values in which y = number of cells
                    
                    if (j == 0) { //bottom left corner
                        directionList.add(2);
                        }
                   
                    else if ( j == numCellsX - 1 ) { //bottom right corner
                        directionList.add(3);
                        }
                    else { //all other bottom cells
                        directionList.add(2);
                        directionList.add(3);
                    }
                }
                
                else{
                    directionList.add(0);
                    directionList.add(1); //all cells (except top and bottom) at the side goes up and down
                    
                    if ( j == 0 ) { //all left cells
                        directionList.add(2);
                    }
 
                    else if (j == numCellsX - 1 ) { //all right cells
                        directionList.add(3);
                    }
                    
                    else { //the rest of the cells
                        if(i <= numCellsX/2){ //for the top half, increase the probability of going down
                            directionList.add(0);
                            directionList.add(2);
                            directionList.add(3);
                            directionList.add(1);
                            directionList.add(1);
                            directionList.add(1);
                            directionList.add(1);
                            directionList.add(1);
                        }
                        else if(i >= numCellsX/2){ //for the bottom half, increase the probability of going up
                            directionList.add(2);
                            directionList.add(3);
                            directionList.add(0);
                            directionList.add(0);
                            directionList.add(0);
                            directionList.add(0);
                            directionList.add(0);
                        }
                       
                        if(j <= numCellsX/2){ //for the left half, increase the probability of going right
                            directionList.add(2);
                            directionList.add(2);
                            directionList.add(2);
                            directionList.add(2);
                           
                        }
                        else if(j <= numCellsX/2){ //for the right half, increase the probability of going left
                            directionList.add(3);
                            directionList.add(3);
                            directionList.add(3);
                            directionList.add(3);                      
                        }
                    }
                }
               
                int index = r.nextInt(directionList.size());
                direction[i][j] = directionList.get(index); //adds direction to the direction array
               
            }
        }
    }
           
    public void computeNextGeneration() {
        for (int i = 0; i < cells.length; i++) {
                for (int j = 0; j < cells[0].length; j++) {
                    
                    if(cells[i][j]){ //checks for garbage cells                        
                        if(direction[i][j] == 0){ //direction = up
                            cellsOnNext[i-1][j] = cells[i][j];
                            colourNext[i][j] = colour[i][j] + 1;
                        }                
                        else if(direction[i][j] == 1){ //direction = down
                            cellsOnNext[i+1][j] = cells[i][j];
                            colourNext[i][j] = colour[i][j] + 1;
                        }      
                        else if(direction[i][j] == 2){ //direction = right
                            cellsOnNext[i][j+1] = cells[i][j];
                            colourNext[i][j] = colour[i][j] + 1;
                        }      
                        else if(direction[i][j] == 3){ //direction = left
                            cellsOnNext[i][j-1] = cells[i][j];
                            colourNext[i][j] = colour[i][j] + 1;
                        }
                    }                    
                   
                    if(!cellsOnNext[i][j]){ //if in the next generation, it is not a garbage cell
                        if (colourNext[i][j] == 0) {
                            cellsOnNext[i][j] = false; //sets cell into ocean cell
                        }
                        else {
                            colourNext[i][j] = colour[i][j] + 1;
                        }                                        
                    }
             }            
        }
        
        //decomposes garbage cells and pollution cells
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[0].length; j++) {
                int garbageProb = r.nextInt(300);
                if (garbageProb == 1){ //1/301 probability of deleting a garbage cell
                    colourNext[i][j] = 0;
                }
                
                int pollutionProb = r.nextInt(350);
                if(pollutionProb == 1){ //1/351 probability of deleting a garbage cell
                    colourNext[i][j] = 0;
                }
               
            }
            
        }
                
        //plants more garbage cells coming out the side
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[0].length; j++) {
 
                if (i == 0 || j == 0 || i == numCellsY - 1 || j == numCellsX - 1) {
                    int garbageProb = r.nextInt(2); //probability of planting a garbage cell is 1/3
                    if (garbageProb == 1) {
                        cellsOnNext[i][j] = true; //plants garbage
                        colour[i][j] = 1;                   
                    }
                }
            }            
        }
    }
   
    public void plantNextGeneration() {
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[0].length; j++) {
                cells[i][j] = cellsOnNext[i][j];
                colour[i][j] = colourNext[i][j];
                cellsOnNext[i][j] = false;
            }            
        }        
        currGeneration ++;
    }
   
     public void paint( Graphics g ) {
        int x, y, i, j;
       
        x = borderWidth;
        drawLabel(g, currGeneration);
        
        for (i = 0; i < cells.length; i++) {
            y = borderWidth;
           
            for (j = 0; j < cells[0].length; j++) {
               
                if(cells[i][j]){
                    g.setColor(garbageColor);    
                }
                
                else{                    
                    if (colour[i][j] == 2) {
                        g.setColor(level1);
                    }
                    else if (colour[i][j] == 3) {
                        g.setColor(level2);
                    }
                    else if (colour[i][j] == 4) {
                        g.setColor(level3);
                    }
                    else if (colour[i][j] >= 5) {
                        g.setColor(level4);
                    }
                    else {
                        g.setColor(oceanColor);
                    }
                }
               
                g.fillRect(x, y, cellWidth, cellWidth);
               
                g.setColor(Color.black);
                g.drawRect(x, y, cellWidth, cellWidth);
               
                y += cellWidth;
            }
           
            x += cellWidth;
        }
    }
     
    public static void sleep(int duration) {
        try {
            Thread.sleep(duration);
        }
        catch (Exception e) {}
    }
    
    void drawLabel(Graphics g, int state) {
        g.setColor(Color.black);
        g.fillRect(0, 0, width, borderWidth);
        g.setColor(Color.white);
        g.drawString("Generation: " + state, labelX, labelY);
    }
    
    public void initializeWindow() {
        setTitle("Garbage Patch Automaton");
        setSize(width, height);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);//calls paint() for the first time
    }
    
    public static void main(String[] args) {
        CellularAutomaton game = new CellularAutomaton();
        game.initializeWindow();
        game.plantFirstGeneration();

        for (int i = 0; i < game.numGenerations; i++) {
            if(i % 10 == 0){ //changes the direction every 10 generations to avoid situations in which garbage cells gets stuck in the current
                game.getDirection();
            }
            game.repaint();
            game.sleep(200);
            game.computeNextGeneration();
            game.plantNextGeneration();
        }
    }
}