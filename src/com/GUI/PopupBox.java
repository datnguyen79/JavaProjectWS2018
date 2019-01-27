package com.GUI;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PopupBox {
    private static boolean answer;
    private static boolean triggered;

    public static void confirmBox(Stage stage){
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Warning");
        window.setMinWidth(300);

        Label label = new Label();
        label.setText("Are you sure you want to exit");

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

        Text helpfulText = new Text(10, 50, "*** Here users can review or edit the matrix before import ***");
        helpfulText.setFont(new Font(16));

        Button imptButton = new Button("Import");
        imptButton.setPrefSize(80, 30);

        Button instButton = new Button("Instuction");
        instButton.setPrefSize(100,30);

        instButton.setOnAction(e -> {
            instBox();
        });

        imptButton.setOnAction((ActionEvent e) -> {
            triggered = true;
            window.close();
        });

        textArea.setPrefSize(300, 400);
        hbox.getChildren().addAll( instButton, imptButton);
        layout.getChildren().addAll(text, textArea,helpfulText,hbox);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
        return triggered;
    }

    public static void cancelBox(){
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Warning!");
        window.setMinWidth(300);

        Label label = new Label();
        label.setText("No text file has been selected.");

        Button okButton = new Button("Ok");
        okButton.setPrefSize(80, 30);

        okButton.setOnAction(e -> {
            window.close();
        });

        VBox layout1 = new VBox();
        layout1.getChildren().addAll(label);
        layout1.setAlignment(Pos.CENTER);
        layout1.setPadding(new Insets(15, 12, 15, 12));

        HBox layout2 = new HBox(30);
        layout2.getChildren().addAll(okButton);
        layout2.setAlignment(Pos.CENTER);
        layout2.setPadding(new Insets(20, 20, 20, 20));

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(layout2);
        borderPane.setTop(layout1);

        //Scene
        Scene scene = new Scene(borderPane);
        window.setScene(scene);
        window.showAndWait();
    }

    public static void instBox(){
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Instruction");
        window.setMinWidth(300);

        Text text1 = new Text(10, 50,"- The matrix must be NxN matrix.");
        text1.setFont(new Font(16));

        Text text2 = new Text(10, 50,"- Each integer must be seperated by a space.");
        text2.setFont(new Font(16));


        Button okButton = new Button("Ok");
        okButton.setPrefSize(80, 30);

        okButton.setOnAction(e -> {
            window.close();
        });

        VBox layout1 = new VBox();
        layout1.getChildren().addAll(text1, text2);
        layout1.setAlignment(Pos.CENTER_LEFT);
        layout1.setPadding(new Insets(15, 12, 15, 12));

        HBox layout2 = new HBox(30);
        layout2.getChildren().addAll(okButton);
        layout2.setAlignment(Pos.CENTER);
        layout2.setPadding(new Insets(20, 20, 20, 20));

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(layout2);
        borderPane.setTop(layout1);

        //Scene
        Scene scene = new Scene(borderPane);
        window.setScene(scene);
        window.showAndWait();
    }

    public static void aboutBox(){
        Stage window = new Stage();
        Image image = new Image(PopupBox.class.getResourceAsStream("img/cuteAnt.jpg"));
        ImageView imageView = new ImageView(image);
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Instruction");

        Text text1, text2, text3, text4;
        text1 = new Text(10, 50, "MINIMIZE ELECTRICITY TRANSMISSON COST(ACO)");
        text2 = new Text(10, 50, "Frankfurt University of Applied Science\n Java Project - Winter Semester 2018");
        text3 = new Text(10, 50, "Team 6:");
        text4 = new Text(10, 50, "-Chi Minh Truong\n-Ho Tat Dat Nguyen\n-Khac Nhu Phung\n-Ngoc Thach Le");
        text1.setFont(new Font(20));
        text2.setFont(new Font(18));
        text3.setFont(new Font(18));
        text4.setFont(new Font(18));

        Button okButton = new Button("Ok");
        okButton.setPrefSize(80, 30);

        okButton.setOnAction(e -> {
            window.close();
        });

        VBox layout1 = new VBox();
        layout1.getChildren().addAll(text1, text2);
        layout1.setAlignment(Pos.CENTER);
        layout1.setPadding(new Insets(15, 12, 15, 12));

        VBox layout3 = new VBox();
        layout3.getChildren().addAll(text3, text4);
        layout3.setAlignment(Pos.CENTER_LEFT);
        layout3.setPadding(new Insets(15, 12, 15, 12));

        VBox layout2 = new VBox(30);
        layout2.getChildren().addAll(layout3,okButton);
        layout2.setAlignment(Pos.CENTER);
        layout2.setPadding(new Insets(20, 20, 20, 20));

        BorderPane borderPane = new BorderPane();
        borderPane.setBottom(layout2);
        borderPane.setCenter(imageView);
        borderPane.setTop(layout1);

        //Scene
        Scene scene = new Scene(borderPane, 500, 510);
        window.setScene(scene);
        window.showAndWait();
    }
}

