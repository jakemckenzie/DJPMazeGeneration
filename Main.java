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

public class Main extends JFrame implements KeyListener{
    public int[][] rgbMaze;
    public int[][] rgbMazeWithSolution;
    public int[][] rgbMazeToDisplay;
    public int MAZE_CONTROL_EVENT = 0;

    public Main(int[][] rgbMaze, int[][] rgbMazeWithSolution) {
        this.rgbMaze = rgbMaze;
        this.rgbMazeWithSolution = rgbMazeWithSolution;
        addKeyListener(this);
        rgbMazeToDisplay = rgbMaze;
        
        setTitle("DJP Maze w/ Solver Using Recursive Backtracking: Press Spacebar to Toggle Solution");
        setSize(10*rgbMaze[0].length + 20,10*rgbMaze.length+47);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE ) {
            rgbMazeToDisplay = (MAZE_CONTROL_EVENT++) % 2 == 0 ? rgbMazeWithSolution : rgbMaze;
            repaint();
            revalidate();
        } 
    }
    @Override
    public void keyReleased(KeyEvent e) {

    }
    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void paint(Graphics g) {
        JButton button = new JButton();
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
        Long startTime = System.currentTimeMillis();
		//Graph G1 = new Graph(4,4, true);
		//G1.print();
		Graph G2 = new Graph(50,80, false);
		G2.print();
		// Graph G3 = new Graph(25,25, false);
        // G3.print();
        // BufferedImage img = createImage(G2.rgbMaze);
        
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Main gui = new Main(G2.rgbMaze,G2.rgbMazeWithSolution);
                gui.setVisible(true);
            }
        });

        Long endTime = System.currentTimeMillis();
        System.out.println((endTime - startTime) + " miliseconds");
    }
    
    
}
