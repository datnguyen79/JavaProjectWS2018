package com.fxgraph.graph;

import com.fxgraph.cells.HexagonCell;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;


public class MouseGestures {

    final DragContext dragContext = new DragContext();

    Graph graph;

    public MouseGestures( Graph graph) {
        this.graph = graph;
    }

    public void makeInteractable( final Node node) {

        node.setOnMousePressed(onMousePressedEventHandler);
        node.setOnMouseDragged(onMouseDraggedEventHandler);
        node.setOnMouseClicked(onMouseClickedEventHandler);

    }

    EventHandler<MouseEvent> onMousePressedEventHandler = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent event) {
            if (event.getButton() == MouseButton.PRIMARY) {
                Node node = (Node) event.getSource();

                double scale = graph.getScale();

                dragContext.x = node.getBoundsInParent().getMinX() * scale - event.getScreenX();
                dragContext.y = node.getBoundsInParent().getMinY() * scale - event.getScreenY();
            }

        }
    };

    EventHandler<MouseEvent> onMouseDraggedEventHandler = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent event) {
            if (event.getButton() == MouseButton.PRIMARY) {
                Node node = (Node) event.getSource();

                double offsetX = event.getScreenX() + dragContext.x;
                double offsetY = event.getScreenY() + dragContext.y;

                // adjust the offset in case we are zoomed
                double scale = graph.getScale();

                offsetX /= scale;
                offsetY /= scale;

                node.relocate(offsetX, offsetY);
            }

        }
    };

    EventHandler<MouseEvent> onMouseClickedEventHandler = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent event) {
            if (event.getButton() == MouseButton.SECONDARY) {
                HexagonCell hexagonCell = (HexagonCell) event.getSource();

                for (Cell c : graph.getModel().allCells) {
                    if (c.getCellId() == hexagonCell.getCellId()) {
                        c.setState();
                    }
                }
            }
        }
    };

    class DragContext {

        double x;
        double y;

    }
}