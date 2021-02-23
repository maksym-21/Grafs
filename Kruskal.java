import java.io.File;
import java.util.*;

import static java.lang.System.*;

public class Kruskal {

    private static Set<Vertex> set_verticles=new HashSet<>();//lista wierzcholkow
    private static final ArrayList<Edge> set_relations=new ArrayList<>();//lista relacji

    private static ArrayList<Vertex> vertices = new ArrayList<>();
    private static ArrayList<HashSet<Vertex>> subsets = new ArrayList<>();
    private static ArrayList<Edge> edges = new ArrayList<>();



    public static void read_graph(String PATHFILE){
        try{
            File file=new File(PATHFILE);

            ArrayList<String> my_strings = new ArrayList<>();
            Scanner sc1 = new Scanner(file);

            while(sc1.hasNextLine()){
                my_strings.add(sc1.nextLine().trim());
            }

            for (String s : my_strings) {
                String[] splitt = s.split(",",3);

                int a=Integer.parseInt(splitt[0]);
                int b=Integer.parseInt(splitt[1]);
                double c = Double.parseDouble(splitt[2]);

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


    public static void removeTheSame(){
        Set<Vertex> sames=new HashSet<>();
        Set<Integer> samesByValue = new HashSet<>();

        for (Vertex vertex : set_verticles) {
            if (!samesByValue.contains(vertex.value)){
                samesByValue.add(vertex.value);
            }
            else {
                sames.add(vertex);
            }
        }

        set_verticles.removeAll(sames);
    }


    public int FOREST_KRUSKAL(Set<Vertex> vertices){
        int nodecount=vertices.size(); //ilosc wierzcholkow

        int totalWeight=0;

        //String message="";

        Collections.sort(set_relations); //sorting

        ArrayList<Edge> minimum = new ArrayList<>();

        DS nodeSet = new DS(nodecount+1);


        for(int q=0; q<set_relations.size() && minimum.size()< (nodecount-1);q++){//zaczynamy petle

            Edge currentEdge = set_relations.get(q);

            int root1 = nodeSet.find(currentEdge.getVertex1().value); //Find root of 1 vertex of the edge
            int root2 = nodeSet.find(currentEdge.getVertex2().value); //find another one

            //just print, keep on same line for union message
            //message+="find("+currentEdge.getVertex1() +") returns " + root1 + ", find("+ currentEdge.getVertex2()+") returns "+root2;


            //String unionMessage=",\tNo union performed\n";//check if no union is to be performed

            if(root1 != root2){ //if roots are in different sets the DO NOT create a cycle
                minimum.add(currentEdge); //add the edge to the graph
                nodeSet.union(root1, root2); //union the sets
                //unionMessage=",\tUnion("+root1+", "+root2+") done\n";
            }
            //message+=unionMessage;

        }

        //message+="\nFinal Minimum Spanning Tree ("+minimum.size()+" edges) :\n";//add to our message

        for(Edge edge: minimum){
            //message+=edge +"\n";
            totalWeight += edge.getWeight();
        }

        return totalWeight;
    }




    static class weightComparator implements Comparator<Edge> {
        @Override
        public int compare(Edge e1, Edge e2) {
            return (int)e1.getWeight() - (int)e2.getWeight();
        }
    }


    public static int ORIGINAL_KRUSKAL(){
        int weight = 0;

        vertices.addAll(set_verticles);
        edges.addAll(set_relations);

        // make subset of each vertex
        for (Vertex vertex : vertices) {
            HashSet<Vertex> set = new HashSet<>();
            set.add(vertex);
            subsets.add(set);
        }

        Collections.sort(edges, new weightComparator());

        for (int i = 0; i < edges.size(); i++) {
            Edge edg = edges.get(i);
            Vertex srcNode = edg.getVertex1();
            Vertex destNode = edg.getVertex2();

            if (find(srcNode) != find(destNode)) {
                out.println(edg.getVertex1().value + " --> " + edg.getVertex2().value + " == " + edg.getWeight());
                union(find(srcNode), find(destNode));
            }
        }

        for (Edge edge:edges) {
            weight+=edge.getWeight();
        }

        return weight;
    }


    private static void union(int aSubset, int bSubset) {
        HashSet<Vertex> aSet = subsets.get(aSubset);
        HashSet<Vertex> bSet = subsets.get(bSubset);
        //adding all elements of subsetB in subsetA and deleting subsetB
        Iterator<Vertex> iter = bSet.iterator();
        while (iter.hasNext()) {
            Vertex b = iter.next();
            aSet.add(b);
        }
        subsets.remove(bSubset);

    }

    private static int find(Vertex node) {
        int number = -1;

        for (int i = 0; i < subsets.size(); i++) {
            HashSet<Vertex> set = subsets.get(i);
            Iterator<Vertex> iterator = set.iterator();
            while (iterator.hasNext()) {
                Vertex setnode = iterator.next();
                if (setnode.value == node.value) {
                    number = i;
                    return number;
                }
            }
        }
        return number;
    }




    public static void main(String[] args) {
        out.println("Please give me full path to file for example  C:\\Users\\Downloads\\10.csv  :");

        Scanner s1=new Scanner(in);// C:\Users\Hewlett Packard\Downloads\10.csv
        String pathfile=s1.nextLine();

        read_graph(pathfile.trim());//reading our file-graph

        removeTheSame();


        Kruskal graph=new Kruskal();

        int value; //our var
        long millis1= System.nanoTime();
        value = graph.FOREST_KRUSKAL(set_verticles);
        long millis2= System.nanoTime();

        out.println("FOREST -> " + (millis2 - millis1) + " nsec " + " WEIGHT -> " + value); //info1


        long ms1= System.nanoTime();
        value=ORIGINAL_KRUSKAL();
        long ms2= System.nanoTime();

        out.println("ORIGINAL -> " + (ms2-ms1) + " nsec " + " WEIGHT -> " + value); //info2
    }

}
