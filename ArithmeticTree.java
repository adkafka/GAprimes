/* Tree class that represents a mathematical equation
 * Can be evaluated and changed
 * Only stores the root of the tree but all other nodes are stored by association (children, parents etc)
 */
import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Set;
import java.util.HashSet;
import java.util.Random;
public class ArithmeticTree{
    //////////
    //Fields//
    //////////

    private Node root;//Where the node begins. In this situation, it is the top of the tree
    
    public ArrayList<Node> nodes; //Where all nodes will be stored

    public static Random rand = new Random();

    public static final int MAX_NODES=5000;//After this num is hit, only Values will be generated

    ////////////////
    //Constructors//
    ////////////////
    /** No arg constructor*/
    public ArithmeticTree(){
        //Generate random root poerator
        root=new Operator();
        
        //Initialize nodes
        nodes = new ArrayList<Node>();
        nodes.add(root);

        //Populate until all leafs are Values
        root.populateNode(nodes);
        System.out.println("New Tree");
        checkValid();
    }

    /** Constructor given a root node*/
    public ArithmeticTree(Node root){
        this.root=root;
        nodes = new ArrayList<Node>();
        addNodeToAl(nodes,root);
        System.out.println("New Tree");
        checkValid();
    }
    
    /** Deep copy constructor */
    public ArithmeticTree(ArithmeticTree t) {
        root=t.root.deepCopy();
        nodes = new ArrayList<Node>();
        addNodeToAl(nodes,root);
        System.out.println("New Tree - just copied");
        checkValid();
    }

    ///////////
    //Methods//
    ///////////

    /** Get size of AL (How many nodes) */
    public int size(){
        return nodes.size();
    }

    /** Add Node to array List */ 
    public static ArrayList<Node> addNodeToAl(ArrayList<Node> a, Node n){
        if(n==null){
            return a;
        }
        a.add(n);
        for(Node ch : n.getChildren()){
            addNodeToAl(a,ch);
        }
        return a;
    }
    
    /** Delete Nodes from array List */ 
    public static ArrayList<Node> deleteNodeFromAl(ArrayList<Node> a, Node n){
        if(n==null){
            return a;
        }
        if(!a.remove(n)){
            System.err.println("Deleted node not in AL: "+n.getSymbol());
            System.exit(1);
        }
        for(Node ch : n.getChildren()){
            deleteNodeFromAl(a,ch);
        }
        return a;
    }

    /** Swap nodes in ArrayList - UNUSED as of now */
    public static ArrayList<Node> swapInAl(ArrayList<Node> a, Node in, Node out){
        a.remove(out);
        a.add(in);
        return a;
    }

    /** Get arraylist of nodes*/
    private ArrayList<Node> getNodes(){
        return nodes;
    }

    /** Adds a node to the tree - Stored by association*/
    public void addNode(Node n){
        //If it is new tree, set startNode
        if(root==null){
            root=n;
            return;
        }
        addNode(n,root);
    }

    /** Adds a node to the tree recursively - Helper method */
    private boolean addNode(Node n, Node addTo){
        if(!addTo.isFull()){//if it is not full, just add to this node
            addTo.setEmptyChild(n);
            nodes.add(n);
            return true; //Succesfully added
        }
        else{//addTo is full
            //Run recursively with all children
            for(Node add : addTo.getChildren()){
                if(addNode(n, add)){//Added succesfully
                    return true;
                }
                else{
                    continue;
                }
            }
        }
        return false;
    }

    /** Simplifies the arithmetic tree by folding constants **/
    public void foldConstants(){
        //System.out.println("Symbols in AL:");
        //for(Node n :  nodes){
            //Node[] ns = n.getChildren();
            //System.out.print(n.getSymbol());
        //}System.out.println();
        for(Node c : root.getChildren()){
            foldConstants(c);
        }
        System.out.println("Just folded constants");
        checkValid();
    }
    private void foldConstants(Node current){
        if(current==null){//Shouldn't happen
            System.err.println("ERROR: foldConstants recieved null Node. Error in ArithmeticTree");
            return;
        }
        if(current instanceof Value){//Cant fold only a Value
            return;
        }
        else if(current == root){
            return;
        }else{//An op that is not the root
            Node[] childs = current.getChildren();//Can throw null pointer??? - Not anymore I believe

            if(childs[0] instanceof Value && childs[1] instanceof Value){//Both children are values
                Value child1 = (Value)childs[0];
                Value child2 = (Value)childs[1];
                if(!child1.isInput() && !child2.isInput()){//Neither children is Input (they are constants)
                    Node newNode = new Value(current.evaluate(0));//We will be replacing current with this
                    //System.out.println("This Node: "+current.getSymbol());
                    //System.out.println("Parent: "+current.getParent().getSymbol());
                    //System.out.println("Child1: "+child1.getSymbol());
                    //System.out.println("Child2: "+child2.getSymbol());
                    //System.out.println("Can fold here: "+ childs[0].getSymbol()+current.getSymbol()+childs[1].getSymbol()+
                           //" With "+newNode.getSymbol());
                    //System.out.println("Parents eval: "+current.getParent().toString());
                    current.replace(newNode,nodes);
                    //System.out.println("Folded:");
                    //System.out.println(toString()+"\n");
                    //////////
                    //System.out.println("Symbols in AL:");
                    //for(Node n :  nodes){
                        //Node[] ns = n.getChildren();
                        //System.out.print(n.getSymbol()+", ");
                    //}System.out.println();
                    //////////
                    foldConstants(newNode.getParent());//Try level up too see if we can do more folding
                }
                else{//Can't fold here
                    return;
                }
            }
            else{//One of the children are operators
                if(!(childs[0] instanceof Value)){//Recurse
                    foldConstants(childs[0]);
                }
                if(!(childs[1] instanceof Value)){//Recurse
                    foldConstants(childs[1]);
                }
            }
        }
    }
    
