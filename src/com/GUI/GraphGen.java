package com.GUI;

import com.fxgraph.graph.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.text.DecimalFormat;




public class GraphGen extends InputMatrix {

    static Graph graph = new Graph();
    private static Edge[][] edges;

    public static void genCanvas() {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Graph drawing...");


        int[][] matrix = {{1, 1, 0, 1, 0}, {0, 1, 0, 0, 1}, {1, 0, 0, 1, 0}, {0, 0, 0, 1, 1}};

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

        root.setBottom(hBottem(window));

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


    private static void updateGraph(int[][] matrix) {

        graph.beginUpdate();

        int cities = 5;
        int generator = 4;

        for (int i = 0; i < generator; i++) {
            for (int j = 0; j < cities; j++) {
                if (matrix[i][j] == 0) {
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

    private static VBox settingBar() {
        //Title
        Text title = new Text();
        title.setText("Setting");
        title.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        //slider
        Slider sliderAlpha = new Slider(1, 100, 1);
        sliderAlpha.setBlockIncrement(1);

        Slider sliderBeta = new Slider(1, 100, 1);
        sliderBeta.setBlockIncrement(1);

        Slider sliderE = new Slider(0.1, 0.99, 0);
        sliderE.setBlockIncrement(0.01);

        Slider sliderAnt = new Slider(0.1, 0.9, 0.1);
        sliderAnt.setBlockIncrement(0.1);

        Slider sliderQ = new Slider(1, 1000, 1);
        sliderQ.setBlockIncrement(1);
        //box
        HBox root = new HBox();

        root.setSpacing(200);
        root.setPadding(new Insets(15, 20, 10, 10));
        //vbox
        VBox leftLayout = new VBox();
        leftLayout.setSpacing(50);
        leftLayout.setPadding(new Insets(20, 20, 20, 20));
        // leftLayout.setPadding(new Insets(20));
        //hbox
        HBox h1 = new HBox();
        h1.setSpacing(20);
        h1.setAlignment(Pos.CENTER_RIGHT);
        HBox h2 = new HBox();
        h2.setAlignment(Pos.CENTER_RIGHT);
        h2.setSpacing(20);
        HBox h3 = new HBox();
        h3.setSpacing(20);
        h3.setAlignment(Pos.CENTER_RIGHT);
        HBox h4 = new HBox();
        h4.setSpacing(20);
        h4.setAlignment(Pos.CENTER_RIGHT);
        HBox h5 = new HBox();
        h5.setSpacing(20);
        h5.setAlignment(Pos.CENTER_RIGHT);
        HBox hButton = new HBox();
        hButton.setSpacing(40);


        // Text
        Text text1 = new Text();
        text1.setText("Alpha");
        text1.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 13));
        text1.setTextAlignment(TextAlignment.CENTER);
        Text text2 = new Text();
        text2.setText("Beta");
        text2.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 13));
        text2.setTextAlignment(TextAlignment.CENTER);
        Text text3 = new Text();
        text3.setText("Evaporation");
        text3.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 13));
        text3.setTextAlignment(TextAlignment.CENTER);
        Text text4 = new Text();
        text4.setText("% of Ant");
        text4.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 13));
        text4.setTextAlignment(TextAlignment.CENTER);
        Text text5 = new Text();
        text5.setText("Q");
        text5.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 13));
        text5.setTextAlignment(TextAlignment.CENTER);
        //FORMAT
        DecimalFormat df = new DecimalFormat("0.##");
        DecimalFormat df1 = new DecimalFormat("0.#");

        //TextField vs slider

        Label alphaInput = new Label("" + sliderAlpha.getValue());
        alphaInput.setPrefWidth(50);

        sliderAlpha.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, //
                                Number oldValue, Number newValue) {

                sliderAlpha.setValue(newValue.intValue());
                alphaInput.setText(String.valueOf(newValue.intValue()));
            }
        });

        Label betaInput = new Label("" + sliderBeta.getValue());
        betaInput.setPrefWidth(50);

        sliderBeta.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, //
                                Number oldValue, Number newValue) {
                sliderBeta.setValue(newValue.intValue());
                betaInput.setText(String.valueOf(newValue.intValue()));
            }
        });

        Label EInput = new Label("" + sliderE.getValue());
        EInput.setPrefWidth(50);

        sliderE.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, //
                                Number oldValue, Number newValue) {

                sliderE.setValue(Double.parseDouble(df.format(newValue)));
                EInput.setText(df.format(newValue));
            }
        });

        Label AntInput = new Label("" + sliderAnt.getValue());
        AntInput.setPrefWidth(50);

        sliderAnt.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, //
                                Number oldValue, Number newValue) {

                sliderAnt.setValue(Double.parseDouble(df1.format(newValue)));
                AntInput.setText(df1.format(newValue));
            }
        });

        Label QInput = new Label("" + sliderQ.getValue());
        QInput.setPrefWidth(50);

        sliderQ.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, //
                                Number oldValue, Number newValue) {

                sliderQ.setValue(newValue.intValue());
                QInput.setText(String.valueOf(newValue.intValue()));
            }
        });
        //button
        Button update = new Button();
        update.setPrefSize(80, 30);
        update.setText("Run");
        update.setOnAction(e -> {
            if(update.getText()=="Run"){
//            System.out.println(sliderE.getValue());
//            System.out.println(alphaInput.getText());
//            System.out.println(betaInput.getText());
//            System.out.println(EInput.getText());
//            System.out.println(AntInput.getText());
//            System.out.println((QInput.getText()));
                System.out.println("Cai lon");
                update.setText("Stop");
            //
            }
            else {
                System.out.println("Con cac");
                update.setText("Run");
            }
        });

        Button clear = new Button();
        clear.setPrefSize(80, 30);
        clear.setText("Clear");
        clear.setOnAction(e -> {
            sliderAlpha.setValue(0);
            sliderBeta.setValue(0);
            sliderE.setValue(0);
            sliderAnt.setValue(0);
            sliderQ.setValue(0);
        });
        //scene
        h1.getChildren().addAll(text1, sliderAlpha, alphaInput);
        h2.getChildren().addAll(text2, sliderBeta, betaInput);
        h3.getChildren().addAll(text3, sliderE, EInput);
        h4.getChildren().addAll(text4, sliderAnt, AntInput);
        h5.getChildren().addAll(text5, sliderQ, QInput);
        hButton.getChildren().addAll(update, clear);
        hButton.setAlignment(Pos.CENTER);
        leftLayout.getChildren().addAll(title, h1, h2, h3, h4, h5, hButton);


        //root.getChildren().addAll(leftLayout);

        return leftLayout;
    }

    private static HBox hBottem(Stage window ) {
        HBox bottemLayout = new HBox();
        bottemLayout.setSpacing(250);
        bottemLayout.setPadding(new Insets(30, 40, 100, 450));
        //Button
//        Button start = new Button();
//        start.setPrefSize(80, 30);
//        start.setText("Start");
//
//        Button pause = new Button();
//        pause.setPrefSize(80, 30);
//        pause.setText("Pause");

        Button close = new Button();
        close.setPrefSize(80, 30);
        close.setText("Close");

        close.setOnAction(e->{
            PopupBox.confirmBox(window);
        });
        bottemLayout.getChildren().addAll(close);
        return bottemLayout;
    }


}

