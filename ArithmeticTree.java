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
        System.out.println("Adding "+n.value);
        //If it is new tree, set startNode
        if(root==null){
            root=n;
            return;
        }
        addNode(n,root);
    }

    /** Adds a node to the tree recursively - Stored by association*/
    public boolean addNode(Node n, Node addTo){
        if(!addTo.isOperator()){//If it not an operator, it can't add here
            return false;
        }
        else if(addTo.leftChild==null){//If can add to left node
            System.out.println("Adding "+n.value+" to "+addTo.value);
            n.parent=addTo;
            addTo.leftChild=n;
            return true;
        }else if(addTo.rightChild==null){//If can add to right node
            System.out.println("Adding "+n.value+" to "+addTo.value);
            n.parent=addTo;
            addTo.rightChild=n;
            return true;
        }else{
            if(addNode(n,addTo.leftChild)){//Try left child
                return true;
            }else if(addNode(n,addTo.rightChild)){//Try right child
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
        /*
        //Loop thru the tree saying each node
        Node n = root;
        Node end = root;
        while(n.operator){ //Find bottom left node
            n=n.leftChild;
        }
        
        while(n.operator){ //Find bottom left node
            n=n.rightChild;
        }

        //Starting at bottom left, output subtree
        Node l = n;
        while(l!=n){//Until it hit the bottom right leaf
            if (l.operator){
                System.out.println(l.value);
            }
            else{
                System.out.println("("+l.value+l.parent.value+l.parent.rightChild.value+")");
            }
        }
        */

    }

    //Main method
    public static void main(String[] args){
        ArithmeticTree t = new ArithmeticTree();
        t.addNode(new Node("/",true));
        t.addNode(new Node("+",true));
        t.addNode(new Node("12",false));
        t.addNode(new Node("+",true));
        t.addNode(new Node("7",false));
        t.addNode(new Node("*",true));
        t.addNode(new Node("*",true));
        t.addNode(new Node("X",false));
        t.addNode(new Node("X",false));
        t.addNode(new Node("3",false));
        t.addNode(new Node("X",false));
        System.out.println(t.toString());
    }
}
