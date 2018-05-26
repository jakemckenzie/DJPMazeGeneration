import java.util.UUID;

public class Node {
    public final int y;
    public final int x;
    //https://kodejava.org/how-do-i-generate-uuid-guid-in-java/
    public UUID id;

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