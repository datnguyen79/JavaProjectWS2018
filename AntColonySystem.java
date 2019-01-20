import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;
import java.util.Random;
import java.util.stream.IntStream;

public class AntColonySystem {
    private double c = 1.0;
    private double alpha = 1;
    private double beta = 5;
    private double evaporation = 0.5;
    private double Q = 500;
    private double randomFactor = 0.01;

    private int numberOfCities;
    private int numberOfDepots;
    private int numberOfAnts = 100;
    private double graph[][];
    private double trails[][];
    private List<Ant> ants = new ArrayList<>();
    private Random random = new Random();
    private double probabilities[];

    private int depot;
    private int currentIndex;

    private int[] bestTourOrder;
    private double bestTourLength;
    
    public AntColonySystem(int noOfCities, int noOfDepots, int depot, double graph[][]) {
    	this.graph = graph.clone();
    	numberOfCities = noOfCities;
    	numberOfDepots = noOfDepots;
    	trails = new double[numberOfCities+1][numberOfCities];
    	probabilities = new double[numberOfCities];
    	
    	this.depot = depot;
    	IntStream.range(0, numberOfAnts)
        	.forEach(i -> ants.add(new Ant(numberOfCities+1)));
    }
    
//    /**
//     * 
//     */
//    public int[] Run() {
//        setupAnts();
//        clearTrails();
//        moveAnts();
//        updateTrails();
//        updateBest();
//        System.out.println("Best tour length: " + (bestTourLength - numberOfCities));
//        System.out.println("Best tour order: " + Arrays.toString(bestTourOrder));
//        return bestTourOrder.clone();
//    }

    /**
     * Prepare ants for the simulation
     */
   public void setupAnts() {
        IntStream.range(0, numberOfAnts)
            .forEach(i -> {
                ants.forEach(ant -> {
                    ant.clear();
                    ant.visitCity(-1, depot);
                });
            });
        currentIndex = 0;
    }

    /**
     * At each iteration, move ants
     */
    public void moveAnts() {
		IntStream.range(currentIndex, numberOfCities)
    	.forEach(i -> {
    		ants.forEach(ant -> {
    			ant.visitCity(currentIndex, selectNextCity(ant));
    		});
    		currentIndex++;
    	});
    }

    /**
     * Select next city for each ant
     */
    public int selectNextCity(Ant ant) {
        int t = random.nextInt(numberOfCities - currentIndex);
        if (random.nextDouble() < randomFactor) {
            OptionalInt cityIndex = IntStream.range(0, numberOfCities)
                .filter(i -> i == t && !ant.visited(i+1))
                .findFirst();
            if (cityIndex.isPresent()) {
                return cityIndex.getAsInt();
            }
        }
        calculateProbabilities(ant);
        double r = random.nextDouble();
        double total = 0;
        for (int i = 0; i < numberOfCities; i++) {
            total += probabilities[i];
            if (total >= r) {
                return i;
            }
        }
        
        throw new RuntimeException("No more city?");
    }


    /**
     * Calculate the next city by picking probabilities
     */
    public void calculateProbabilities(Ant ant) {
        int i = ant.trail[currentIndex];
        double pheromone = 0.0;
        for (int l = 0; l < numberOfCities; l++) {
            if (!ant.visited(l+1)) {
                pheromone += Math.pow(trails[i+1][l], alpha) * Math.pow(1.0 / graph[i+numberOfDepots][l], beta);
            }
        }
        for (int j = 0; j < numberOfCities; j++) {
            if (ant.visited(j+1)) {
            	probabilities[j] = 0.0;
            } else {
                double numerator = Math.pow(trails[i+1][j], alpha) * Math.pow(1.0 / graph[i+numberOfDepots][j], beta);
                probabilities[j] = numerator / pheromone;
            }
        }
    }
    /**
     * Update trails that ants used
     */
    public void updateTrails() {
        for (int i = 0; i < numberOfCities+1; i++) {
            for (int j = 0; j < numberOfCities; j++) {
                trails[i][j] *= evaporation;
            }
        }
        for (Ant a : ants) {
            double contribution = Q / a.trailLength(graph, numberOfDepots);
            for (int i = 0; i < numberOfCities - 1; i++) {
                trails[a.trail[i]][a.trail[i + 1]] += contribution;
            }
            trails[a.trail[numberOfCities - 1]][a.trail[0]] += contribution;
        }
    }

    /**
     * Update the best solution
     */
    public void updateBest() {
        if (bestTourOrder == null) {
            bestTourOrder = ants.get(0).trail;
            bestTourLength = ants.get(0).trailLength(graph, numberOfDepots);
        }
        for (Ant a : ants) {
            if (a.trailLength(graph, numberOfDepots) < bestTourLength) {
                bestTourLength = a.trailLength(graph, numberOfDepots);
                bestTourOrder = a.trail.clone();
            }
        }
    }
    
    public void printBest() {
    	for (int i=0; i<bestTourOrder.length; i++) {
    		if (i==0) {
    			System.out.print("G");
    		} else {
    			System.out.print("D");
    		}
    		System.out.print(bestTourOrder[i]+" ");
    	}
    	System.out.println("Length: " + getTourlength());
    }
    /**
     * Clear trails after simulation
     */
    public void clearTrails() {
    	
        IntStream.range(0, numberOfCities+1)
            .forEach(i -> {
                IntStream.range(0, numberOfCities)
                    .forEach(j -> trails[i][j] = c);
            });
    }
    
    public double getTourlength() {
    	return bestTourLength;
    }
    
    public void printTrails() {
    	for (int i=0; i < numberOfCities+1 ; i++) {
    		for (int j=0; j < numberOfCities; j++) {
    			System.out.print(trails[i][j]+" ");
    		}
    	}
    }
    
}
