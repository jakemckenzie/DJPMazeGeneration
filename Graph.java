import java.util.Random;
import java.util.ArrayList;
import java.util.Arrays;
// cd C:\Users\Epimetheus\Documents\GitHub\DJPMazeGeneration
// javac *.java -Xlint:unchecked
// http://interactivepython.org/runestone/static/Java/Graphs/PrimsSpanningTreeAlgorithm.html

public class Graph {
    public int WIDTH;
    public int HEIGHT;

    public boolean debugger;

    public Node[][] lattice;
    public boolean[][] flatWall;
    public boolean[][] tallWall;
    public int[][] rgbMaze;
    public int[][] solution;
    public int[][] rgbMazeWithSolution;
    public int Up   = Integer.MAX_VALUE;
    public int Down = Integer.MIN_VALUE;
    public int Left = -1;
    public int Right = 1;

    public int[] rainbow = {0xFF355E,0xFD5B78,0xFF6037,0xFF9966,0xFF9933,0xFFCC33,0xFFFF66
                           ,0xFFFF66,0xCCFF00,0x66FF66,0xAAF0D1,0x50BFE6,0xFF6EFF,0xEE34D2
                           ,0xFF00CC,0xFF00CC};

    public Graph(int HEIGHT, int WIDTH, boolean debugger) {
        this.HEIGHT                     = HEIGHT;
        this.WIDTH                      = WIDTH;
        this.debugger                   = debugger;
        buildLattice();
    }
    public void buildLattice() {
        
        ArrayList<Wall> borders         = new ArrayList<Wall>();
        lattice                         = new Node[HEIGHT][WIDTH];
        flatWall                        = new boolean[HEIGHT + 1][WIDTH];
        tallWall                        = new boolean[HEIGHT][WIDTH + 1];
        Random urn                      = new Random(XORShift128plus());
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
            if (w.type == Left && !lattice[w.y][w.x - 1].added) {
                lattice[w.y][w.x - 1].added     = true;
                tallWall[w.y][w.x]              = true;
                n                               = lattice[w.y][w.x - 1];
            } else if (w.type == Right && !lattice[w.y][w.x + 1].added) {
                lattice[w.y][w.x + 1].added     = true;
                tallWall[w.y][w.x +1]           = true;
                n                               = lattice[w.y][w.x + 1];
            } else if (w.type == Up && !lattice[w.y - 1][w.x].added) {
                lattice[w.y - 1][w.x].added     = true;
                flatWall[w.y][w.x]              = true;
                n                               = lattice[w.y - 1][w.x];
            } else if (w.type == Down && !lattice[w.y + 1][w.x].added) {
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
            for (int x = 0; x < c.length; x++) {
                //System.out.print(" " + y + " " + x + " ");
                rgbMaze[y][x] = c[x] == 'X' ? 0x0 : c[x] == ' ' ? 0xFFFFFF : 0xFFFFFF; 
            }
        }
        //System.out.println(Arrays.deepToString(rgbMaze).replace("], ", "]\n").replace("[[", "[").replace("]]", "]") + '\n');

        System.out.println(maze);

        solution = new int[HEIGHT * 2 + 1][WIDTH * 2 + 1];
        solver(rgbMaze,0,1,0,solution,Down);
        solution[HEIGHT * 2][WIDTH * 2] = 0x0;
        //System.out.println(Arrays.deepToString(rgbMaze).replace("], ", "]\n").replace("[[", "[").replace("]]", "]") + '\n');
        //System.out.println(Arrays.deepToString(solution).replace("], ", "]\n").replace("[[", "[").replace("]]", "]") + '\n');
        
        rgbMazeWithSolution = new int[HEIGHT * 2 + 1][WIDTH * 2 + 1];
        for (int y = 0; y < rgbMaze.length; y++) {
            for (int x = 0; x < solution[0].length; x++) {
                rgbMazeWithSolution[y][x] = solution[y][x] != 0 ? solution[y][x] : rgbMaze[y][x];
            }
        }
        //System.out.println(Arrays.deepToString(rgbMazeWithSolution).replace("], ", "]\n").replace("[[", "[").replace("]]", "]") + '\n');
    }
    public boolean solver(int[][] rgbMaze,int y, int x, int path,  int[][] solution,int direction){
        if (y == (HEIGHT * 2) && x == (WIDTH * 2 - 1)) {
            solution[y][x]                                          = rainbow[path++ % 16];
                                                                    return true;
        }
        if ((y >= 0 && y < HEIGHT * 2 + 1 && x >= 0 && x < WIDTH * 2 + 1 && rgbMaze[y][x] != 0x0) == true) {
            solution[y][x]                                                                  = rainbow[path++ % 16];
            if (direction != Left && solver(rgbMaze, y, x + 1, path, solution,Right))       return true;
            if (direction != Up && solver(rgbMaze, y + 1, x, path, solution,Down))          return true;
            if (direction != Right && solver(rgbMaze, y, x - 1, path, solution,Left))       return true;
            if (direction != Down && solver(rgbMaze, y - 1, x, path, solution,Up))          return true;
            solution[y][x]                                                                  = 0x0;
                                                                                            return false;
        }
                                                                                            return false;
    }
        // if (x > 0 && rgbMaze[y][x - 1] == 0xFFFFFF) {
        //     solution[y][x] = rainbow[path++ % 6];
        //     solver(rgbMaze, y, x - 1, path, solution);
        // } else if (x < WIDTH * 2 + 1 && rgbMaze[y][x + 1] == 0xFFFFFF) {
        //     solution[y][x] = rainbow[path++ % 6];
        //     solver(rgbMaze, y, x + 1, path, solution);
        // } else if (y > 0 && rgbMaze[y - 1][x] == 0xFFFFFF) {
        //     solution[y][x] = rainbow[path++ % 6];
        //     solver(rgbMaze, y - 1, x, path, solution);
        // } else if (y < HEIGHT * 2 + 1 && rgbMaze[y + 1][x] == 0xFFFFFF) {
        //     solution[y][x] = rainbow[path++ % 6];
        //     solver(rgbMaze, y + 1, x, path, solution);
        // } else if (y == 0 || x == 0 || y == HEIGHT * 2 + 1 || x == WIDTH * 2 + 1) {
        //     solution[y][x] = rainbow[path++ % 6];
        // } else {
        //     solution[y][x] = 0x0;
        //     if (x > 0 && rgbMaze[y][x - 1] != 0xFFFFFF) {
        //         solution[y][x] = 0x0;
        //         solver(rgbMaze, y, x - 1, path, solution);
        //     } else if (x < WIDTH * 2 + 1 && rgbMaze[y][x + 1] != 0x0) {
        //         solution[y][x] = 0x0;
        //         solver(rgbMaze, y, x + 1, path, solution);
        //     } else if (y > 0 && rgbMaze[y - 1][x] == 0x0) {
        //         solution[y][x] = 0x0;
        //         solver(rgbMaze, y - 1, x, path, solution);
        //     } else if (y < HEIGHT * 2 + 1 && rgbMaze[y + 1][x] == 0x0) {
        //         solution[y][x] = 0x0;
        //         solver(rgbMaze, y + 1, x, path, solution);
        //     }

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
