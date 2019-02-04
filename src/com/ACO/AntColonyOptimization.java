package com.ACO;

import java.util.ArrayList;
import java.util.Arrays;

public class AntColonyOptimization {

    private int numberOfCities;
    private int numberOfDepots;

    private double graph[][];

    private ArrayList<AntColonySystem> colonies = new ArrayList<>();

    private boolean state[];
    private int finalTrail[][];
    private double finalLength;

	/**
	 * @param graph the cost matrix
	 * @param generatorState array of boolean for the state of the generators
	 */
    public AntColonyOptimization(int noOfCities, int noOfDepots, double[][] graph, boolean[] generatorState, Settings settings) {
        this.graph = Arrays.copyOf(graph, graph.length);
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
     * Perform ant optimization
     */
    public int[][] solve() {
    	setupColonies();
    	for (AntColonySystem colony : colonies) {
    		colony.clearTrails();
    		colony.moveAnts();
    		colony.updateTrails();
    		colony.updateBest();
    	}

    	findBest();

    	return finalTrail;
    }
    
    /***
     * Setup each colony in each depot
     */
    public void setupColonies() {
    	for (AntColonySystem colony : colonies) {
    		colony.clearTrails();
    		colony.setupAnts();
    	}
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

	public double computeLength(int[][] trail) {
    	double length = 0.0;
    	for (int i=0; i< numberOfCities + numberOfDepots; i++) {
    		for (int j=0; j<numberOfCities; j++) {
    			length += graph[i][j]*trail[i][j];
    		}
    	}
    	return length;
    }

    public double getFinalLength() {
    	return this.finalLength;
	}

}