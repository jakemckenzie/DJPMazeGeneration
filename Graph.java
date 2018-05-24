public class Graph {
    public final int WIDTH;
    public final int HEIGHT;

    public boolean done;

    public Node[][] lattice;
    public Node root;
    public Node end;

    public Graph(int WIDTH, int HEIGHT) {
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;

        this.lattice = new Node[WIDTH][HEIGHT];

        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                Node n = new Node(Type.WALL,x,y);
                System.out.println(n);
                this.lattice[x][y] = n;
            }
        }
        this.done = false;
    }

}