package com.fxgraph.cells;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

import com.fxgraph.graph.Cell;

public class HexagonCell extends Cell {
    static boolean state;

    public HexagonCell(String id) {
        super( id);

        Polygon view = new Polygon( 30,3,10,3,0,20,10,37,30,37,40,20);
        state = true;
        view.setStroke(Color.GREEN);
        view.setFill(Color.GREEN);
        setView( view,id);

    }

}