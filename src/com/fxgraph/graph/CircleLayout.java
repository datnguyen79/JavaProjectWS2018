package com.fxgraph.graph;

import java.util.List;

public class CircleLayout extends Layout{
    Graph graph;


    public CircleLayout(Graph graph) {
        this.graph = graph;

    }

    public void execute() {

        List<Cell> cells = graph.getModel().getAllCells();
        List<Edge> edges = graph.getModel().getAllEdges();
        int centerX = 800/2;
        int centerY = 768/2;
        int n = cells.size();
        int radius = 300;
        double alpha = 2*Math.PI / n;
        for(int i = 0; i < n; i++){
            int x = centerX + (int) (Math.cos(i * alpha - Math.PI / 2) * radius);
            int y = centerY + (int) (Math.sin(i * alpha - Math.PI / 2) * radius);
            cells.get(i).relocate(x,y);

        }

    }

}
