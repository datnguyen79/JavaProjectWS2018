package com.GUI;

import com.fxgraph.graph.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;


public class GraphGen extends InputMatrix {

    static Graph graph = new Graph();
    static Model model = graph.getModel();
    private static Edge[][] edges;
    public static void canvas() {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Graph drawing...");


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

        BorderPane root = new BorderPane();
        //root.getChildren().addAll(draw(matrix));
        root.setLeft(settingBar());
        root.setCenter(draw(matrix));

        Scene scene = new Scene(root, 1024, 768);
        window.setScene(scene);
        window.show();
    }


    public static BorderPane draw(int[][] matrix) {

        BorderPane root = new BorderPane();
        root.setMinSize(800, 768);
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

    private static VBox settingBar(){
        //slider
        Slider sliderAlpha = new Slider(0, 100, 0);
        sliderAlpha.setBlockIncrement(10);
        sliderAlpha.setShowTickLabels(true);
        sliderAlpha.setShowTickMarks(true);

        Slider sliderBeta = new Slider(0, 100, 0);
        sliderBeta.setBlockIncrement(10);
        sliderBeta.setShowTickLabels(true);
        sliderBeta.setShowTickMarks(true);

        Slider slidera = new Slider(0, 100, 0);
        slidera.setBlockIncrement(10);
        slidera.setShowTickLabels(true);
        slidera.setShowTickMarks(true);

        Slider sliderb = new Slider(0, 100, 0);
        sliderb.setBlockIncrement(10);
        sliderb.setShowTickLabels(true);
        sliderb.setShowTickMarks(true);

        Slider sliderQ = new Slider(0, 100, 0);
        sliderQ.setBlockIncrement(10);
        sliderQ.setShowTickLabels(true);
        sliderQ.setShowTickMarks(true);
        //box
        HBox root = new HBox();

        root.setSpacing(200);
        root.setPadding(new Insets(15, 20, 10, 10));
        //vbox
        VBox v1 = new VBox();
        v1.setSpacing(10);
        // v1.setPadding(new Insets(20));
        //hbox
        HBox h1 = new HBox();
        h1.setSpacing(20);
        HBox h2 = new HBox();
        h2.setSpacing(20);
        HBox h3 = new HBox();
        h3.setSpacing(20);
        HBox h4 = new HBox();
        h4.setSpacing(20);
        HBox h5 = new HBox();
        h5.setSpacing(20);


        // Text
        Text text1 = new Text();
        text1.setText("Alpha");
        text1.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 13));
        text1.setTextAlignment(TextAlignment.CENTER);
        Text text2 = new Text();
        text2.setText("Beta  ");
        text2.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 13));
        text2.setTextAlignment(TextAlignment.CENTER);
        Text text3 = new Text();
        text3.setText("a        ");
        text3.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 13));
        text3.setTextAlignment(TextAlignment.CENTER);
        Text text4 = new Text();
        text4.setText("m       ");
        text4.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 13));
        text4.setTextAlignment(TextAlignment.CENTER);
        Text text5 = new Text();
        text5.setText("Q        ");
        text5.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 13));
        text5.setTextAlignment(TextAlignment.CENTER);
        //TextField vs slider

        TextField alphaInput = new TextField();
        alphaInput.setPrefWidth(50);

        sliderAlpha.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, //
                                Number oldValue, Number newValue) {

                alphaInput.setText(String.valueOf(newValue.intValue()));
            }
        });

        TextField betaInput = new TextField();
        betaInput.setPrefWidth(50);

        sliderBeta.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, //
                                Number oldValue, Number newValue) {

                betaInput.setText(String.valueOf(newValue.intValue()));
            }
        });
        TextField aInput = new TextField();
        aInput.setPrefWidth(50);

        slidera.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, //
                                Number oldValue, Number newValue) {

                aInput.setText(String.valueOf(newValue.intValue()));
            }
        });

        TextField bInput = new TextField();
        bInput.setPrefWidth(50);

        sliderb.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, //
                                Number oldValue, Number newValue) {

                bInput.setText(String.valueOf(newValue.intValue()));
            }
        });

        TextField QInput = new TextField();
        QInput.setPrefWidth(50);

        sliderQ.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, //
                                Number oldValue, Number newValue) {

                QInput.setText(String.valueOf(newValue.intValue()));
            }
        });
        //button
        Button update = new Button();
        update.setText("Apply");
        update.setOnAction(e -> {
            System.out.println(alphaInput.getText());
            System.out.println(betaInput.getText());
        });

        //scene
        h1.getChildren().addAll(text1, sliderAlpha, alphaInput);
        h2.getChildren().addAll(text2, sliderBeta, betaInput);
        h3.getChildren().addAll(text3, slidera, aInput);
        h4.getChildren().addAll(text4, sliderb, bInput);
        h5.getChildren().addAll(text5, sliderQ, QInput);
        v1.getChildren().addAll(h1, h2, h3, h4, h5, update);

        v1.setPadding( new Insets(20,20,20,20));

        //root.getChildren().addAll(v1);

        return v1;
    }
}

