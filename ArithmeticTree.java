/* Tree class that represents a mathematical equation
 * Can be evaluated and changed
 * Only stores the root of the tree but all other nodes are stored by association (children, parents etc)
 */
import java.util.ArrayList;
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
    }

    /** Constructor given a root node*/
    public ArithmeticTree(Node root){
        this.root=root;
        nodes = new ArrayList<Node>();
        addNodeToAl(nodes,root);
    }
    
    /** Deep copy constructor */
    public ArithmeticTree(ArithmeticTree t) {
        root=t.root.deepCopy();
        nodes = new ArrayList<Node>();
        addNodeToAl(nodes,root);
    }

    ///////////
    //Methods//
    ///////////

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
        a.remove(n);
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
        for(Node c : root.getChildren()){
            foldConstants(c);
        }
    }
    private void foldConstants(Node current){
        if(current instanceof Value){//Cant fold only a Value
            return;
        }
        else if(current == root){
            return;
        }else{
            Node[] childs = current.getChildren();

            if(childs[0] instanceof Value && childs[1] instanceof Value){//BOth children are values
                Value child1 = (Value)childs[0];
                Value child2 = (Value)childs[1];
                if(!child1.isInput() && !child2.isInput()){//Neither children is Input
                    Node newNode = new Value(current.evaluate(0));
                    current.replace(newNode,nodes);
                    System.out.println("Can fold here: "+ childs[0].getSymbol()+current.getSymbol()+childs[1].getSymbol()+
                            " With "+current.evaluate(0));
                    foldConstants(newNode.getParent());//Try level up too see if we can do more folding
                }
                else{//Can't fold here
                    return;
                }
            }
            else{
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
    }

    /** Perform a genetic crossover between current AT and param */
    public ArithmeticTree crossover(ArithmeticTree p){
        //First, clone the instance AT
        ArithmeticTree kid = new ArithmeticTree(this);
        //Choose instance's node point
        Node thisNode = kid.randomNode();
        //Choose p's node point
        Node pNode = p.randomNode();
        //Perform crossover
        thisNode.replace(pNode,kid.nodes);
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

    public static void setRandSeed(int seed){
        ArithmeticTree.rand.setSeed(seed);
        Node.rand.setSeed(seed);
        Operator.rand.setSeed(seed);
        Value.rand.setSeed(seed);
    }
}
