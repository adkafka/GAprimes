/* A subclass of Node that represents either a variable or a constant
*/
import java.util.Random;
public class Value extends Node{
    //////////
    //Fields//
    //////////

    private double value;

    private boolean isInput;

    public static Random rand = new Random();

    private static final int NUM_CHILDREN=0; //How many children th enode should have
    private static final int MEAN_VALUE=10;
    private static final int STANDARD_DEVIATION=5;
    private static final double INPUT_FREQUENCY=0.1; //Amount of times a random node should be input

    ////////////////
    //Constructors//
    ////////////////

    /** Constructor given parent, value, and isInput*/
    public Value(Node parent, double value, boolean isInput){
        super(parent, NUM_CHILDREN);
        this.isInput=isInput;
        this.value=value;
    }
    /** Constructor given parent*/
    public Value(Node parent){
        this(parent,Value.randomValue(),false);
    }
    /** Constructor given value and parent*/
    public Value(Node parent, double value){
        this(parent,value,false);
    }
    /** Constructor parent and isInput*/
    public Value(Node parent, boolean isInput){
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
    /** Constructor given nothing - Random value and random isInput*/
    public Value(){
        this(null,Value.randomValue(),Value.randomBoolean());
    }
    /** Deep copy */
    public Value(Value v){
        this(null,v.value,v.isInput);
    }


    ///////////
    //Methods//
    ///////////
    /** Perform the deep copy */
    public Value deepCopy(){
        return new Value(this);
    }

    /** Get a random boolean value*/
    public static boolean randomBoolean(){
        if (rand.nextDouble()>INPUT_FREQUENCY){
            return false;
        }else{
            return true;
        }
    }
    /** This method returns a random value with Gaussian distribution and mean of MEAN_VALUE*/
    public static double randomValue(){
        return rand.nextGaussian()*STANDARD_DEVIATION+MEAN_VALUE;
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
