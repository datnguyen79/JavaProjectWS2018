package com.fxgraph.graph;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

public class Edge extends Pane {

    protected Cell source;
    protected Cell target;
    protected double w;

    Line line;
    Text weight;

    public Edge(Cell source, Cell target, double w) {

        this.source = source;
        this.target = target;
        this.w = w;

        source.addCellChild(target);
        target.addCellParent(source);

        line = new Line();
        weight = new Text();

        //Styling for edge
        line.setStrokeWidth(3);
        line.setStyle("-fx-stroke: #c6c6c6");
        weight.setId("textWithStroke");

        line.startXProperty().bind( source.layoutXProperty().add(source.getBoundsInParent().getWidth() / 2.0));
        line.startYProperty().bind( source.layoutYProperty().add(source.getBoundsInParent().getHeight() / 2.0));

        line.endXProperty().bind( target.layoutXProperty().add( target.getBoundsInParent().getWidth() / 2.0));
        line.endYProperty().bind( target.layoutYProperty().add( target.getBoundsInParent().getHeight() / 2.0));

        weight.setText(Double.toString(w));
        weight.layoutXProperty().bind(line.endXProperty().subtract(line.endXProperty().subtract(line.startXProperty()).divide(2)));
        weight.layoutYProperty().bind(line.endYProperty().subtract(line.endYProperty().subtract(line.startYProperty()).divide(2)));

        getChildren().addAll(line, weight);

    }

    public void setStatus(boolean status){
        if(weight != null && line != null){
            if(status){
                line.setOpacity(1.0);
                weight.setOpacity(1.0);
            }else{
                line.setOpacity(0.0);
                weight.setOpacity(0.0);
            }
        }

    }

    public Cell getSource() {
        return source;
    }

    public Cell getTarget() {
        return target;
    }

}
