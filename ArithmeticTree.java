/* Tree class that represents a mathematical equation
 * Can be evaluated and changed
 * Only stores the root of the tree but all other nodes are stored by association (children, parents etc)
 */
import java.util.ArrayList;
public class ArithmeticTree{
    //////////
    //Fields//
    //////////

    Node root;//Where the node begins. In this situation, it is the top of the tree
    
    ArrayList<Node> nodes; //Where all nodes will be stored

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
        nodes.add(root);
    }

    ///////////
    //Methods//
    ///////////

    /** Adds a node to the tree - Stored by association*/
    public void addNode(Node n){
        //If it is new tree, set startNode
        if(root==null){
            root=n;
            return;
        }
        addNode(n,root);
    }

    /** Adds a node to the tree recursively - Stored by association*/
    private boolean addNode(Node n, Node addTo){
        if(!addTo.isFull()){//if it is not full, just add to this node
            addTo.setEmptyChild(n);
            nodes.add(n);
            System.out.println("Added "+n.getSymbol()+" to "+addTo.getSymbol());
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

        /*
        if(! (addTo instanceof Operator)){//If it not an operator, it can't add here
            return false;
        }
        Operator addToOp=(Operator)addTo; //Convert it to an operator
        if(addToOp.leftChild==null){//If can add to left node
            n.setParent(addToOp);
            addToOp.leftChild=n;
            nodes.add(n);
            return true;
        }else if(addToOp.rightChild==null){//If can add to right node
            n.setParent(addToOp);
            addToOp.rightChild=n;
            nodes.add(n);
            return true;
        }else{
            if(addNode(n,addToOp.leftChild)){//Try left child
                nodes.add(n);
                return true;
            }else if(addNode(n,addToOp.rightChild)){//Try right child
                nodes.add(n);
                return true;
            }else{
                return false;
            }
        }
        */
    }
    
    /** Returs a random node from nodes*/ 
    public Node randomNode(){
        //return a random entry from the arraylist nodes
        return nodes.get((int)(Math.random()*nodes.size()));
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
        ArithmeticTree t = new ArithmeticTree(new Operator(3));
        
        //t.addNode(new Operator(3));
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
        */
        ArithmeticTree t = new ArithmeticTree();
        System.out.println(t.toString());
        System.out.println("RandNode - "+t.randomNode().toString()+" "+t.nodes.size());

    }
}
