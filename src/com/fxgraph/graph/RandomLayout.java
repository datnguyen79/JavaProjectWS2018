package com.fxgraph.graph;

import java.util.List;
import java.util.Random;

import com.fxgraph.graph.Cell;
import com.fxgraph.graph.Graph;
import com.fxgraph.graph.Layout;

public class RandomLayout extends Layout{

    Graph graph;

    Random rnd = new Random();

    public RandomLayout(Graph graph) {

        this.graph = graph;

    }

    public void execute() {

        List<Cell> cells = graph.getModel().getAllCells();

        for (Cell cell : cells) {

            double x = rnd.nextDouble() * 1024;
            double y = rnd.nextDouble() * 768;

            cell.relocate(x, y);

        }

    }

}