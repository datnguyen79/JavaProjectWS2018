/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaprojectws2018;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import com.fxgraph.graph.CellType;
import com.fxgraph.graph.Graph;
import com.fxgraph.graph.Model;

/**
 *
 * @author Ho Tat Dat Nguyen
 */
public class JavaProjectWS2018 extends Application {


    Graph graph = new Graph();
    @Override
    public void start(Stage primaryStage) {

        graph = new Graph();
        
        Scene scene = new Scene(Layout.mainLayout(primaryStage), 840, 720);
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