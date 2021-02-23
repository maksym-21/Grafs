public class Vertex {
    int value;
    Vertex previous;
    boolean visited;

    public Vertex(int value){
        this.value=value;
        previous=null;
        visited=false;
    }

}
