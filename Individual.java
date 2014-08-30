/*
 * This class represents a single individual with an arithmetic tree
 */
public class Individual implements Comparable{
    //////////
    //Fields//
    //////////

    private ArithmeticTree equation; //Genome

    private double fitness; //Numerical representation of the fitness

    private int name;//Name of individual

    private static int num = 0;//How many have been generated

    private static int[] key = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 103, 107, 109, 113, 127, 131, 137, 139, 149, 151, 157, 163, 167, 173, 179, 181, 191, 193, 197, 199, 211, 223, 227, 229, 233, 239, 241, 251, 257, 263, 269, 271, 277, 281, 283, 293, 307, 311, 313, 317, 331, 337, 347, 349, 353, 359, 367, 373, 379, 383, 389, 397, 401, 409, 419, 421, 431, 433, 439, 443, 449, 457, 461, 463, 467, 479, 487, 491, 499, 503, 509, 521, 523, 541};//List of 100 first primes

    private static final double PRICE_PER_NODE = 10.0;

    ////////////////
    //Constructors//
    ////////////////

    /* Constructor for Individual*/
    public Individual() {
        this(new ArithmeticTree());
    }

    /* Constructor for Individual given ArithmeticTree */
    public Individual(ArithmeticTree t){
        equation = t;
        calculateFitness();
        num++;
        name=num;
    }

    ///////////
    //Methods//
    ///////////
    
    /** Mutate*/
    public void mutate(){
        equation.mutate();
    }

    /** Fold constants */
    public void foldConstants(){
        equation.foldConstants();
    }

    /** Getter for equation */
    public ArithmeticTree getEquation(){
        return equation;
    }

    /** Compare two individuals based on their fitness **/
    public int compareTo(Object i2){
        if(i2 instanceof Individual){
            return Double.compare(fitness,((Individual)i2).fitness);
        }else{
            System.err.println("Incomparable types");
            return Integer.MAX_VALUE;
        }
    }

    /** Calculates fitness of AT */
    public void calculateFitness(){
        fitness = 0.0;//set fitness at 0 to begin
        for(int i=0; i<key.length; i++){//Calculate squared distance from each entry in key
            double val = equation.evaluate(i);
            double deltaSquared = Math.pow((val-key[i]),2);
            fitness+=deltaSquared;
            //System.out.println(i+": val="+val+" deltaSquared="+deltaSquared);
            fitness+=equation.size()*PRICE_PER_NODE;
            
        }
    }

    /** Getter for fitness */
    public double getFitness(){
        return fitness;
    }

    /** Getter for name */
    public int name(){
        return name;
    }

    /** Returns a nice string representing the Individual */
    @Override
    public String toString(){
        return (name+"\t\t\t"+fitness+"\t\t\t"+equation.toString());
    }

    /** Main method */
    public static void main(String[] args){
        ArithmeticTree.setRandSeed(20);
        //ArithmeticTree t = new ArithmeticTree();
        //System.out.println(t.toString());
        //System.out.println(t.evaluate(2));
        /*
        for(int i = 0; i<10000; i++){
            ArithmeticTree.setRandSeed(i);
            ArithmeticTree t = new ArithmeticTree();
            for(int j = 0; j<100; j++){
                double ans = t.evaluate(0);
                Double Ans = new Double(ans);
                if(Ans.isNaN()){
                    System.err.println("NaN - seed: "+i+" and eval: "+j+" failed");
                }
                else if(Ans.isInfinite()){
                    System.err.println("Infinite - seed: "+i+" and eval: "+j+" failed");
                }
            }
        }
        */

        Individual i = new Individual();
        Individual j = new Individual();
        System.out.println(i.fitness);
        System.out.println(j.fitness);
        System.out.println(i.compareTo(i));
        /*
        Individual i = new Individual();
        System.out.println(i.toString());
        */
    }
}
