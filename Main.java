
public class Main {

	public static void main(String[] args) {
        Long startTime = System.currentTimeMillis();
		Graph G1 = new Graph(5,5, true);
		G1.print();
		Graph G2 = new Graph(5,5, false);
		G2.print();
		Graph G3 = new Graph(15,15, false);
        G3.print();
        Long endTime = System.currentTimeMillis();
        System.out.println((endTime - startTime) + " miliseconds");
	}

}
