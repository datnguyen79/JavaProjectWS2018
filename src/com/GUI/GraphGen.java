package com.GUI;

import com.ACO.*;

import com.fxgraph.graph.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.text.DecimalFormat;
import java.util.concurrent.atomic.AtomicInteger;


public class GraphGen extends InputMatrix {

    static Graph graph;
    private static Edge[][] edges;
    static boolean closeWindow;

    public static void displayGraphWindow() {
        //Initialize display graph window
        Stage window = new Stage();
        graph = new Graph();
        closeWindow = false;

        window.setOnCloseRequest(e ->{
            PopupBox.confirmBox(window);
        });

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Graph Window");


        BorderPane root = new BorderPane();
        root.setLeft(settingBar(window));
        root.setCenter(initCanvas());

        Scene scene = new Scene(root, 1200, 800);
        scene.getStylesheets().add("com/GUI/styling.css");
        window.setScene(scene);
        window.show();
    }


    public static Pane initCanvas() {

        Pane root = new Pane();
        root.setMinSize(900, 800);
        root.getChildren().add(graph.getScrollPane());

        Layout layout = new CircleLayout(graph);

        initBaseGraph();
        layout.execute();

        return root;
    }

    private static void initBaseGraph() {

        Model model = graph.getModel();

        graph.beginUpdate();

        int cities = col;
        int generators = row;

        double[][] matrix = getMatrixValue();

        for (int i = 0; i < cities; i++) {
            model.addCell("C" + i, CellType.CIRCLE);
        }

        for (int i = 0; i < generators; i++) {
            model.addCell("G" + i, CellType.HEXAGON);
        }

        edges = new Edge[generators+cities][cities];
        for (int i = 0; i < generators; i++) {
            for (int j = 0; j < cities; j++) {
                edges[i][j] = model.addEdge("G" + i, "C" + j, matrix[i][j]);
            }
        }

        for (int i = generators; i < generators+cities; i++) {
            for (int j = 0; j < cities; j++) {
                edges[i][j] = model.addEdge("C" + (i-generators), "C" + j, matrix[i][j]);
            }
        }

        graph.endUpdate();
       // model.clearAddedLists();
    }


    private static void updateGraph(int[][] matrix) {

        graph.beginUpdate();

        int cities = col;
        int generators = row;

        for (int i = 0; i < generators + cities; i++) {
            for (int j = 0; j < cities; j++) {
                if (matrix[i][j] == 0) {
                    edges[i][j].setStatus(false);
                }
                if (matrix[i][j] == 1) {
                    edges[i][j].setStatus(true);
                }
            }
        }

        graph.endUpdate();
    }

    public static void runAlgorithm(Settings currentSetting){

        boolean[] state = new boolean[row];
        for (int i=0; i < row; i++) {
            state[i] = true;
        }

        AntColonyOptimization mTSP = new AntColonyOptimization(col, row, state, currentSetting);
        mTSP.printMatrix();

        AtomicInteger iter = new AtomicInteger(0);

        Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        KeyFrame keyFrame = new KeyFrame(Duration.millis(200), action -> {
            updateGraph(mTSP.solve());
            iter.getAndIncrement();
            if(closeWindow || iter.intValue() >= 100) timeline.stop();
        });
        timeline.getKeyFrames().add(keyFrame);
        timeline.playFromStart();
    }

