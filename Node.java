/* Node class
 * class that represents possible nodes in a tree
 */
import java.util.ArrayList;
import java.util.Random;
public abstract class Node implements Cloneable{
    //////////
    //Fields//
    //////////
    
    protected Node[] children;
    protected Node parent;
    protected int numChildren;

    public static Random rand = new Random();

    protected static final double OP_FREQ = 0.35;//Frequency that when a random Node is created, it will be an operator


    ////////////////
    //Constructors//
    ////////////////
    /** Input how many children*/
    public Node(int numChildren){
        this.numChildren=numChildren;
        //initialize the array
        children = new Node[numChildren];
    }
    
    /** Constructor given parent*/
    public Node(Node parent, int numChildren){
        this(numChildren);
        if(parent!=null){
            parent.setEmptyChild(this);
        }
    }

    /** Deep Copy */
    public Node(Node n) {
        //empty
    }

    ///////////
    //Methods//
    ///////////
    
    /** Get parent */
    public Node getParent(){
        return this.parent;
    }
    
    /** Returns whether the node is full*/
    public boolean isFull(){
        for(Node n : children){//Cycle thru the array
            if(n==null){//If there is an empty child
                return false;
            }
        }
        return true;
    }

    /** Set an empty child*/
    public void setEmptyChild(Node child){
        for(int i = 0; i<numChildren; i++){
            if(children[i]==null){
                //set parent relationship
                child.parent=this;
                //set child
                children[i]=child;
                return;
            }
        }
    }

    /** Get array of children*/
    public Node[] getChildren(){
        return children;
    }
    /** Set children*/
    private void setChildren(Node[] children){
        if(this.numChildren==children.length){//same size of children
            this.children=children;
            //Set the parents for each of the children
            for(Node n : children){
                n.parent=this;
            }
        }
    }

    /** Get symbol*/
    public abstract String getSymbol();
    
    /** Evaluate the equation given an input */
    public abstract double evaluate(int x);

    /** Perform the deep copy */
    public abstract Node deepCopy();
    
    /** Create and return a random Node, could be Operator or Value - freq decided by OP_FREQ */
    public static Node randomNode(){
        if (rand.nextInt()>OP_FREQ){//create a Value
            return new Value();
        }else{
            return new Operator();
        }
    }

    /** Mutate node */
    public abstract void mutate();

    /** Replace this node with the supplies node
     *  Also modifies nodes accordingly
     *  Does not work with root node
     * */
    public void replace(Node newNode, ArrayList<Node> nodes){
        //If newNode is Operator
        if(newNode instanceof Operator){//works if replacing op or value
            replaceNode(newNode,nodes);//replace it
            ArithmeticTree.deleteNodeFromAl(nodes,this);
            ArithmeticTree.addNodeToAl(nodes,newNode);
        }
        //If newNode is Value
        else if(newNode instanceof Value){
            ArithmeticTree.deleteNodeFromAl(nodes, this);//Delete old one from AL
            replaceNode(newNode,nodes);//Set parents and children
            ArithmeticTree.addNodeToAl(nodes,newNode);//add new node
        }
    }
    /** Sets parent and child in the tree*/
    private void replaceNode(Node newNode, ArrayList<Node> nodes){
        Node[] kids = this.parent.getChildren(); //kids of current parent
        for(int i=0; i<kids.length; i++){//loop through currentNodes parents
            if(kids[i]==this){
                //Replace with this kid
                kids[i]=newNode;
                //Set parent
                newNode.parent=this.parent;
            }
        }
    }

    /**Returns true if Node is an Operator
     * @deprecated
     * */
    @Deprecated
    public boolean isOperator(){
        if(this instanceof Operator){
            return true;
        }else{
            return false;
        }
    }

    /** Populate a given node until all Leafs are Value*/
    public void populateNode(ArrayList<Node> nodes,int depth){
        if(isFull()){
            return;//Node is already full
        }
        else{
            while(!isFull()){//While not full, set all children
                Node newChild = null;
                if(depth<=0)//We are at the bottom of the tree, lets only populate with values
                    newChild = new Value();
                else
                    newChild = new Operator();
                setEmptyChild(newChild);//set new node as child and parent as parent
                nodes.add(newChild);//Add new node to the arraylist
                newChild.populateNode(nodes,depth-1);//Run recursively on new node
            }
        }
    }
}
