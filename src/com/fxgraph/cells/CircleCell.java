package com.fxgraph.cells;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import com.fxgraph.graph.Cell;

public class CircleCell extends Cell {

    public CircleCell(String id) {
        super( id);

        //Rectangle view = new Rectangle( 30,30);
        Circle view = new Circle(20, 20, 20);

        view.setStroke(Color.DODGERBLUE);
        view.setFill(Color.DODGERBLUE);

        setView( view);

    }

}