import javax.imageio.*;
//import java.awt.Image;
import java.awt.*;
import java.io.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Main extends JFrame{
    public int[][] rgbMaze;
    public int[][] rgbMazeWithSolution;
    public Main(int[][] rgbMaze, int[][] rgbMazeWithSolution) {
        this.rgbMaze = rgbMaze;
        this.rgbMazeWithSolution = rgbMazeWithSolution;
        setTitle("DJP Maze Generation & Solver Using Recursive Backtracking");
        setSize(30*rgbMazeWithSolution[0].length,30*rgbMazeWithSolution.length + 37);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        for (int y = 0; y < rgbMaze.length; y++) {
            for (int x = 0; x < rgbMazeWithSolution[0].length; x++) {
                Color colour = new Color(rgbMazeWithSolution[y][x]);
                g.setColor(colour);
                g.fillRect(30*x,30*y + 30,30,30);
                g.setColor(Color.BLACK);
                g.drawRect(30*x,30*y,30,30);
            }
        }
    }

	public static void main(String[] args) {
        Long startTime = System.currentTimeMillis();
		//Graph G1 = new Graph(4,4, true);
		//G1.print();
		Graph G2 = new Graph(5,5, false);
		G2.print();
		// Graph G3 = new Graph(25,25, false);
        // G3.print();
        // BufferedImage img = createImage(G2.rgbMaze);
        Long endTime = System.currentTimeMillis();
        System.out.println((endTime - startTime) + " miliseconds");
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Main gui = new Main(G2.rgbMaze,G2.rgbMazeWithSolution);
                gui.setVisible(true);
            }
        });
        // BufferedImage image = new BufferedImage(G2.rgbMaze[0].length, G2.rgbMaze.length, BufferedImage.TYPE_INT_ARGB);
        // image.setRGB(0, 0, G2.rgbMaze[0].length, G2.rgbMaze.length, G2.rgbMaze, 0, G2.rgbMaze[0].length);
        // ImageIO.write(image, "png", new File("test.png"));
    }
    
    // public BufferedImage createImage(int[][] pixelData)
    // {
    // final int width = pixelData[0].length ;
    // final int height = pixelData.length ;
    // // First I create a BufferedImage with a DataBufferInt, with the appropriate dimensions and number of channels/bands/colors
    // ColorSpace myColorSpace = new FloatCS(ColorSpace.TYPE_INT_ARGB, channel) ;
    // int[] bits = new int[]{32} ;
    // ColorModel myColorModel = new ComponentColorModel(myColorSpace,bits,false,false,ColorModel.OPAQUE,DataBuffer.Type_INT_ARGB) ;
    // BufferedImage outputImage = new BufferedImage(myColorModel, myColorModel.createCompatibleWritableRaster(width, height), false, null) ;

    // int[] outputImagePixelData = ((DataBufferInt) outputImage.getRaster().getDataBuffer()).getData() ;

    // for (int y=0, pos=0 ; y < height ; y++)
    //     for (int x=0 ; x < width ; x++, pos++)
    //         outputImagePixelData[pos] = pixelData[y][x];

    // return outputImage ;
    // }
}
