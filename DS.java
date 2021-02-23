public class DS {

    private int[] set; //the disjoint set as an array

    public DS(int numElements) { //in constructor we make our singleton sets ; makeSet()
        set = new int [numElements]; //creating set-singleton
        for(int i = 0; i < set.length; i++){ //initialize to -1 so the trees have nothing in them
            set[i] = -1;
        }
    }

    public void union(int root1, int root2) { // union() -->  union(){ link(){...} }
        if(set[root2] < set[root1]){ // root2 is deeper; upper limit
            set[root1] = root2; //Make root2 new root
        }
        else {
            if(set[root1] == set[root2]){
                set[root1]--; //Update height if same
            }
            set[root2] = root1; //Make root1 new root
        }
    }


    public int find(int x) {
        if(set[x] < 0){ //If tree is a root, return its index
            return x;
        }
        int next = x;
        while(set[next] > 0){ //Loop until we find a root
            next=set[next];
        }
        return next; //return our root
    }

}
