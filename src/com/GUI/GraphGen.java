package com.GUI;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import com.fxgraph.graph.Cell;
import com.fxgraph.graph.Graph;
import com.fxgraph.graph.Layout;
import com.fxgraph.graph.RandomLayout;
import com.fxgraph.graph.CellType;
import com.fxgraph.graph.Model;

import java.util.List;

public class GraphGen extends InputMatrix{

    static Graph graph = new Graph();
    static Model model = graph.getModel();

<<<<<<< HEAD
    public static void canvas(){
=======

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
>>>>>>> ACO
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Graph drawing...");
        window.setMinWidth(800);
        window.setMinHeight(600);

        BorderPane root = draw();
        Scene scene = new Scene(root, 1024, 768);
        window.setScene(scene);
        window.show();
    }
    public static BorderPane draw(){

        BorderPane root = new BorderPane();
        root.setCenter(graph.getScrollPane());


        Layout layout = new RandomLayout(graph);
        layout.execute();


        int[][] matrix =
                {{0,1,0,0,1},
                {1,0,0,1,0},
                {1,0,1,0,1},
                {0,0,1,0,1}
        };
        initBaseGraph();
        //updateGraph(matrix);
        layout.execute();

        return root;
    }

    private static void initBaseGraph() {

        //Model model = graph.getModel();

        graph.beginUpdate();

        int cities = 5;
        int generator = 4;

<<<<<<< HEAD
        for (int i = 0; i < cities; i++) {
            model.addCell("C" + i, CellType.CIRCLE);
        }

        for (int i = 0; i < generator; i++) {
            model.addCell("G" + i, CellType.HEXAGON);
        }

        for (int i = 0; i < generator; i++) {
            for (int j = 0; j < cities; j++) {
                model.addEdge("G"+i, "C"+j);
            }
        }
=======
        for(int i = 0; i < cities; i++){
            model.addCell("D"+i, CellType.CIRCLE);

        }

        for(int i = 0; i < generator; i++){
            model.addCell("G"+i, CellType.HEXAGON);
        }

        for(int i = 0; i < generator; i++){
            for(int j = 0; j < cities; j++) {
                if(matrix[i][j] == 1){
                    model.addEdge("G"+i,"D"+j, 10);
                }
            }
        }

//        for(int i = generator; i < cities; i++){
//            for(int j = 0; j < cities; j++) {
//                if(matrix[i][j] == 1){
//                    model.addEdge("D"+i,"D"+j);
//                }
//            }
//        }

>>>>>>> ACO
        graph.endUpdate();
    }

    private static void updateGraph(int[][] matrix) {


        graph.beginUpdate();

        int cities = 5;
        int generator = 4;

        for (int i = 0; i < cities; i++) {
            model.addCell("City_" + i, CellType.CIRCLE);
        }

        for (int i = 0; i < generator; i++) {
            model.addCell("Gen_" + i, CellType.HEXAGON);
        }

        for (int i = 0; i < generator; i++) {
            for (int j = 0; j < cities; j++) {
                if(matrix[i][j] == 1) model.addEdge("City_" + j, "Gen_" + i);
            }
        }


        graph.endUpdate();
    }

}

