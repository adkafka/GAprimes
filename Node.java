/* Node class
 * Abstract class that represents possible nodes in a tree
 */
public class Node{
    //temporary
    private boolean operator;
    public String value;

    public boolean isOperator(){ return operator; }

    public Node leftChild;
    public Node rightChild;
    public Node parent;
    
    public Node(String value, boolean operator){
        this.value=value;
        this.operator=operator;
        leftChild=null;
        rightChild=null;
        parent=null;
    }


    @Override
    public String toString(){
        if(operator){
            return leftChild.toString()+value+rightChild.toString();
        }else{
            return value;
        }
    }

}
