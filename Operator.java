/* Class that reprents a Node that is an operator (+,-,/,* etc)
 */

public class Operator extends Node{
    //////////
    //Fields//
    //////////
    
    public Node leftChild;
    public Node rightChild;

    public int opIndex;

    private static char[] operators = {'+','-','*','/','^'};

    ////////////////
    //Constructors//
    ////////////////
    /** Constructor given parent*/
    public Operator(Operator parent, int opIndex){
        super(parent);
        this.opIndex=opIndex;
    }
    /** Constructor given parent*/
    public Operator(Operator parent){
        this(parent,(int)(Math.random()*operators.length));
    }
    /** Constructor given opIndex*/
    public Operator(int opIndex){
        this(null,opIndex);
    }
    /** Constructor given nothing - Gives random*/
    public Operator(){
        this(null,(int)(Math.random()*operators.length));
    }

    ///////////
    //Methods//
    ///////////
    
    /** Returns operator as a symbol*/
    public String getSymbol(){
        return Character.toString(operators[opIndex]);
    }
        
    /** Returns the value of this node and its children recursively*/
    public double evaluate(int x){
        if(leftChild==null || rightChild==null){
            //No leaf here
            return 0;
        }
        switch (operators[opIndex]){
            case '+': //Add them
                return leftChild.evaluate(x)+rightChild.evaluate(x);
            case '-': //Subtract them
                return leftChild.evaluate(x)-rightChild.evaluate(x);
            case '*': //Multiply them
                return leftChild.evaluate(x)*rightChild.evaluate(x);
            case '/': //Divide them
                return leftChild.evaluate(x)/rightChild.evaluate(x);
            case '^': //Raise one to the power of the other
                return Math.pow(leftChild.evaluate(x),rightChild.evaluate(x));
            default:
                return 0;
        }
    }


    @Override
    public String toString(){            
        return "("+leftChild.toString()+getSymbol()+rightChild.toString()+")";
    }

}
