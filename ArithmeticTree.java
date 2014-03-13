/* Tree class that represents a mathematical equation
 * Can be evaluated and changed
 * Only stores the root of the tree but all other nodes are stored by association (children, parents etc)
 */
import java.util.ArrayList;
public class ArithmeticTree{
    //////////
    //Fields//
    //////////

    private Node root;//Where the node begins. In this situation, it is the top of the tree
    
    private ArrayList<Node> nodes; //Where all nodes will be stored

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
        System.out.println("Adding to al "+n.getSymbol());
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
        System.out.println("Removin from al "+n.getSymbol());
        a.remove(n);
        for(Node ch : n.getChildren()){
            deleteNodeFromAl(a,ch);
        }
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
    
    /** Mutates the arithmetic tree
     *
     *  Methods:
     *  1 - Find random node and replace it with another of its kind
     *  2 - Find random node and replace it with any kind (Creates 'Junk DNA' (unused Nodes) but thats ok because that is what nature does too)
     */
    public void mutate(){
        //Select a random node
        int randomSpot = (int)(Math.random()*nodes.size());
        Node mutate;
        do{
            randomSpot = (int)(Math.random()*nodes.size());
            mutate = nodes.get(randomSpot);
        }while(mutate==root);//Don't mutate the root
        System.out.print("Mutating Node "+ mutate.getSymbol());
        //Choose which method to use to mutate

        //Replace this node with a random one of its kind
        Node newNode=null;
        if(mutate instanceof Operator){
            newNode=new Operator();

        }else if(mutate instanceof Value){
            newNode=new Value();
        }
        System.out.println(" with "+newNode.getSymbol());
        mutate.replace(newNode, nodes);
        nodes.set(randomSpot,newNode);
    }

    /** Returs a random node from nodes*/ 
    public Node randomNode(){
        //return a random entry from the arraylist nodes
        Node notRoot = null;
        do{
            int randomSpot = (int)(Math.random()*nodes.size());
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

    //Main method
    public static void main(String[] args){
         /*
        //Uses input for testing
        ArithmeticTree t = new ArithmeticTree(new Operator(3));
        t.addNode(new Operator(0));
        t.addNode(new Value(12));
        t.addNode(new Operator(0));
        t.addNode(new Value(7));
        t.addNode(new Operator(2));
        t.addNode(new Operator(2));
        t.addNode(new Value(true));
        t.addNode(new Value(true));
        t.addNode(new Value(3));
        t.addNode(new Value(true));
        System.out.println(t.toString());
*/

        
        //Random arithmetic tree
        //ArithmeticTree t = new ArithmeticTree();
        //System.out.println(t.toString());
        //System.out.println("RandNode - "+t.randomNode().toString()+" "+t.nodes.size());
    
        //Mutation test
        ArithmeticTree t = new ArithmeticTree();
        System.out.println(t.toString());

        ArithmeticTree s =  new ArithmeticTree(t);

        System.out.println(s.nodes.size());
        for(Node no : s.nodes){
            System.out.println(no.getSymbol());
        }

        System.out.println("S: "+s.toString());

        s.mutate();
        System.out.println("T: "+t.toString());
        System.out.println("S: "+s.toString());

        System.out.println(s.nodes.size());
        for(Node no : s.nodes){
            System.out.println(no.getSymbol());
        }

        System.out.println("Attempting come kind of crossover");
        ArithmeticTree n = new ArithmeticTree();
        System.out.println("N: "+n.toString());

        Node nn = n.randomNode();
        Node sn = s.randomNode();
        System.out.println("Replacing "+sn.toString()+"\nwith "+nn.toString());
        sn.replace(nn,s.nodes);
        System.out.println("S: "+s.toString());
        System.out.println(s.nodes.size());
        for(Node no : s.nodes){
            System.out.println(no.getSymbol());
        }

    }
}