    /** Mutates the arithmetic tree */
    public void mutate(){
        //Select a random node
        int randomSpot = rand.nextInt(nodes.size());
        Node mutate = nodes.get(randomSpot);
        mutate.mutate();
        System.out.println("Just mutated");
        checkValid();
    }

    /** Perform a genetic crossover between current AT and param */
    public ArithmeticTree crossover(ArithmeticTree p){
        System.out.println("Before crossover called");
        System.out.println(toString());
        checkValid();

        //First, clone the instance AT
        ArithmeticTree kid = new ArithmeticTree(this);
        ArithmeticTree par = new ArithmeticTree(p);//Clone other parent!
        System.out.println("First, clone the instance AT and the other parent");
        checkValid();

        //Choose instance's node point
        Node thisNode = kid.randomNode();
        System.out.println("Choose instance's node point: "+ thisNode.getSymbol());
        checkValid();

        //Choose p's node point
        Node pNode = par.randomNode();
        System.out.println("Choose par's node point: "+pNode.getSymbol());
        checkValid();

        //Perform crossover
        thisNode.replace(pNode,kid.nodes);
        System.out.println("Perform crossover");
        checkValid();

        System.out.println("Just crossed over");
        checkValid();
        return kid;
    }

    /** Returs a random node from nodes*/ 
    public Node randomNode(){
        //return a random entry from the arraylist nodes
        Node notRoot = null;
        do{
            int randomSpot = (int)(rand.nextDouble()*nodes.size());//change to use nextInt
            notRoot = nodes.get(randomSpot);
        }while(notRoot==root);
        return notRoot;
    }

    /** Converts tree into readabble math equation */
    @Override
    public String toString(){
        return root.toString();
    }

    /** Evaluates entire tree given input*/
    public double evaluate(int x){
        return root.evaluate(x);
    }

    /** Counts how many nodes are in a given tree */
    public int count(){
        return count(root,0);
    }
    private int count(Node n, int c){
        if(n==null){
            return c; 
        }
        c++;
        for(Node child : n.children){
            c = count(child,c); 
        }
        return c;
    }

    /** Used for debugging only
     * Make sure an AT is valid:
     * All nodes are replresented in the array and no more */
    public void checkValid(){
        Queue<Node> list = new LinkedList<Node>();
        Set<Node> visited = new HashSet<Node>();
        list.add(root);
        visited.add(root);
        while(! list.isEmpty()){
            Node t = list.remove();
            //Chech if this is in AL
            if(! nodes.contains(t)){
                System.err.println("Node not in AL!!!");
                System.exit(1);
            }
            //Check if parent is in AL (unless root)
            if(! nodes.contains(t.getParent()) && t!=root){
                System.err.println("Parent node ("+t.getParent().getSymbol()+")[Parent of "+t.getSymbol()+"] in AL!!!");
                System.exit(1);
            }
            for(Node ch : t.getChildren()){
                //Test that children are in AL
                if(! nodes.contains(ch)){
                    System.err.println("Child not in Node!!!!");
                    System.exit(1);
                }
                if(!visited.contains(ch)){
                    list.add(ch);
                    visited.add(ch);
                }
            }
            
        }
        
    }
    private void printAL(ArrayList<Node> a, String s){
        System.out.println("AL "+s+":");
        for(Node n : a){
            System.out.print(n.getSymbol()+',');
        }
    }

    public static void setRandSeed(int seed){
        ArithmeticTree.rand.setSeed(seed);
        Node.rand.setSeed(seed);
        Operator.rand.setSeed(seed);
        Value.rand.setSeed(seed);
    }
    public static void main(String[] args){
        setRandSeed(1);
        ArithmeticTree t = new ArithmeticTree();
        ArithmeticTree s = new ArithmeticTree();
        System.out.println("t: "+t.toString()+"\ns: "+s.toString());
        ArithmeticTree u = t.crossover(s);
        System.out.println("u: "+u.toString());
        System.out.println("t: "+t.toString()+"\ns: "+s.toString());
        System.out.println("t");
        t.checkValid();
        System.out.println("s");
        s.checkValid();
        System.out.println("u");
        u.checkValid();
    }
}
