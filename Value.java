/* A subclass of Node that represents either a variable or a constant
 */
import java.util.Random;
public class Value extends Node{
    //////////
    //Fields//
    //////////

    public double value;

    public boolean isInput;
    
    private static int meanValue=100;
    private static int stdDeviation=50;

    ////////////////
    //Constructors//
    ////////////////

    /** Constructor given parent, value, and isInput*/
    public Value(Operator parent, double value, boolean isInput){
        super(parent);
        this.isInput=isInput;
        this.value=value;
    }
    /** Constructor given parent*/
    public Value(Operator parent){
        this(parent,Value.randomValue(),false);
    }
    /** Constructor given value and parent*/
    public Value(Operator parent, double value){
        this(parent,value,false);
    }
    /** Constructor parent and isInput*/
    public Value(Operator parent, boolean isInput){
        this(parent,0,isInput);
    }
    /** Constructor given isInput*/
    public Value(boolean isInput){
        this(null,0,isInput);
    }
    /** Constructor given value*/
    public Value(double value){
        this(null,value,false);
    }
    /** Constructor given nothing - defaults to not input*/
    public Value(){
        this(null,Value.randomValue(),false);
    }


    ///////////
    //Methods//
    ///////////
    /** This method returns a random value with Gaussian distribution and mean of meanValue*/
    public static double randomValue(){
        Random rand = new Random();
        return rand.nextGaussian()*stdDeviation+meanValue;
    }
    
    /** Converts it to a string */
    public String getSymbol(){
        if (isInput){
            return "X";
        }else{
            return Double.toString(value); 
        }
    }
    
    /** Returns the value of this node as an int*/
    public double evaluate(int x){
        if(isInput){
            return x;
        }
        else{
            return value;
        }
    }

    @Override
    public String toString(){
        return getSymbol();
    }


}
