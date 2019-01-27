package com.fxgraph.cells;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

import com.fxgraph.graph.Cell;

public class TriangleCell extends Cell {

    public TriangleCell( String id) {
        super( id);

        double width = 30;
        double height = 30;

        Polygon view = new Polygon( width / 2, 0, width, height, 0, height);

        view.setStroke(Color.RED);
        view.setFill(Color.RED);


        setView( view);

    }

}