package com.GUI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import com.fxgraph.graph.Graph;

public class Main extends Application {

        @Override
        public void start(Stage primaryStage) {

            Scene scene = new Scene(LayoutGUI.mainLayout(primaryStage), 840, 720);
            scene.getStylesheets().add(getClass().getResource("styling.css").toExternalForm());
            primaryStage.setTitle("Minimize The Cost of Electricity Transmisson");
            primaryStage.setScene(scene);
            primaryStage.show();
        }

        /**
         * @param args the command line arguments
         */
        public static void main(String[] args) {
            launch(args);
        }

}