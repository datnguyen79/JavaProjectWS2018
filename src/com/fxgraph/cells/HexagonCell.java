package com.fxgraph.cells;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

import com.fxgraph.graph.Cell;

public class HexagonCell extends Cell {
    static boolean state;

    private boolean state;
    private Polygon view = new Polygon( 30,3,10,3,0,20,10,37,30,37,40,20);

    public HexagonCell(String id) {
        super( id);
        state = true;

        this.view.setStroke(Color.GREEN);
        this.view.setFill(Color.GREEN);

        setView( this.view,id);

    }

    @Override
    public void setState() {
        this.state = !this.state;
        changeColor();
    }

    public void changeColor() {
        if (state) {
            view.setStroke(Color.GREEN);
            view.setFill(Color.GREEN);

            setView( view, this.getCellId());
        } else {
            view.setStroke(Color.RED);
            view.setFill(Color.RED);

            setView( view, this.getCellId());
        }

    }

}