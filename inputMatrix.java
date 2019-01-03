/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaprojectws2018;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

/**
 *
 * @author ADMIN
 */

public class inputMatrix {
    //Default matrix size
    static int row = 6, col = 7;
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
    
    public static int[][] getMatrixValue(){
        int intMatrix[][] = new int[row][col];
        
        for(int i = 0; i < row; i++){
            for(int j = 0;  j < col; j++){
                if(tf[i][j].getText().length() == 0) intMatrix[i][j] = 0;
                else intMatrix[i][j] = Integer.parseInt(tf[i][j].getText());
            }
        }
        return intMatrix;
    }
    
    public static void clearMatrixValue(){
        for(int i = 0; i < row; i++){
            for(int j = 0;  j < col; j++){
                tf[i][j].setText("");
            }
        }
    }
    
}
