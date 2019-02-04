package com.ACO;

public class Ant {

	protected int trailSize;
	protected int trail[];
	protected boolean visited[];

	/**
	 * @param tourSize the size of the path that the ants will travel
	 */
	public Ant(int tourSize) {
		this.trailSize = tourSize;
		this.trail = new int[tourSize];
		this.visited = new boolean[tourSize];
	}

	/**
	 * @param currentIndex the current index of the ant on its path
	 * @param city the city number the ant visit
	 */
	protected void visitCity(int currentIndex, int city) {
		trail[currentIndex + 1] = city;
		if (currentIndex==-1) {
			visited[0] = true;
		} else {
			visited[city+1] = true;
		}
	}

	/**
	 * @param i index of the city
	 * @return true/false depends on the whether the city has been visited or not
	 */
	protected boolean visited(int i) {
		return visited[i];
	}

	/**
	 * @param graph the cost matrix
	 * @param noOfDepots the number of depots
	 * @return the length of the trail the Ant traveled
	 */
	protected double trailLength(double graph[][], int noOfDepots) {
		double length = graph[trail[0]][trail[1]];
		for (int i = 1; i < trailSize - 1; i++) {
			length += graph[trail[i]+noOfDepots][trail[i + 1]];
		}
		return length;
	}

	protected void clear() {
		for (int i = 0; i < trailSize; i++)
			visited[i] = false;
	}

}
