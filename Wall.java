/**
 * A simple Wall "edge" class. This class context nodes. These edges are
 * broken in the creation of the DJP maze.
 * 
 * https://medium.com/basecs/a-gentle-introduction-to-graph-theory-77969829ead8
 */

public class Wall {
    /**
     * @param x horizontal cooridinate
     */
    public int x;
    /**
     * @param y vertical cooridinate
     */
    public int y;
    /**
     * @param orientation position of the wall
     */
    public int orientation;

    public Wall(int y, int x, int orientation) {
        this.y              = y;
        this.x              = x;
        this.orientation    = orientation;
    }
}