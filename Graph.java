import java.util.Random;
import java.util.ArrayList;
import java.util.Arrays;
// cd C:\Users\Epimetheus\Documents\GitHub\DJPMazeGeneration
// javac *.java -Xlint:unchecked
// http://interactivepython.org/runestone/static/Java/Graphs/PrimsSpanningTreeAlgorithm.html

/**
 * Since all conections between nodes in my graph happen between adjacent elements,
 * a "linked structure" is not needed to represent the maze as a graph. A pair of boolean matrices
 * are used in conjuction with a 2d matrix of nodes to represent the nodes and edges. This can
 * be accomplished because each node, by it's nature, only has two wall segments that are cut and two
 * intact, ALWAYS. No pointers are needed only the location of nodes in the graph and their wall placements.
 * 
 * For most of this assignment the lectures from coursera on Algorithms and Data Structures by Tim Roughgarden
 * was used heavily.
 * 
 * https://www.coursera.org/learn/algorithms-graphs-data-structures/lecture/NX0BI/graph-search-overview
 * 
 * Although I did not understand most of it, I did watch a set of lectures by the same instructor on
 * algorithmic game theory a while ago and for anyone reading this I
 * recommend the lectures heavily.
 * 
 * https://www.youtube.com/watch?v=TM_QFmQU_VA
 */

public class Graph {
    /**
     * @param WIDTH the width of the lattice
     */
    public int WIDTH;
    /**
     * @param HEIGHT the height of the lattice
     */
    public int HEIGHT;
    /**
     * @param debugger a boolean used in debugging
     */
    public boolean debugger;
    /**
     * @param lattice a grid of nodes which make up the graph
     */
    public Node[][] lattice;
    /**
     * @param flatWall a boolean lattice used for horizontal walls used for terminal printing 
     */
    public boolean[][] flatWall;
    /**
     * @param tallWall a boolean lattice used for vertical walls used for terminal printing 
     */
    public boolean[][] tallWall;
    /**
     * @param rgbMaze Maze containing hex values for easily outputing to GUI
     */
    public int[][] rgbMaze;
    /**
     * @param solution the solution to the maze only used for GUI, this is only used in processing
     */
    public int[][] solution;
    /**
     * @param rgbMazeWithSolution Maze containing hex values for outputing the maze with solution.
     */
    public int[][] rgbMazeWithSolution;
    /**
     * @param Up up direction used for type of wall
     */
    public int Up   = Integer.MAX_VALUE;
    /**
     * @param Down down direction used for type of wall
     */
    public int Down = Integer.MIN_VALUE;
    /**
     * @param Left left direction used for type of wall
     */
    public int Left = -1;
    /**
     * @param Right right direction used for type of wall
     */
    public int Right = 1;
    /**
     * @param urn usn used for random sampling
     */
    public Random urn = new Random(XORShift128plus());
    /**
     * @param rainbow bright colours chosen for creating the solution path to maze
     */
    public int[] rainbow = {0xFF355E,0xFD5B78,0xFF3855,0xFD3A4A,0xFA5B3D,0xFFAA1D,0xFFF700
                           ,0xA7F432,0x299617,0xFB4D46,0xFF6037,0xFF9966,0xFF9933,0xFFCC33
                           ,0xFFFF66,0xFFFF66,0xCCFF00,0x66FF66,0xAAF0D1,0x50BFE6,0xFF6EFF
                           ,0xEE34D2,0x2243B6,0x5DADEC,0x5DADEC,0x9C51B6,0xFDFF00,0x87FF2A
                           ,0x319177,0xFF00CC,0xFF00CC,0xFFFF38,0xB2F302,0xFC5A8D,0xFAFA37
                           ,0xFF5470,0xED0A3F,0x3F26BF,0xFC74FD,0xFF00  ,0xFF0066,0xFF3300};

