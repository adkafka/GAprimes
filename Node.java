/* Node class
 * class that represents possible nodes in a tree
 */
import java.util.ArrayList;
public abstract class Node{
    //////////
    //Fields//
    //////////
    
    private Node[] children;
    private Node parent;
    private int numChildren;

    private static final double OP_FREQ = 0.4;//Frequency that when a random Node is created, it will be an operator


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
    public Node(Operator parent, int numChildren){
        this(numChildren);
        if(parent!=null){
            parent.setEmptyChild(this);
        }
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

    /** Get symbol*/
    public abstract String getSymbol();
    
    /** Evaluate the equation given an input */
    public abstract double evaluate(int x);
    
    /** Create and return a random Node, could be Operator or Value - freq decided by OP_FREQ */
    public static Node randomNode(){
        if (Math.random()>OP_FREQ){//create a Value
            return new Value();
        }else{
            return new Operator();
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
    public void populateNode(ArrayList<Node> nodes){
        if(isFull()){
            return;//Node is already full
        }
        else{
            while(!isFull()){//While not full, set all children
                Node newChild = Node.randomNode();//Create new random node
                setEmptyChild(newChild);//set new node as child and parent as parent
                nodes.add(newChild);//Add new node to the arraylist
                newChild.populateNode(nodes);//Run recursively on new node
            }
        }
    }
}
