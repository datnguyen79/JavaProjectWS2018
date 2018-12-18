/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaprojectws2018;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author ADMIN
 */
public class PopupBox {
    static boolean answer;
    static boolean triggered;
    
    public static void confirmBox(String title, String message, Stage stage){
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(300);
        
        Label label = new Label();
        label.setText(message);
        
        //Create two button yes and no
        Button yesButton = new Button("Yes");
        Button noButton = new Button("No");
        yesButton.setPrefSize(80, 30);
        noButton.setPrefSize(80, 30);
        
        yesButton.setOnAction(e -> {
            answer = true;
            window.close();
        });
        noButton.setOnAction(e -> {
            answer = false;
            window.close();
        });
        //Pane components
        VBox layout = new VBox();
        layout.getChildren().addAll(label);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(15, 12, 15, 12));
        
        HBox layout2 = new HBox(30);
        layout2.getChildren().addAll(yesButton, noButton);
        layout2.setAlignment(Pos.CENTER);
        layout2.setPadding(new Insets(20, 20, 20, 20));
        
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(layout2);
        borderPane.setTop(layout);
        
        //Scene
        Scene scene = new Scene(borderPane);
        window.setScene(scene);
        window.showAndWait();
        //Close window if user click "yes"
        if(answer) stage.close();
    }
    
    public static boolean previewBox(TextArea textArea){
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("View Contain");
        window.setMinWidth(400);
        
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20, 20, 20, 20));
         
        HBox hbox = new HBox(10);
        hbox.setPadding(new Insets(20, 50, 20, 20));
        hbox.setAlignment(Pos.CENTER_RIGHT);
        
        Text text = new Text(10, 50, "File contains: ");
        text.setFont(new Font(20));
        
        Button imptButton = new Button("Import");
        imptButton.setPrefSize(80, 30);
        
        imptButton.setOnAction((ActionEvent e) -> {
            triggered = true;
        });
        
        textArea.setPrefSize(300, 400);
        hbox.getChildren().add(imptButton);
        layout.getChildren().addAll(text, textArea,hbox);
        
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
        return triggered;
    }
}