    public Graph(int HEIGHT, int WIDTH, boolean debugger) {
        this.HEIGHT                     = HEIGHT;
        this.WIDTH                      = WIDTH;
        this.debugger                   = debugger;
        buildLattice();
    }
    /**
     * https://www.coursera.org/learn/algorithms-greedy/lecture/tQ6gK/prims-mst-algorithm
     * This method builds lattice by first creating a grid of connected
     * walls. 
     * 
     * The idea of this algorithm is to choose a single cut randomly, then
     * at each stage I grow by graph by making more and more cuts to the graph.
     * As I break walls(make cuts) I update my wall boolean arrays, if the node
     * has been visited I keep track of that and don't break walls associated 
     * with that node. 
     * 
     * This algorithm takes advantage of the "cut property"
     */
    public void buildLattice() {
        
        ArrayList<Wall> borders         = new ArrayList<Wall>();
        lattice                         = new Node[HEIGHT][WIDTH];
        flatWall                        = new boolean[HEIGHT + 1][WIDTH];
        tallWall                        = new boolean[HEIGHT][WIDTH + 1];
        
        flatWall[0][0]                  = true;
        flatWall[HEIGHT][WIDTH - 1]     = true;

        for (int y = 0; y < HEIGHT; y++) for(int x = 0; x < WIDTH; x++) lattice[y][x] = new Node(y, x);

        int y                           = urn.nextInt(HEIGHT);
        int x                           = urn.nextInt(WIDTH);
        Node n                          = lattice[y][x];
        n.added                         = true;

        if(y > 0)                       borders.add(new Wall(y, x, Up));
        if(y < HEIGHT - 1)              borders.add(new Wall(y, x, Down));
        if(x > 0)                       borders.add(new Wall(y, x, Left));
        if(x < WIDTH - 1)               borders.add(new Wall(y, x, Right));
        
        int adds = 1;

        while (!borders.isEmpty() && adds <= HEIGHT * WIDTH) {
            if (debugger) print();
            Wall w = borders.remove(urn.nextInt(borders.size()));
            if (w.orientation == Left && !lattice[w.y][w.x - 1].added) {
                lattice[w.y][w.x - 1].added     = true;
                tallWall[w.y][w.x]              = true;
                n                               = lattice[w.y][w.x - 1];
            } else if (w.orientation == Right && !lattice[w.y][w.x + 1].added) {
                lattice[w.y][w.x + 1].added     = true;
                tallWall[w.y][w.x +1]           = true;
                n                               = lattice[w.y][w.x + 1];
            } else if (w.orientation == Up && !lattice[w.y - 1][w.x].added) {
                lattice[w.y - 1][w.x].added     = true;
                flatWall[w.y][w.x]              = true;
                n                               = lattice[w.y - 1][w.x];
            } else if (w.orientation == Down && !lattice[w.y + 1][w.x].added) {
                lattice[w.y + 1][w.x].added     = true;
                flatWall[w.y + 1][w.x]          = true;
                n                               = lattice[w.y + 1][w.x];
            } 
            
            int Y = n.y;
            int X = n.x;

            if (Y > 0 && !lattice[Y - 1][X].added)                  borders.add(new Wall(Y, X, Up));
            if (Y < (HEIGHT - 1) && !lattice[Y + 1][X].added)       borders.add(new Wall(Y, X, Down));
            if (X > 0 && !lattice[Y][X - 1].added)                  borders.add(new Wall(Y, X, Left));
            if (X < (WIDTH - 1) && !lattice[Y][X + 1].added)        borders.add(new Wall(Y, X, Right));
        }
        

        
    }
    /**
     * print is used for both console and GUI. It is where the terminal maze is created and 
     * the pixel arrays are generated.
     */
    public void print() {
        String maze = "";
        rgbMaze = new int[HEIGHT * 2 + 1][WIDTH * 2 + 1];
        maze += "XS";
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                maze += (x == 0 && y == 0) ? "": flatWall[y][x] ? "X " : "XX";    
            }
            maze += "X" + "\n";
            for (int x = 0; x < WIDTH; x++) {
                maze += tallWall[y][x] ? (lattice[y][x].added && debugger) ? "V ": "  " : lattice[y][x].added && debugger ? "XV" : "X ";
            }
            maze += "X" + "\n";            
        }
        for (int x = 0; x < WIDTH-1; x++) {
            maze += flatWall[HEIGHT][x] ? "X " : "XX";
        }
        maze += "XFX" + "\n";
        String temp[] = maze.split("\\\n");
        for (int y = 0; y < temp.length; y++) {
            char[] c = temp[y].toCharArray();
            for (int x = 0; x < c.length; x++) rgbMaze[y][x] = c[x] == 'X' ? 0x0 : c[x] == ' ' ? 0xFFFFFF : 0xFFFFFF;
        }
        System.out.println(maze);
        solution = new int[HEIGHT * 2 + 1][WIDTH * 2 + 1];
        recursiveBacktracker(rgbMaze,0,1,35,solution,Down);
        solution[HEIGHT * 2][WIDTH * 2] = 0x0;
        
        rgbMazeWithSolution = new int[HEIGHT * 2 + 1][WIDTH * 2 + 1];
        for (int y = 0; y < rgbMaze.length; y++) {
            for (int x = 0; x < solution[0].length; x++) {
                rgbMazeWithSolution[y][x] = solution[y][x] != 0 ? solution[y][x] : rgbMaze[y][x];
            }
        }
    }
    /**
     * Recursive backtracker to solve the maze. I attempted at first to implement
     * A* but got bogged down in the complexity of the algorithm so I did this instead.
     * 
     * https://www.coursera.org/learn/algorithms-graphs-data-structures/lecture/JZRXz/breadth-first-search-bfs-the-basics
     */
    public boolean recursiveBacktracker(int[][] rgbMaze,int y, int x, int path,  int[][] solution,int direction){
        if (y == (HEIGHT * 2) && x == (WIDTH * 2 - 1)) {
            solution[y][x]                                                                                  = rainbow[urn.nextInt(path)];
                                                                                                            return true;
        }
        if ((y >= 0 && y < HEIGHT * 2 + 1 && x >= 0 && x < WIDTH * 2 + 1 && rgbMaze[y][x] != 0x0) == true) {
            solution[y][x]                                                                                  = rainbow[urn.nextInt(path)];
            if (direction != Left && recursiveBacktracker(rgbMaze, y, x + 1, path, solution,Right))         return true;
            if (direction != Up && recursiveBacktracker(rgbMaze, y + 1, x, path, solution,Down))            return true;
            if (direction != Right && recursiveBacktracker(rgbMaze, y, x - 1, path, solution,Left))         return true;
            if (direction != Down && recursiveBacktracker(rgbMaze, y - 1, x, path, solution,Up))            return true;
            solution[y][x]                                                                                  = 0x0;
                                                                                                            return false;
        }
                                                                                                            return false;
    }
    /**
     * Checks to see if the recursiveBacktracker() is following a valid path
     */

    public boolean blocked(int[][] rgbMaze,int y, int x, int path,  int[][] solution) {
        if (y < 0 || x < 0) return false;
        return y < 0 || x < 0 && rgbMaze[y][x] != 0xFFFFFF ||  x > WIDTH * 2 || y > HEIGHT * 2;
    }

    
 
    /**
     * I implemented these after reading about how Java random works. Essentially they exist to make
     * better psuedo-random seeds. My average day count and standard deviation decreased after I 
     * implemented these.
     * 
     * https://en.wikipedia.org/wiki/Xorshift
     * https://www.javamex.com/tutorials/random_numbers/xorshift.shtml#.Wtmgwi7wZEY
     */
    private static long XORShift() {
        long x = System.currentTimeMillis();
        x ^= (x << 21);
        x ^= (x >>> 35);
        x ^= (x << 4);
        return x;
    }
    private static long XORShift128plus() {
        long x = System.currentTimeMillis();
        long y = XORShift();    
        x ^= (x << 23);
        long z = x ^ y ^ (x >> 26);
        return z + x;
    }
}
