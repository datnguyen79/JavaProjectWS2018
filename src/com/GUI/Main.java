package com.GUI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import com.fxgraph.graph.Graph;

public class Main extends Application {

     @Override
        public void start(Stage primaryStage) {

            primaryStage.setTitle("Minimize The Cost of Electricity Transmission");
            primaryStage.setScene(LayoutGUI.mainLayout(primaryStage));
            primaryStage.show();
        }

        /**
         * @param args the command line arguments
         */
        public static void main(String[] args) {
            launch(args);
        }

}