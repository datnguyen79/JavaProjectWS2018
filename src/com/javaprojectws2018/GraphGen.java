package com.javaprojectws2018;


import com.fxgraph.graph.Graph;
import com.fxgraph.graph.Model;
import com.fxgraph.graph.CellType;
import com.fxgraph.graph.RandomLayout;
import com.fxgraph.graph.Layout;

import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.Scene;



public class GraphGen extends InputMatrix{

    static Graph graph = new Graph();

    public static void algorithm(){

    }
    public static void draw(){
        /*
        int[][] matrix = {{0,1,0,0,1},
                          {1,0,0,1,0},
                          {1,0,1,0,1},
                          {0,0,1,0,1}
        };
        int cities = 5;
        int generator = 4;
        */
        graph = new Graph();

        BorderPane root = new BorderPane();
        root.setCenter(graph.getScrollPane());

        Scene scene = new Scene(root, 1024, 768);

        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Graph drawing...");
        window.setMinWidth(800);
        window.setMinHeight(600);

        window.setScene(scene);
        window.show();
        addGraphComponents();

        Layout layout = new RandomLayout(graph);
        layout.execute();

    }

    private static void addGraphComponents() {

        Model model = graph.getModel();

        graph.beginUpdate();

        model.addCell("Cell A", CellType.RECTANGLE);
        model.addCell("Cell B", CellType.RECTANGLE);
        model.addCell("Cell C", CellType.RECTANGLE);
        model.addCell("Cell D", CellType.TRIANGLE);
        model.addCell("Cell E", CellType.TRIANGLE);
        model.addCell("Cell F", CellType.RECTANGLE);
        model.addCell("Cell G", CellType.RECTANGLE);

        model.addEdge("Cell A", "Cell B");
        model.addEdge("Cell A", "Cell C");
        model.addEdge("Cell B", "Cell C");
        model.addEdge("Cell C", "Cell D");
        model.addEdge("Cell B", "Cell E");
        model.addEdge("Cell D", "Cell F");
        model.addEdge("Cell D", "Cell G");

        graph.endUpdate();

    }


}
