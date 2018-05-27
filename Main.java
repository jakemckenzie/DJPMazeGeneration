import javax.imageio.*;
import java.awt.Image;
import java.io.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

public class Main {

	public static void main(String[] args) throws Exception{
        Long startTime = System.currentTimeMillis();
		Graph G1 = new Graph(4,4, true);
		G1.print();
		Graph G2 = new Graph(4,4, false);
		G2.print();
		//Graph G3 = new Graph(15,15, false);
        //G3.print();
        Long endTime = System.currentTimeMillis();
        System.out.println((endTime - startTime) + " miliseconds");
        // BufferedImage image = new BufferedImage(G2.rgbMaze[0].length, G2.rgbMaze.length, BufferedImage.TYPE_INT_ARGB);
        // image.setRGB(0, 0, G2.rgbMaze[0].length, G2.rgbMaze.length, G2.rgbMaze, 0, G2.rgbMaze[0].length);
        // ImageIO.write(image, "png", new File("test.png"));
    }
    
    // public static void getImageFromArray(boolean[][] flatWall) throws IOException{
    //     int xLenght = flatWall.length;
    //     int yLength = flatWall[0].length;
    //     BufferedImage b = new BufferedImage(xLenght, yLength, 3);

    //     for(int x = 0; x < xLenght; x++) {
    //         for(int y = 0; y < yLength; y++) {
    //             int rgb = flatWall[x][y] == true ? 0x0 : 0xFF;
    //             b.setRGB(x, y, rgb);
    //         }
    //     }
    //     ImageIO.write(b, "test", new File("test.jpg"));
    // }
}
