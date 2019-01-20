import java.util.ArrayList;
import java.util.Random;
import java.util.stream.IntStream;

public class AntColonyOptimization {

//    private double c = 1.0;
//    private double alpha = 1;
//    private double beta = 5;
//    private double evaporation = 0.5;
//    private double Q = 500;
////  private double antFactor = 0.8;
//    private double randomFactor = 0.01;
//
    private int maxIterations = 100;
//
    private int numberOfCities;
    private int numberOfDepots;
//    private int numberOfAnts;
    private double graph[][];
//    private double trails[][];
//    private List<Ant> ants = new ArrayList<>();
    private ArrayList<AntColonySystem> colonies = new ArrayList<>();
    private Random random = new Random();
//    private double probabilities[];
//
//    private int currentIndex;
//
//    private int[] bestTourOrder;
//    private double bestTourLength;
    


    public AntColonyOptimization(int noOfCities, int noOfDepots) {
        graph = generateRandomMatrix(noOfCities, noOfDepots);
        numberOfCities = noOfCities;
        numberOfDepots = noOfDepots;
        
        
        /***
         * Set up the colonies
         */
        for (int i=0; i < noOfDepots; i++) {
        	colonies.add(new AntColonySystem(numberOfCities, numberOfDepots, i, graph));
        }
        
//        numberOfAnts = noOfCities;
//
//        trails = new double[numberOfCities][numberOfCities];
//        probabilities = new double[numberOfCities];
//        IntStream.range(0, numberOfAnts)
//            .forEach(i -> ants.add(new Ant(numberOfCities)));
    }

    /**
     * Generate graph
     */
    public double[][] generateRandomMatrix(int n, int noOfDepots) {
        double[][] randomMatrix = new double[n + noOfDepots][n];
        IntStream.range(0, n+ noOfDepots)
            .forEach(i -> IntStream.range(0, n)
                .forEach(j -> randomMatrix[i][j] = Math.abs(random.nextInt(100) + 1)));
        return randomMatrix;
    }
    
    public void printMatrix() {
    	IntStream.range(0, numberOfCities + numberOfDepots)
    		.forEach(i -> {
    			IntStream.range(0, numberOfCities)
    				.forEach(j -> System.out.print(this.graph[i][j]+"   "));
    			System.out.println("");
    		});	
    }
    
    /**
     * Perform ant optimization
     */
    public void startAntOptimization() {
    	setupColonies();
    	for (AntColonySystem colony : colonies) {
    		for (int i=0; i< maxIterations; i++) {
    			colony.moveAnts();
        		colony.updateTrails();
        		colony.updateBest();
//        		System.out.println("Iteration "+i+":");
//        		colony.printBest();
    		}
    	}
    	for (AntColonySystem colony : colonies) {
    		colony.printBest();
    	}
    	
    }
    
    /***
     * Initial solution
     */
    public void setupColonies() {
    	for (AntColonySystem colony : colonies) {
    		colony.setupAnts();
    		colony.clearTrails();
    	}
    }
    
    public void printAllTrails() {
    	int i=0;
    	for (AntColonySystem colony : colonies) {
    		System.out.println(++i);
    		colony.printTrails();
    	}
    }
    
    public void NN_Heuristic() {
    	
    }
    
    /***
     * Compute total length of all the colonies
     */
    public double totalLength(ArrayList<AntColonySystem> colonies) {
    	double total = 0;
    	for (AntColonySystem colony : colonies) {
    		total += colony.getTourlength();
    	}
    	return total;
    }

}