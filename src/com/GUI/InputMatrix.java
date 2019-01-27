package com.GUI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

public class InputMatrix {
    //Default matrix size
    static int row = 6, col = 7;
    //Text field matrix to hold the cost between cities and generators, cities
    static TextField[][] tf;

    public static GridPane matrixDisplay(){

        GridPane grid = new GridPane();
        grid.setVgap(5);
        grid.setHgap(5);
        grid.setPadding( new Insets(20, 20, 20, 20));
        tf = new TextField[row][col];

        for(int i = 0; i < row+1; i++){
            for(int j = 0;  j < col+1; j++){
                //Generate title rows and columns
                if(i == 0 && j == 0){
                    Label x = new Label("G\\D");
                    x.setId("labelColor1");
                    GridPane.setConstraints(x,j,i);
                    grid.getChildren().add(x);
                }
                else if(i == 0 || j == 0){
                    Label lb = new Label(i==0?"D"+Integer.toString(j):"G"+Integer.toString(i));
                    lb.setFont(new Font("Arial", 18));
                    lb.setAlignment(Pos.CENTER);
                    lb.setId("labelColor1");
                    GridPane.setConstraints(lb,j,i);
                    grid.getChildren().add(lb);
                }
                //Then generate grid text field
                else{
                    tf[i-1][j-1] = new TextField();
                    tf[i-1][j-1].setPrefHeight(50);
                    tf[i-1][j-1].setPrefWidth(50);
                    tf[i-1][j-1].setAlignment(Pos.CENTER);
                    tf[i-1][j-1].setText("0");
                    GridPane.setConstraints(tf[i-1][j-1],j,i);
                    //Handle invalid input (ie. Sysmbol, text..)
                    //Receive only integer
                    TextField curText = tf[i-1][j-1];
                    curText.textProperty().addListener((observer, oldvalue, newvalue)->{
                        if(!curText.getText().matches("[\\d]")){
                            curText.setText(curText.getText().replaceAll("[^\\d]", ""));
                        }
                    });
                    grid.getChildren().add(tf[i-1][j-1]);
                }
            }
        }
        return grid;
    }

    public static GridPane generatorState(){

        GridPane grid = new GridPane();
        grid.setVgap(5);
        grid.setHgap(5);
        grid.setPadding( new Insets(20, 20, 20, 20));

        RadioButton[][] buttonState = new RadioButton[row][1];

        for(int i = 0; i < row+1; i++){
            for(int j = 0;  j < 2; j++){
                //Generate title rows and columns
                if(i == 0 && j == 0){
                    Label x = new Label("Gn");
                    x.setFont(new Font("Arial", 18));
                    x.setId("labelColor1");
                    GridPane.setConstraints(x,j,i);
                    grid.getChildren().add(x);
                }
                else if(i == 0 || j == 0){
                    if(i != 0 && j == 0){
                        Label lb = new Label("G"+Integer.toString(i)+"  ");
                        lb.setFont(new Font("Arial", 18));
                        lb.setAlignment(Pos.CENTER);
                        lb.setId("labelColor1");
                        GridPane.setConstraints(lb,j,i);
                        grid.getChildren().add(lb);
                    }
                    else if(i == 0 && j == 1){
                        Label onOff = new Label("ON");
                        onOff.setPadding( new Insets(5, 30, 5, 30));
                        onOff.setFont(new Font("Arial", 18));
                        onOff.setAlignment(Pos.CENTER);
                        onOff.setId("labelColor1");
                        GridPane.setConstraints(onOff,j,i);
                        grid.getChildren().add(onOff);
                    }
                }
                //Then generate grid text field
                else{
                    /* About the grid of radio button
                     * The coordinate of radio buttons have to minus by 1
                     * Because we have already generated the title rows and columms
                     */

                    // Declare the button
                    buttonState[i-1][j-1] = new RadioButton();
                    //Set alignment for the button
                    buttonState[i-1][j-1].setPrefHeight(50);
                    buttonState[i-1][j-1].setPrefWidth(50);
                    buttonState[i-1][j-1].setAlignment(Pos.CENTER);
                    buttonState[i-1][j-1].setPadding( new Insets(5, 30, 5, 30));
                    //buttonState[i-1][j-1].setSelected(true);

                    //All generators are turned on by DEFAUFT
                    if(j == 1) buttonState[i-1][j-1].setSelected(true);;
                    GridPane.setConstraints(buttonState[i-1][j-1],j,i);

                    grid.getChildren().add(buttonState[i-1][j-1]);
                }
            }
        }
        return grid;
    }

    public static double[][] getMatrixValue(){
        double intMatrix[][] = new double[row][col];

        for(int i = 0; i < row; i++){
            for(int j = 0;  j < col; j++){
                if(tf[i][j].getText().length() == 0) intMatrix[i][j] = 0;
                else intMatrix[i][j] = Double.parseDouble(tf[i][j].getText());
            }
        }
        return intMatrix;
    }

    public static String matrixToText(int matrix[][], int row, int col){
        String text = new String();
        for(int i = 0; i < row; i++){
            for(int j = 0; j < col; j++){
                text +=Integer.toString(matrix[i][j])+" ";
            }
            text+="\n";
        }
        return text;
    }

    public static void clearMatrixValue(){
        for(int i = 0; i < row; i++){
            for(int j = 0;  j < col; j++){
                tf[i][j].setText("");
            }
        }
    }

}
