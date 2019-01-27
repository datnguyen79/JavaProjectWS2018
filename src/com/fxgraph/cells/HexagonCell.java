package com.fxgraph.cells;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

import com.fxgraph.graph.Cell;

public class HexagonCell extends Cell {

    public HexagonCell(String id) {
        super( id);

        //double width = 30;
        //double height = 30;

        Polygon view = new Polygon( 30,3,10,3,0,20,10,37,30,37,40,20);

        view.setStroke(Color.RED);
        view.setFill(Color.RED);


        setView( view,id);

    }

}