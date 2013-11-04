/* Node class
 * class that represents possible nodes in a tree
 */
public abstract class Node{
    //////////
    //Fields//
    //////////
    
    public Node parent;
    
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
}
