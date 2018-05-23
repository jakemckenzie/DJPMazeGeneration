import java.util.UUID;

public class Node {

    public Type type;
    public Node parent;

    public final int x;
    public final int y;
    //https://kodejava.org/how-do-i-generate-uuid-guid-in-java/
    public UUID id;
    
    public Node(Type type, int x, int y){
        this.parent = null;
        this.x = x;
        this.y = y;
        this.type = type;
        this.id = UUID.randomUUID();
    }

    public Node(Type type, int x,int y, Node parent) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.parent = parent;
        this.id = UUID.randomUUID();
    }
    
    public UUID getID() {
        return this.id;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public Type getType() {
        return type;
    }

    @Override
    public String toString() {
        return "(" + this.x + ", " + this.y + ")" + this.getID();;
    }
}