public class Edge {
    //https://medium.com/basecs/a-gentle-introduction-to-graph-theory-77969829ead8
    public Node origin;
    public Node destination;

    public Edge(Node origin, Node destination) {
        this.origin = origin;
        this.destination = destination;
    }

    public Node getOrigin() {
        return origin;
    }

    public Node getDestination() {
        return destination;
    }

    @Override
    public toString() {
        return "{" + this.origin + ", " + this.destination + "}";
    }
}