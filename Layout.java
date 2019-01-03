/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaprojectws2018;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author STEVEN
 */
public class Layout  extends inputMatrix{
    //Global variable
     static BorderPane root = new BorderPane();
     static int preMatrix[][] = new int[row][col];
     static int preRow, preCol;
     static TextField numDes = new TextField();
     static TextField numGen = new TextField();
    
    public static VBox topLayout(){
         Stage window = new Stage();
        /*Layout contains: Menu Bar, VBox(Text1 + Text2)
         *Section: Top
         *Use in: Border Pane
         */
        //
        //File Loader
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        
        //Menu Section
        MenuBar menuBar = new MenuBar();

        Menu file = new Menu("File");
        Menu help = new Menu("Help");
        
        MenuItem menuOpen = new MenuItem("Open...");
        MenuItem menuSave = new MenuItem("Save");
        MenuItem menuAbout = new MenuItem("About");
        SeparatorMenuItem separator = new SeparatorMenuItem();

        file.getItems().add(menuOpen);
        file.getItems().add(separator);
        file.getItems().add(menuSave);
        help.getItems().add(menuAbout);
        
        
        //Receive matrix from text file
        TextArea textArea = new TextArea(); //use TextArea for holding input text
        menuOpen.setOnAction((final ActionEvent e) -> {
            try {
                File file1 = fileChooser.showOpenDialog(window);
                if(file1 == null){
                    PopupBox.cancelBox();
                }
                else{
                Scanner sc = new Scanner(file1);
                while (sc.hasNextLine()) 
                    textArea.appendText(sc.nextLine()+"\n");
                }
            } 
            catch (FileNotFoundException ex) {
                Logger.getLogger(Layout.class.getName()).log(Level.SEVERE, null, ex);
            }
            if(PopupBox.previewBox(textArea)){
                String text = textArea.getText();
                String[] str1 = text.split("\n");
                String[] str0 = str1[0].split(" ");
                int matrix[][] = new int[row][col];
                row = str1.length;
                col = str0.length;
                root.setLeft(leftLayout());
                for(int i=0; i < row; i++){
                    String[] str2 = str1[i].split(" ");
                    for(int j = 0; j < col; j++){
                    tf[i][j].setText(str2[j]);
                    }
                }
              textArea.setText("");
              numGen.setText(row+"");
              numDes.setText(col+"");
                
            }
         });
        menuAbout.setOnAction(e ->{
            PopupBox.aboutBox();
        });

        menuBar.getMenus().addAll(file,help);
        //Text Section
        
        Text text1, text2;
        text1 = new Text(10, 50, "MINIMIZE ELECTRICITY TRANSMISSON COST");
        //text1.setFill(Color.TEAL);
        text1.setId("textColor1");
        text2 = new Text(10, 50, "USING ANT COLONY OPTIMIZATION(ACO)");
        text2.setId("textColor1");
        text1.setFont(new Font(28));
        text2.setFont(new Font(24));
      
        VBox top = new VBox(10);
        //Adding components
        top.getChildren().addAll(menuBar, text1, text2);
        top.setAlignment(Pos.TOP_CENTER);
        top.setPadding( new Insets(0, 0, 10, 0));
        return top;
    }
    
