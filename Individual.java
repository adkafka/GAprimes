public class Individual {
    //////////
    //Fields//
    //////////

    private ArithmeticTree equation; //Genome

    private int fitness; //Numerical representation of the fitness
    private static int[] key = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 103, 107, 109, 113, 127, 131, 137, 139, 149, 151, 157, 163, 167, 173, 179, 181, 191, 193, 197, 199, 211, 223, 227, 229, 233, 239, 241, 251, 257, 263, 269, 271, 277, 281, 283, 293, 307, 311, 313, 317, 331, 337, 347, 349, 353, 359, 367, 373, 379, 383, 389, 397, 401, 409, 419, 421, 431, 433, 439, 443, 449, 457, 461, 463, 467, 479, 487, 491, 499, 503, 509, 521, 523, 541};//List of 100 first primes

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
    }

    ///////////
    //Methods//
    ///////////
    
    /** Calculates fitness of AT */
    public void calculateFitness(){
        fitness = 0;//set fitness at 0 to begin
        for(int i=0; i<key.length; i++){//Calculate squared distance from each entry in key
            double val = equation.evaluate(i);
            double deltaSquared = Math.pow((val-key[i]),2);
            fitness+=(int)deltaSquared;
            System.out.println(i+": val="+val+" deltaSquared="+deltaSquared);
            
        }

    }

    /** Getter for fitness */
    public int getFitness(){
        return fitness;
    }

    /** Returns a nice string representing the Individual */
    @Override
    public String toString(){
        return (fitness+"\t\t\t"+equation.toString());
    }

    /** Main method */
    public static void main(String[] args){
        ArithmeticTree.setRandSeed(18);
        ArithmeticTree t = new ArithmeticTree();
        System.out.println(t.toString());
        System.out.println(t.evaluate(2));
        t.foldConstants();
        System.out.println(t.toString());
        for(Node n : t.nodes){
            System.out.print(n.getSymbol()+", ");
        }System.out.println();
        System.out.println(t.evaluate(2));
        System.out.println(t.nodes.size()+"\t"+t.count());
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
        //Individual i = new Individual();
        //System.out.println(i.toString());
    }
}
