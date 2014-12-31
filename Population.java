/*
 * This class represents a population of individuals. This is where the evolution occurs
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Population{
    //////////
    //Fields//
    //////////
    private Individual[] pop;//The population of individuals

    private Individual mostFit;//The most fit individual yet

    private int generation;//What generation we are on

    private double avg;//Average fitness (Not including NaN and Infinities)
    private double variance;//Variance (stdDev^2) of fitness (Not including NaN and Infinities)

    private static Random rand = new Random();

    private static final int DEFAULT_POP_SIZE=30;//How many individuals in the population

    private static final int ELITISM_SIZE=1;//Number of indivisuals who will survive unaltered (most fit indivuals)

    private static final double MUTATION_RATE=0.3;//Rate at which individuals mutate (1 to always mutate, 0 to never)

    private static final double BROAD_MUTATION_RATE=0.05;//Rate at which individuals mutate using mutateBroad (1 to always mutate, 0 to never)

    ////////////////
    //Constructors//
    ////////////////

    /* Constructor for Population */
    public Population(){
        pop = new Individual[DEFAULT_POP_SIZE];
        generation=0;
        mostFit=pop[0];
        for(int i=0; i<pop.length; i++){
            pop[i] = new Individual();
        }
        sortByFitness();
        mostFit=pop[0];

    }

    /* Constructor for Population given pop*/
    public Population(Individual[] population){
        this();
        pop=population;
        sortByFitness();
        mostFit=pop[0];

    }

    ///////////
    //Methods//
    ///////////

    /** Getter for pop */
    public Individual[] getPop(){
        return pop;
    }

    /** Select a parent with highest fitness leading to most children (probabalisticly) */
    private Individual selectParent(){
        int cutoff = rand.nextInt(pop.length)+1;
        int index = rand.nextInt(cutoff);
        return pop[index];
    }

    /** Sort the population by fitness */
    public void sortByFitness(){
        Arrays.sort(pop);
    }

    /** Calculates the average fitness for the generation */
    public void calculateStats(){
        avg=0.0;
        variance=0.0;
        int notNaNs = 0;//How many individuals to count by
        for(Individual i : pop){
            Double fit = i.getFitness();
            if(! (fit.isNaN() || fit.isInfinite())){
                avg+=fit;
                notNaNs++;
            }
        }
        avg/=notNaNs;
        //Now variance
        for(Individual i : pop){
            Double fit = i.getFitness();
            if(! (fit.isNaN() || fit.isInfinite())){
                variance+=Math.pow(fit-avg,2);
            }
        }
        variance=variance/notNaNs;

    }

    /** Set random seed */
    public static void setRandSeed(int seed){
        rand.setSeed(seed);
        ArithmeticTree.setRandSeed(seed);
    }

    /** Returns a text string of the pop */
    @Override
    public String toString(){
        StringBuilder s = new StringBuilder(1024);
        s.append("+------------------+------------------+\n");
        s.append(String.format("| %7s: %7d | %8s: %6d |\n", "PopSize", DEFAULT_POP_SIZE, "NumElite", ELITISM_SIZE ));
        s.append(String.format("| %7s: %1.5f | %8s: %6d |\n", "MutRate", MUTATION_RATE, "MaxNode", ArithmeticTree.MAX_NODES));
        s.append(String.format("| %6s: %1.6f | %7s: %1.5f |\n", "OpFreq", Node.OP_FREQ, "Price/N", Individual.PRICE_PER_NODE ));
        s.append("+------------+------------------------+\n");
        s.append(String.format("| GEN: %5d | AVG_FIT: %13.2f |\n",generation,avg));
        s.append(String.format("| VARIATION: %8.5f |\n",variance));
        System.out.println(variance);
        s.append("+------------+------------------------+\n");
        s.append("|    NAME    |         FITNESS        |\n");
        s.append("+------------+------------------------+\n");
        for(Individual i : pop){
            s.append(String.format("| %10d | %13.2f | %6d |\n",i.name(),i.getFitness(),i.getEquation().size()));
            //i.checkValid("At printout");
        }
        s.append("+------------+------------------------+\n");
        return s.toString();
    }

    /** Performs constant folding on all Individuals */
    public void foldConstants(){
        for(Individual i : pop){
            System.out.println(i.toString());
            i.foldConstants();
        }
    }

    /** Performs crossover between two Individuals - will use root from i1 */
    public static Individual crossover(Individual i1, Individual i2){
        return new Individual(i1.getEquation().crossover(i2.getEquation()));
    }

    /** Evolves one generation of the population */
    public void evolve(){
        generation++;
        sortByFitness();//Sort
        ArrayList<Individual> newPop = new ArrayList<Individual>();//New population
        if(generation%100==0){//Every 100 gens, add the most fit one back into the pop
            newPop.add(new Individual(mostFit));
        }
        for(int i =0; i<ELITISM_SIZE; i++){//These move on to the next generation unaltered (may mutate later)
            newPop.add(pop[i]);
        }
        while(newPop.size() < pop.length){//While pop is not full
            Individual i1 = selectParent();
            Individual i2 = null;
            do{//Two different parents
                i2 = selectParent();
            }while(i1==i2);//Until two different parents
            Individual child = crossover(i1,i2);
            newPop.add(child);
        }
        for(int i=0; i<newPop.size(); i++){
            double r = rand.nextDouble();
            while(r < MUTATION_RATE){//Mutate, possibly multiple times for one individual
                newPop.get(i).mutate();
                newPop.get(i).calculateFitness();
                r = rand.nextDouble();
            }
            r = rand.nextDouble();
            while(r < BROAD_MUTATION_RATE){//Mutate broadly, possibly multiple times for one individual
                newPop.get(i).mutateBroad();
                newPop.get(i).calculateFitness();
                r = rand.nextDouble();
            }
        }
        pop=newPop.toArray(new Individual[0]);
        if(pop[0].getFitness() < mostFit.getFitness()){//Set most fit in all time
            mostFit=new Individual(pop[0]);//Deep copy
        }
        calculateStats();
    }


    public static void main(String[] args){
        //ArrayList<Individual> top = new ArrayList<Individual>();
        //for(int s = 1; s<50; s++){
            setRandSeed(10);
            //setRandSeed(s);
            //System.err.println("Seed: "+s);
            Population p = new Population();

            for(int i = 0; i<500000; i++){

                //System.out.println(i);
                if(i%1000==0){
                    p.sortByFitness();
                    p.foldConstants();
                    p.calculateStats();
                    System.out.println(p.toString());
                    System.out.println(p.mostFit);
                }
                p.evolve();
            }
            //Done, clean things up and display
            p.sortByFitness();
            p.foldConstants();
            System.out.println(p.toString());
            //top.add(p.mostFit);
        //}
        //Population fi = new Population(top.toArray(new Individual[top.size()]));
        //System.out.println(fi);
        //System.out.println("Most fit...");
        //System.out.println(fi.mostFit);
    }
}
