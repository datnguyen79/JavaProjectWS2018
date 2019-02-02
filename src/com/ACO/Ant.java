package com.ACO;

public class Ant {

	protected int trailSize;
	protected int trail[];
	protected boolean visited[];

	public Ant(int tourSize) {
		this.trailSize = tourSize;
		this.trail = new int[tourSize];
		this.visited = new boolean[tourSize];
	}

	protected void visitCity(int currentIndex, int city) {
		trail[currentIndex + 1] = city;
		if (currentIndex==-1) {
			visited[0] = true;
		} else {
			visited[city+1] = true;
		}
	}

	protected boolean visited(int i) {
		return visited[i];
	}

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
