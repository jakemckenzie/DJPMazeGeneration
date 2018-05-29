import javax.imageio.*;
//import java.awt.Image;
import java.awt.*;
import java.io.*;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.util.Scanner;

/**
 * @author Jake McKenzie
 * 
 * This assignment was worked on alone by Jake McKenzie.
 * 
 * ***All extra credit was completed. PLEASE ENTER IN DIMENSIONS YOU WANT THE GUI TO BE.***  
 */

public class Main extends JFrame implements KeyListener{
    /**
     * @param rgbMaze pixels for blank maze
     */
    public int[][] rgbMaze;
    /**
     * @param rgbMazeWithSolution pixels for solved maze
     */
    public int[][] rgbMazeWithSolution;
    /**
     * @param rgbMazeToDisplay pixels actually printed to GUI, used for control flow
     */
    public int[][] rgbMazeToDisplay;
    /**
     * @param MAZE_CONTROL_EVENT number of space bar presses for GUI
     */
    public int MAZE_CONTROL_EVENT = 0;
    

    public Main(int[][] rgbMaze, int[][] rgbMazeWithSolution) {
        this.rgbMaze = rgbMaze;
        this.rgbMazeWithSolution = rgbMazeWithSolution;
        addKeyListener(this);
        rgbMazeToDisplay = rgbMaze;
        
        setTitle("DJP generated Maze solved w/ Recursive Backtracking: Press Spacebar to Toggle Solution");
        setSize(10*rgbMaze[0].length + 20,10*rgbMaze.length+47);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    /**
     * @param e key event for control flow, if key pressed it repaints maze
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE ) {
            rgbMazeToDisplay = (MAZE_CONTROL_EVENT++) % 2 == 0 ? rgbMazeWithSolution : rgbMaze;
            repaint();
            revalidate();
        }
    }
    /**
     * trash method needed for GUI to work properly
     */
    @Override
    public void keyReleased(KeyEvent e) {

    }
    /**
     * trash method needed for GUI to work properly
     */
    @Override
    public void keyTyped(KeyEvent e) {
        
    }
    /**
     * @param g used to determine pixel density and dimensions
     * 
     * There isn't very good documentation on how to do this and this
     * was very finicky to actually create.
     */

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        rgbMazeToDisplay[rgbMazeToDisplay.length - 1][rgbMazeToDisplay[0].length - 1] = 0x0;
        for (int y = 0; y < rgbMazeToDisplay.length; y++) {
            for (int x = 0; x < rgbMazeToDisplay[0].length; x++) {
                Color colour = new Color(rgbMazeToDisplay[y][x]);
                g.setColor(colour);
                g.fillRect(10*x+10,10*y+37,20,11);
                g.setColor(Color.BLACK);
                g.drawRect(10*x+10,10*y+37,10,11);
            }
        }
    }

	public static void main(String[] args) {
        int MAZE_WIDTH = 1;
        int MAZE_HEIGHT = 1;
        Scanner keyboard = new Scanner(System.in);
        while (true) {
            System.out.print("Please enter a maze width between 4 to 90 blocks: ");
            MAZE_WIDTH = keyboard.nextInt();
            if (MAZE_WIDTH >= 4 && MAZE_WIDTH <= 95) break;
        }
        while (true) {
            System.out.print("Please enter a maze height between 4 to 50 blocks: ");
            MAZE_HEIGHT = keyboard.nextInt();
            if (MAZE_HEIGHT >= 4 && MAZE_HEIGHT <= 50) break;
        }
        
        Long startTime = System.currentTimeMillis();
		
		Graph G = new Graph(MAZE_HEIGHT,MAZE_WIDTH, false);
		G.print();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Main gui = new Main(G.rgbMaze,G.rgbMazeWithSolution);
                gui.setVisible(true);
            }
        });

        Long endTime = System.currentTimeMillis();
        System.out.println((endTime - startTime) + " miliseconds");

        //testGraph();
        //testLattice();
    }

    /**
     * Prints a graph with dugger on. Due to how the GUI works the
     * terminal print is now less readable but works as it should.
     * Each broken wall prints a V in its place.
     */
    public static void testGraph() {
        Graph test = new Graph(4,4,true);
    }
    /**
     * Tests the dimensions of the lattice.
     */
    public static void testLattice() {
        Graph testLatticeOddEven;
        for (int i = 5; i < 22; i+=2) {
            for (int j = 4; j < 30; j+=2) {
                System.out.println(i + " by " + j);
                testLatticeOddEven = new Graph(i,j,false);
                testLatticeOddEven.print();
            }
        }
        Graph testLatticeEvenOdd;
        for (int m = 4; m < 30; m+=2) {
            for (int n = 5; n < 4; n+=2) {
                System.out.println(m + " by " + n);
                testLatticeEvenOdd = new Graph(m,n,false);
                testLatticeEvenOdd.print();
            }
        }
    }
    
}
