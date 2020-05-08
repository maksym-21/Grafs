import java.io.File;
import java.util.*;

public class Graph_dijkstry {

    private static Set<Vertex> set_verticles=new HashSet<>();//list of verticles
    private static final Set<Edge> set_relations=new HashSet<>();//list of relations
    private static List<Vertex>[] adj;

    static class Vertex{
        int name;//value of vertex
        int dystance;
        Vertex previous;//previous 

        public Vertex(int name){
            this.name=name;
        }


        public void setPrevious(Vertex previous) {
            this.previous = previous;
        }

        public void setDystance(int dystance) {
            this.dystance = dystance;
        }

        public int getDystance() {
            return dystance;
        }

        public int getName() {
            return name;
        }

        public Vertex getPrevious() {
            return previous;
        }

        public static Vertex getV(int name){
            for (Vertex a:set_verticles) {
                if (name==a.name)return a;
            }
            return null;
        }
    }

    static class Edge{
        Vertex First;
        Vertex Last;
        int weight;//weight od edge

        public Edge(Vertex first,Vertex last,int weight){
            this.First=first;
            this.Last=last;
            this.weight=weight;
        }

        public Vertex getLast() {
            return Last;
        }

        public Vertex getFirst() {
            return First;
        }

        public static Edge getEdge(Vertex A,Vertex B){
            for (Edge edge:set_relations) {
                if (A.getName()==edge.First.getName() && B.getName()==edge.Last.getName()){
                    return edge;
                }
            }
            return null;
        }
    }


    public static void Read_graph(String filename){
        try{
            File file=new File(filename);

            ArrayList<String> my_strings = new ArrayList<>();
            Scanner sc1 = new Scanner(file);

            while(sc1.hasNextLine()){
                my_strings.add(sc1.nextLine().trim());
            }

            for (String s : my_strings) {
                String[] splitt = s.split(",",3);

                int a=Integer.parseInt(splitt[0]);
                int b=Integer.parseInt(splitt[1]);
                int c=Integer.parseInt(splitt[2]);

                set_verticles.add( new Vertex(a) );
                set_verticles.add( new Vertex(b) );

                Edge g1=new Edge(new Vertex(a),new Vertex(b),c);

                set_relations.add(g1);
            }
            sc1.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    public static void RemoveTheSame(Set<Vertex> a){
        Set<Vertex> sames=new HashSet<>();
        Set<Integer> samesByValue = new HashSet<>();

        for (Vertex vertex : a) {
            if (!samesByValue.contains(vertex.name)){
                samesByValue.add(vertex.name);
            }
            else {
                sames.add(vertex);
            }
        }

        set_verticles.removeAll(sames);
    }


    public static void InitializeAdjList(){
        RemoveTheSame(set_verticles);//checking repeats-objects in set<Vertex>
        
        adj=new LinkedList[set_verticles.size()+1];

        for (Vertex A:set_verticles) {
            adj[A.name]=new LinkedList<>();
            for (Edge edge:set_relations) {
                if(edge.getFirst().equals(A)){
                    adj[A.name].add(edge.getLast());
                }
            }
        }

    }


    public static void InitializeSingleSource(Vertex started){
        for (Vertex vertex : set_verticles) {
            vertex.dystance=Integer.MAX_VALUE;
            vertex.previous=null;
        }
        started.setDystance(0);
    }


    public static void Relax(Vertex u,Vertex v,Edge w){
        if (v.getDystance() > ( u.getDystance() + w.weight )){
            v.setDystance(u.dystance + w.weight);
            v.setPrevious(u);
        }
    }


    public static void DIJKSTRY(Vertex started){
        InitializeAdjList();//inicialization adjency list
        InitializeSingleSource(started);//doing initialize..()
        
        PriorityQueue<Integer> priorityQueue=new PriorityQueue<>();

        Map<Integer,Vertex> mapa=new HashMap<>();

        for (Vertex A:set_verticles){
            mapa.put(A.dystance,A);
            priorityQueue.add(A.dystance);
        }

        while (!priorityQueue.isEmpty()){
            Vertex u=mapa.get(priorityQueue.poll());
            done_verticles.add(u);

            for (Vertex vertex : adj[u.name]){
                Relax(u,vertex,Objects.requireNonNull(Edge.getEdge(u,vertex)));
            }
        }
    }


    public static void main(String[] args) {
        System.out.println("Please give full path(for example C:\\Users\\Downloads\\10.csv):");

        Scanner s1=new Scanner(System.in);
        String path=s1.nextLine();
        Read_graph(path);

        System.out.println("Please give started_vertex:");

        Scanner s2=new Scanner(System.in);
        String v2=s2.nextLine();//for example 1 in next line
        Vertex started_vertex=Vertex.getV(Integer.parseInt(v2));

        System.out.println("Please give end_vertex1:");

        Scanner s3=new Scanner(System.in);
        String v3=s3.nextLine();//for example 2 in next line
        Vertex vertex_end1=Vertex.getV(Integer.parseInt(v3));

        System.out.println("Please give end_vertex2:");

        Scanner s4=new Scanner(System.in);
        String v4=s4.nextLine();//for example 2 in next line
        Vertex vertex_end2=Vertex.getV(Integer.parseInt(v4));

        Date date1=new Date();
        long milis1=date1.getTime();

        DIJKSTRY(started_vertex);

        Date date2=new Date();
        long milis2=date2.getTime();

        System.out.println("Dystances:" + Objects.requireNonNull(vertex_end1).getDystance() + " " + Objects.requireNonNull(vertex_end2).getDystance());
        System.out.println("Time: "+ (milis2-milis1) +" ms");

        System.out.println("Used in project PriorityQueue from java.util.PriorityQueue");
    }
}
