/* Node class
 * class that represents possible nodes in a tree
 */
public abstract class Node{
    //////////
    //Fields//
    //////////
    
    public Node parent;

    private static final double opFreq = 0.4;//Frequency that when a random Node is created, it will be an operator
    
    ////////////////
    //Constructors//
    ////////////////
    /** No input constructor*/
    public Node(){
        parent=null;
    }
    
    /** Constructor given parent*/
    public Node(Operator parent){
        this();
        if (parent!=null){
            if (parent.leftChild==null){
                parent.leftChild=this;
                this.parent=parent;
            }else if (parent.rightChild==null){
                parent.rightChild=this;
                this.parent=parent;
            }
        }
    }

    ///////////
    //Methods//
    ///////////
    /** Get symbol*/
    public abstract String getSymbol();
    
    /** Evaluate the equation given an input */
    public abstract double evaluate(int x);
    
    /** Create and return a random Node, could be Operator or Value - freq decided by opFreq */
    public static Node randomNode(){
        if (Math.random()>opFreq){//create a Value
            return new Value();
        }else{
            return new Operator();
        }
    }

    /**Returns true if Node is an Operator*/
    public boolean isOperator(){
        if(this instanceof Operator){
            return true;
        }else{
            return false;
        }
    }

    /** Populate a given node until all Leafs are Value*/
    public void populateNode(){
        //System.out.println("PopulateNode - "+this.getSymbol());
        //Check if it is an Operator
        if (this instanceof Operator){
            Operator op = (Operator)this;
            //Check if leftChild is null
            if (op.leftChild==null){
                op.leftChild=Node.randomNode();//Creates random node
                op.leftChild.parent=this;
                op.leftChild.populateNode();//Run again with new node
            }
            //Check if rightChild is null
            if (op.rightChild==null){
                op.rightChild=Node.randomNode();//Creates random node
                op.rightChild.parent=this;
                op.rightChild.populateNode();//Run again with new node
            }
            return;//Node is filled
        }else{
            return;//Node is a Value so it is filled by design
        }
    }
}
