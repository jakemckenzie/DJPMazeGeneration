import java.util.UUID;
/**
 * A node class
 */
public class Node {
    /**
     * @param y the veritcal coordinate
     */
    public final int y;
    /**
     * @param x the horizontal coordinate
     */
    public final int x;
    /**
     * @param id an id created for GUI but never used
     * 
     * https://kodejava.org/how-do-i-generate-uuid-guid-in-java/
     */

    public UUID id;
    /**
     * @param added a boolean to keep track of whether the node exists
     * in the lattice
     */

    public boolean added;


    public Node(int y, int x) {
        this.y = y;
        this.x = x;
        this.id = UUID.randomUUID();
    }

    @Override
    public String toString() {
        return "(" + this.x + ", " + this.y + ")" + this.id.toString();
    }
}