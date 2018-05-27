import javax.imageio.*;
//import java.awt.Image;
import java.awt.*;
import java.io.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

public class Main {

	public static void main(String[] args) throws Exception{
        Long startTime = System.currentTimeMillis();
		//Graph G1 = new Graph(4,4, true);
		//G1.print();
		Graph G2 = new Graph(4,4, false);
		G2.print();
		//Graph G3 = new Graph(15,15, false);
        //G3.print();
        // BufferedImage img = createImage(G2.rgbMaze);
        Long endTime = System.currentTimeMillis();
        System.out.println((endTime - startTime) + " miliseconds");
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
