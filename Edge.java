public class Edge implements  Comparable<Edge>{
    private final Vertex vertex1;
    private final Vertex vertex2;
    private final double weight;

    public Edge(Vertex vertex1,Vertex vertex2,double weight){
        this.vertex1=vertex1;
        this.vertex2=vertex2;
        this.weight=weight;
    }

    public Vertex getVertex1(){
        return vertex1;
    }

    public Vertex getVertex2(){
        return vertex2;
    }

    public double getWeight(){
        return weight;
    }

    @Override
    public int compareTo(Edge otherEdge){
        int much;
        double aaa = this.getWeight() - otherEdge.getWeight();
        much=(int)aaa;
        return much;
    }

    @Override
    public String toString() {
        return "Edge ( " + getVertex1().value + " , " + getVertex2().value + " ) weight-> " + getWeight();
    }
}
