/* Tree class that represents a mathematical equation
 * Can be evaluated and changed
 */
public class ArithmeticTree{
    //////////
    //Fields//
    //////////

    Node root;//Where the node begins. In this situation, it is the bottom left of the tree

    
    ////////////////
    //Constructors//
    ////////////////


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
    public boolean addNode(Node n, Node addTo){
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
        ArithmeticTree t = new ArithmeticTree();
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
        System.out.println(t.toString());
        System.out.println(t.evaluate(10));
    }
}
