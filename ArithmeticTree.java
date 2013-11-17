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
    

    ////////////////
    //Constructors//
    ////////////////
    /** No arg constructor*/
    public ArithmeticTree(){
        //Generate random root poerator
        root=new Operator();
        //Populate until all leafs are Values
        root.populateNode();
    }

    /** Constructor given a root node*/
    public ArithmeticTree(Node root){
        this.root=root;
        this.root.parent=null;
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
        if(! (addTo instanceof Operator)){//If it not an operator, it can't add here
            return false;
        }
        Operator addToOp=(Operator)addTo; //Convert it to an operator
        if(addToOp.leftChild==null){//If can add to left node
            n.parent=addToOp;
            addToOp.leftChild=n;
            return true;
        }else if(addToOp.rightChild==null){//If can add to right node
            n.parent=addToOp;
            addToOp.rightChild=n;
            return true;
        }else{
            if(addNode(n,addToOp.leftChild)){//Try left child
                return true;
            }else if(addNode(n,addToOp.rightChild)){//Try right child
                return true;
            }else{
                return false;
            }
        }
    }
    
    /** Trial method for returning a random node */
    public Node randomNode(){
        int dist=0;//Legnth of randomly traversed path from root to leaf
        Node n = root;
        ArrayList<Node> nodes = new ArrayList<Node>();
        while(n.isOperator()){
            Operator o = (Operator)n;
            if(Math.random()>=0.5){
                n=o.leftChild;
                nodes.add(n);
                dist++;
            }else{
                n=o.rightChild;
                nodes.add(n);
                dist++;
            }
        }
        return nodes.get((int)Math.random()*nodes.size());
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
           t.addNode(new Operator(3));
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
        for(int i =0; i<20;i++ ){
            ArithmeticTree t = new ArithmeticTree();
            System.out.println(t.toString());
            System.out.println("RandNode \n"+t.randomNode().toString());
            //System.out.println(t.evaluate(10));
        }
    }
}
