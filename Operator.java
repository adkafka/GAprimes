/* Class that reprents a Node that is an operator (+,-,/,* etc)
 */
import java.util.ArrayList;
import java.util.Random;
public class Operator extends Node{
    //////////
    //Fields//
    //////////
    
    private int opIndex;

    public static Random rand = new Random();

    private static final char[] operators = {'+','-','*','/','^'};//Make sure there is a corresponding case call in evaluate()
    private static final int NUM_CHILDREN=2; //How many children th enode should have
    
    ////////////////
    //Constructors//
    ////////////////
    /** Constructor given parent*/
    public Operator(Node parent, int opIndex){
        super(parent, NUM_CHILDREN);
        this.opIndex=opIndex;
    }
    /** Constructor given parent*/
    public Operator(Node parent){
        this(parent,(int)(rand.nextDouble()*operators.length));
    }
    /** Constructor given opIndex*/
    public Operator(int opIndex){
        this(null,opIndex);
    }
    /** Constructor given nothing - Gives random*/
    public Operator(){
        this(null,(int)(rand.nextDouble()*operators.length));
    }
    /** Deep copy */
    public Operator(Operator o){
        this(null,o.opIndex);
    }

    ///////////
    //Methods//
    ///////////
    /** Perform the deep copy */
    public Operator deepCopy() {
        Operator beg = new Operator(this);
        for(int i=0;i<NUM_CHILDREN ;i++ ){//Loop thru all children
            Node n = children[i].deepCopy();
            beg.setEmptyChild(n);
        }
        return beg;
    }

    /** Returns operator as a symbol*/
    public String getSymbol(){
        return Character.toString(operators[opIndex]);
    }
        
    /** Returns the value of this node and its children recursively*/
    public double evaluate(int x){
        if(!isFull()){
            return 0; //Incomplete node - Probable should throw an exception or something
        }
        else{
            Node[] children = getChildren();
            switch (operators[opIndex]){
                case '+': //Add them
                    return children[0].evaluate(x)+children[1].evaluate(x);
                case '-': //Subtract them
                    return children[0].evaluate(x)-children[1].evaluate(x);
                case '*': //Multiply them
                    return children[0].evaluate(x)*children[1].evaluate(x);
                case '/': //Divide them
                    return children[0].evaluate(x)/children[1].evaluate(x);
                case '^': //Raise one to the power of the other
                    return Math.pow(children[0].evaluate(x),children[1].evaluate(x));
                default:
                    return 0;
            }
        }
    }

    @Override
    public String toString(){
        Node[] children = getChildren();
        return "("+children[0].toString()+getSymbol()+children[1].toString()+")";
    }

}
