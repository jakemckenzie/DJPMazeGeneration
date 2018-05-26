import javax.imageio.*;
import java.awt.Image;
import java.io.*;
import java.awt.image.BufferedImage;

public class Main {

	public static void main(String[] args) throws IOException{
        Long startTime = System.currentTimeMillis();
		Graph G1 = new Graph(5,5, true);
		G1.print();
		Graph G2 = new Graph(5,5, false);
		G2.print();
		Graph G3 = new Graph(15,15, false);
        G3.print();
        Long endTime = System.currentTimeMillis();
        System.out.println((endTime - startTime) + " miliseconds");
        getImageFromArray(G2.flatWall);
    }
    
    public static void getImageFromArray(boolean[][] flatWall) throws IOException{
        int xLenght = flatWall.length;
        int yLength = flatWall[0].length;
        BufferedImage b = new BufferedImage(xLenght, yLength, 3);

        for(int x = 0; x < xLenght; x++) {
            for(int y = 0; y < yLength; y++) {
                int rgb = flatWall[x][y] == true ? 0x0 : 0xFF;
                b.setRGB(x, y, rgb);
            }
        }
        ImageIO.write(b, "test", new File("test.jpg"));
    }
}
