package com.GUI;

import com.fxgraph.graph.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;


public class GraphGen extends InputMatrix {

    static Graph graph = new Graph();
    static Model model = graph.getModel();
    private static Edge[][] edges;
    public static void genCanvas() {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Graph drawing...");
        window.setMinWidth(800);
        window.setMinHeight(600);

        int[][] matrix = {{1,1,0,1,0},{0,1,0,0,1},{1,0,0,1,0},{0,0,0,1,1}};
        Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        KeyFrame keyFrame = new KeyFrame(Duration.millis(200), action -> {
            int x = (int) (Math.random() * 4);
            int y = (int) (Math.random() * 5);
            matrix[x][y] = matrix[x][y] == 1 ? 0 : 1;
            System.out.println(x + " " + y);
            updateGraph(matrix);
        });
        timeline.getKeyFrames().add(keyFrame);
        timeline.playFromStart();
        BorderPane root = draw(matrix);
        Scene scene = new Scene(root, 1024, 768);
        window.setScene(scene);
        window.show();
    }


    public static BorderPane draw(int[][] matrix) {

        BorderPane root = new BorderPane();
        root.setCenter(graph.getScrollPane());


        Layout layout = new CircleLayout(graph);

        initBaseGraph();
        updateGraph(matrix);
        layout.execute();

        return root;
    }

    private static void initBaseGraph() {

        Model model = graph.getModel();

        graph.beginUpdate();

        int cities = 5;
        int generator = 4;

        for (int i = 0; i < cities; i++) {
            model.addCell("C" + i, CellType.CIRCLE);
        }

        for (int i = 0; i < generator; i++) {
            model.addCell("G" + i, CellType.HEXAGON);
        }

        edges = new Edge[generator][cities];
        for (int i = 0; i < generator; i++) {
            for (int j = 0; j < cities; j++) {
                edges[i][j] = model.addEdge("G" + i, "C" + j, 10);
            }
        }
//
//        for (int i = 0; i < generator; i++) {
//            for (int j = 0; j < cities; j++) {
//                if (matrix[i][j] == 1) {
//                    model.addEdge("G" + i, "D" + j, 10);
//                }
//            }
//        }

//        for(int i = generator; i < cities; i++){
//            for(int j = 0; j < cities; j++) {
//                if(matrix[i][j] == 1){
//                    model.addEdge("D"+i,"D"+j);
//                }
//            }
//        }

        graph.endUpdate();
    }


    private static void updateGraph( int[][] matrix){


        graph.beginUpdate();

        int cities = 5;
        int generator = 4;


        for (int i = 0; i < generator; i++) {
            for (int j = 0; j < cities; j++) {
                if(matrix[i][j] == 0) {
                    edges[i][j].setStatus(false);
                }
                if (matrix[i][j] == 1) {
                    edges[i][j].setStatus(true);
                    //model.addEdge("City_" + j, "Gen_" + i, 10);
                }
            }
        }


        graph.endUpdate();
    }
}

