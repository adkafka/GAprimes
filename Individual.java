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

    //private static int[] key = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 103, 107, 109, 113, 127, 131, 137, 139, 149, 151, 157, 163, 167, 173, 179, 181, 191, 193, 197, 199, 211, 223, 227, 229, 233, 239, 241, 251, 257, 263, 269, 271, 277, 281, 283, 293, 307, 311, 313, 317, 331, 337, 347, 349, 353, 359, 367, 373, 379, 383, 389, 397, 401, 409, 419, 421, 431, 433, 439, 443, 449, 457, 461, 463, 467, 479, 487, 491, 499, 503, 509, 521, 523, 541};//List of 100 first primes
    private static int[] key = {41, 41, 43, 47, 53, 61, 71, 83, 97, 113, 131, 151, 173, 197, 223, 251, 281, 313, 347, 383, 421, 461, 503, 547, 593, 641, 691, 743, 797, 853, 911, 971, 1033, 1097, 1163, 1231, 1301, 1373, 1447, 1523, 1601, 1681, 1763, 1847, 1933, 2021, 2111, 2203, 2297, 2393, 2491, 2591, 2693, 2797, 2903, 3011, 3121, 3233, 3347, 3463, 3581, 3701, 3823, 3947, 4073, 4201, 4331, 4463, 4597, 4733, 4871, 5011, 5153, 5297, 5443, 5591, 5741, 5893, 6047, 6203, 6361, 6521, 6683, 6847, 7013, 7181, 7351, 7523, 7697, 7873, 8051, 8231, 8413, 8597, 8783, 8971, 9161, 9353, 9547, 9743};//How close can we get to x^2-x+41

    protected static final double PRICE_PER_NODE = 0.20;

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
    /* Copy constructor */
    public Individual(Individual t) {
        equation=new ArithmeticTree(t.equation);
        name=t.name;
        fitness=t.fitness;
    }
    ///////////
    //Methods//
    ///////////
    
    /** Mutate*/
    public void mutate(){
        equation.mutate();
    }
    public void mutateBroad(){
        equation.mutateBroad();
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

    public void checkValid(String s){
        equation.checkValid(s);
    }
    public static void main(String[] args){
        ArithmeticTree t = new ArithmeticTree(new Operator(3));
        t.addNode(new Value(true));
        t.addNode(new Value(true));
        Individual i = new Individual(t);
        System.out.println(i.toString());
    }

}