    private static VBox settingBar(Stage window) {
        //Title
        Text title = new Text();
        title.setText("Setting");
        title.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        title.setId("textColor1");

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
        HBox sliderAlphaSet = new HBox();
        sliderAlphaSet.setSpacing(30);
        sliderAlphaSet.setAlignment(Pos.CENTER_RIGHT);
        HBox sliderBetaSet = new HBox();
        sliderBetaSet.setAlignment(Pos.CENTER_RIGHT);
        sliderBetaSet.setSpacing(30);
        HBox sliderESet = new HBox();
        sliderESet.setSpacing(30);
        sliderESet.setAlignment(Pos.CENTER_RIGHT);
        HBox sliderAntSet = new HBox();
        sliderAntSet.setSpacing(30);
        sliderAntSet.setAlignment(Pos.CENTER_RIGHT);
        HBox sliderQSet = new HBox();
        sliderQSet.setSpacing(30);
        sliderQSet.setAlignment(Pos.CENTER_RIGHT);
        HBox ButtonSet = new HBox();

        ButtonSet.setSpacing(40);
        HBox textIterSet = new HBox();
        textIterSet.setSpacing(20);
        textIterSet.setAlignment(Pos.CENTER_LEFT);


        // Text
        Text text1 = new Text();
        text1.setText("Alpha");
        text1.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 13));
        text1.setTextAlignment(TextAlignment.CENTER);
        text1.setId("textColor2");

        Text text2 = new Text();
        text2.setText("Beta");
        text2.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 13));
        text2.setTextAlignment(TextAlignment.CENTER);
        text2.setId("textColor2");

        Text text3 = new Text();
        text3.setText("Evaporation");
        text3.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 13));
        text3.setTextAlignment(TextAlignment.CENTER);
        text3.setId("textColor2");

        Text text4 = new Text();
        text4.setText("% of Ant");
        text4.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 13));
        text4.setTextAlignment(TextAlignment.CENTER);
        text4.setId("textColor2");

        Text text5 = new Text();
        text5.setText("Q");
        text5.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 13));
        text5.setTextAlignment(TextAlignment.CENTER);
        text5.setId("textColor2");

        Text text6 = new Text();
        text6.setText("Number of Iteration");
        text6.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 13));
        text6.setTextAlignment(TextAlignment.CENTER);
        text6.setId("textColor2");
        //Text field iteration
        TextField numofIteration = new TextField();
        numofIteration.setPrefWidth(50);
        numofIteration.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    numofIteration.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

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

        Button pauseContinue = new Button();
        pauseContinue.setPrefSize(80, 30);
        pauseContinue.setText("Pause");
        pauseContinue.setOnAction(e -> {
            if (pauseContinue.getText() == "Pause") {
                closeWindow = true;
                pauseContinue.setText("Continue");
            }
        });

        Button run = new Button();
        run.setPrefSize(80, 30);
        run.setText("Run");
        run.setOnAction(e -> {
            if(run.getText()=="Run" && numofIteration.getText().trim().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Failure");
                alert.setContentText("You have to fill in the Iteration box");
                alert.showAndWait();
            }
            else if(run.getText()=="Run" && !numofIteration.getText().trim().isEmpty()){
                closeWindow = false;
                Settings currentSetting = new Settings(sliderAlpha.getValue(),sliderBeta.getValue(),
                        sliderE.getValue(), sliderQ.getValue(), sliderAnt.getValue());
                runAlgorithm(currentSetting);
                run.setDisable(true);
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
            numofIteration.setText("0");
        });

        Button close = new Button();
        close.setPrefSize(80, 30);
        close.setText("Close");

        close.setOnAction(e->{
            closeWindow = PopupBox.confirmBox(window);
        });
        //scene
        sliderAlphaSet.getChildren().addAll(text1, sliderAlpha, alphaInput);
        sliderBetaSet.getChildren().addAll(text2, sliderBeta, betaInput);
        sliderESet.getChildren().addAll(text3, sliderE, EInput);
        sliderAntSet.getChildren().addAll(text4, sliderAnt, AntInput);
        sliderQSet.getChildren().addAll(text5, sliderQ, QInput);

        ButtonSet.getChildren().addAll(run, pauseContinue, clear, close);
        textIterSet.getChildren().addAll(text6,numofIteration);

        ButtonSet.setAlignment(Pos.CENTER);

        leftLayout.getChildren().addAll(title, sliderAlphaSet, sliderBetaSet, sliderESet, sliderAntSet, sliderQSet,textIterSet, ButtonSet);

        return leftLayout;
    }




}

