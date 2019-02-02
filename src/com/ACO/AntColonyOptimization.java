package com.ACO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

public class AntColonyOptimization {
	
    private int maxIterations = 1000;

    private int numberOfCities;
    private int numberOfDepots;

    private double graph[][];

    private ArrayList<AntColonySystem> colonies = new ArrayList<>();
    private Random random = new Random();

    private boolean state[];
    private int finalTrail[][];
    private double finalLength;

    public AntColonyOptimization(int noOfCities, int noOfDepots, boolean[] generatorState, Settings settings) {
        graph = generateRandomMatrix(noOfCities, noOfDepots);
        numberOfCities = noOfCities;
        numberOfDepots = noOfDepots;
        
        finalTrail = new int[noOfCities+noOfDepots][noOfCities];
        for (int i=0; i < noOfCities+noOfDepots; i++) {
        	for (int j=0; j<noOfCities; j++) {
        		finalTrail[i][j] = 0;
        	}
        }

        state = generatorState.clone();

        /***
         * Set up the colonies
         */
        for (int i=0; i < noOfDepots; i++) {
        	if (state[i])
        		colonies.add(new AntColonySystem(numberOfCities, numberOfDepots, i, graph, settings));
        }
        
    }

    /**
     * Generate graph
     */
    public double[][] generateRandomMatrix(int n, int noOfDepots) {
        double[][] randomMatrix = new double[n + noOfDepots][n];
        IntStream.range(0, n+ noOfDepots)
            .forEach(i -> IntStream.range(0, n)
                .forEach(j -> randomMatrix[i][j] = Math.abs(random.nextInt(100)) + 1 ));
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
    
//    public void startAntOptimization() {
//        IntStream.rangeClosed(1, 100)
//        	.forEach(i -> {
//        		System.out.println("Attempt #" + i);
//				solve();
//        	});
//    }
    
    /**
     * Perform ant optimization
     */
    public int[][] solve() {
    	setupColonies();
    	for (AntColonySystem colony : colonies) {
    		colony.clearTrails();
    		for (int i=0; i< maxIterations; i++) {
    			colony.moveAnts();
        		colony.updateTrails();
        		colony.updateBest();
    		}
    	}
    	for (AntColonySystem colony : colonies) {
    		colony.printColonyBest();
    	}
    	findBest();
    	printBest();
    	return finalTrail;
    }
    
    /***
     * Initial solution
     */
    public void setupColonies() {
    	for (AntColonySystem colony : colonies) {
    		colony.clearTrails();
    		colony.setupAnts();
    	}
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

	public void findBest() {
		double curFinalLength = 0.0;
		double currentBestLength = 0.0;
		int currentBest[][] = Arrays.copyOf(finalTrail, finalTrail.length);
		int curFinalTrail[][] = Arrays.copyOf(finalTrail, finalTrail.length);
		for (int city = 0; city<numberOfCities; city++) {
			for (AntColonySystem colony : colonies) {
				int tempGraph[][] = Arrays.copyOf(finalTrail, finalTrail.length);
				for (int i = 0; i<colony.getBestTourOrder().length; i++) {
					if (i==0) {
						for (int I=0; I<numberOfCities+numberOfDepots; I++) {
							tempGraph[I][colony.getBestTourOrder()[i+1]] = 0;
						}
						tempGraph[colony.getBestTourOrder()[i]][colony.getBestTourOrder()[i+1]] = 1;
					} else {
						if (colony.getBestTourOrder()[i]==city)
							break;
						for (int I=0; I<numberOfCities+numberOfDepots; I++) {
							tempGraph[I][colony.getBestTourOrder()[i+1]] = 0;
						}
						tempGraph[colony.getBestTourOrder()[i]+numberOfDepots][colony.getBestTourOrder()[i+1]] = 1;
					}
				}
				double tempLength = computeLength(tempGraph);
				if (currentBestLength == 0.0 || tempLength <= currentBestLength) {
					currentBestLength = tempLength;
					currentBest = tempGraph.clone();
				}
			}
			finalTrail = Arrays.copyOf(currentBest, currentBest.length);
		}

		curFinalLength =  computeLength(finalTrail);
		for (AntColonySystem colony : colonies) {
			if (colony.getTourlength() < curFinalLength) {
				curFinalLength = colony.getTourlength();
				for (int i = 0; i<colony.getBestTourOrder().length-1; i++) {
					if (i==0) {
						for (int I=0; I<numberOfCities+numberOfDepots; I++) {
							curFinalTrail[I][colony.getBestTourOrder()[i+1]] = 0;
						}
						curFinalTrail[colony.getBestTourOrder()[i]][colony.getBestTourOrder()[i+1]] = 1;
					} else {
						for (int I=0; I<numberOfCities+numberOfDepots; I++) {
							curFinalTrail[I][colony.getBestTourOrder()[i+1]] = 0;
						}
						curFinalTrail[colony.getBestTourOrder()[i]+numberOfDepots][colony.getBestTourOrder()[i+1]] = 1;
					}
				}
			}
		}

		if (curFinalLength < finalLength || finalLength == 0.0) {
			finalLength = curFinalLength;
			finalTrail = Arrays.copyOf(curFinalTrail, curFinalTrail.length);
		}

	}

	public void printBest() {
		System.out.println("Best solution: " + finalLength);
		for (int i=0; i< numberOfCities + numberOfDepots; i++) {
			for (int j=0; j<numberOfCities; j++) {
				System.out.print(finalTrail[i][j]+" ");
			}
			System.out.println("");
		}
	}

	public double computeLength(int[][] trail) {
    	double length = 0.0;
    	for (int i=0; i< numberOfCities + numberOfDepots; i++) {
    		for (int j=0; j<numberOfCities; j++) {
    			length += graph[i][j]*trail[i][j];
    		}
    	}
    	return length;
    }

}