    public static VBox botLayout(Stage stage) {

        /*Main Layout contains: 
         *Section: Bottom
         *Use in: Border Pane
         */
        VBox bottom = new VBox(10);
        //VBox"Bottom" = VBox"bottom1" + HBox"bottom2"
  
        ///Bottom 1 Section
        VBox tab1 = new VBox(10);
        tab1.setPadding( new Insets(10, 10, 10, 10));
        //VBox"bottom1" = HBox"LabelnButton1"(Label"des" + textField"numDes") + HBox"LabelnButton2"(Label"gen" + textField"numGen")
        HBox LabelnText1, LabelnText2;
        LabelnText1 = new HBox(20);
        LabelnText2 = new HBox(20);
        
        
        //Label
        Label des = new Label("Number of Destination: ");
        des.setId("labelColor1");
        Label gen = new Label("Number of Generator:   ");
        gen.setId("labelColor1");
        //TextField
        numDes.setPrefHeight(30);
        numDes.setPrefWidth(80);
        numDes.setText(Integer.toString(col));
        numGen.setPrefHeight(30);
        numGen.setPrefWidth(80);
        numGen.setText(Integer.toString(row));
        //Button
        Button addButtonG = new Button("+");
        Button subButtonG = new Button("–");
        Button addButtonD = new Button("+");
        Button subButtonD = new Button("–");
        
        Button resizeButton = new Button("Resize");
        resizeButton.setPrefHeight(30);
        resizeButton.setPrefWidth(80);
        Button fillButton = new Button("Fill");
        fillButton.setPrefHeight(30);
        fillButton.setPrefWidth(80);
      

            //set Button event
        resizeButton.setOnAction(e -> {
            preCol = col;
            preRow = row;
            preMatrix = getMatrixValue();
            row = Integer.parseInt(numGen.getText());
            col = Integer.parseInt(numDes.getText());
            root.setLeft(leftLayout());
        });
        fillButton.setOnAction(e -> {
            for(int i = 0;  i < (row>=preRow?preRow:row); i++){
                for(int j = 0; j < (col>=preCol?preCol:col); j++){
                    tf[i][j].setText(preMatrix[i][j]+"");
                }
            }
        });
            
        addButtonG.setOnAction(e ->{
           int newGen =  Integer.parseInt(numGen.getText()) + 1;
           numGen.setText(""+newGen);
        });
        subButtonG.setOnAction(e ->{
           if(Integer.parseInt(numGen.getText()) > 2){
            int newGen =  Integer.parseInt(numGen.getText()) - 1;
            numGen.setText(""+newGen);
           }
        });
        addButtonD.setOnAction(e ->{
           int newDes =  Integer.parseInt(numDes.getText()) + 1;
           numDes.setText(""+newDes);
        });
        subButtonD.setOnAction(e ->{
           if(Integer.parseInt(numDes.getText()) > 2){
            int newDes =  Integer.parseInt(numDes.getText()) - 1;
            numDes.setText(""+newDes);
           }
        });
        
            //Adding all components
        LabelnText1.getChildren().addAll(des,numDes,addButtonD, subButtonD, resizeButton);
        LabelnText2.getChildren().addAll(gen,numGen,addButtonG, subButtonG, fillButton);
        tab1.getChildren().addAll(LabelnText1,LabelnText2);
        tab1.setId("tabBG");
        
        
        ///Bottom 2 Section
        HBox bottom2 = new HBox(80);
        //HBox"bottom2" = Button"Create" + Button"Clear" + Button"Close"
        bottom2.setAlignment(Pos.CENTER);
        bottom2.setPadding( new Insets(10, 10, 10, 10));
        Button createButton, clearButton, closeButton;
        createButton = new Button("Create");
        clearButton = new Button("Clear");
        closeButton = new Button("Close");
        createButton.setPrefSize(80, 30);
        clearButton.setPrefSize(80, 30);
        closeButton.setPrefSize(80, 30);
            //Set Button event
        closeButton.setOnAction(e ->{
            PopupBox.confirmBox("Warning", "Are you sure you want to exit?",stage);
        });
         clearButton.setOnAction(e ->{
            inputMatrix.clearMatrixValue();
        });
        
        //TabPane wrap all bottom1 components
        TabPane botTabPane = new TabPane();
        botTabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        Tab resizeTab = new Tab("Edit Size");
        Tab settingGen = new Tab("Set Generator");
        //botTabPane.getSelectionModel().selectedItemProperty().addListener((observer, oldValue, newValue) -> botTabPane.setId("selectedTab"));
        resizeTab.setContent(tab1);
        

         
            //Adding all components
        bottom2.getChildren().addAll(createButton, clearButton, closeButton);
        botTabPane.getTabs().addAll(resizeTab,settingGen);
        bottom.getChildren().addAll(botTabPane, bottom2);
        
    return bottom;   
    }
    
    public static VBox leftLayout(){
        /*Main Layout contains: text3, matrixGrid(gridPane)
         *Section: Left
         *Use in: Border Pane
         */
        VBox left = new VBox(10);
        Text text3 = new Text(10, 50, "(*) Input Electricity Cost: ");
        text3.setFont(new Font(20));
        text3.setId("textColor2");
        left.getChildren().addAll(text3, inputMatrix.matrixDisplay());
        left.setPadding( new Insets(10, 10, 10, 10));
        return left;
    }
    
    public static BorderPane mainLayout(Stage stage){
        root.setTop(Layout.topLayout());
        root.setLeft(Layout.leftLayout());
        root.setBottom(Layout.botLayout(stage));
        
        return root;
    }
}
