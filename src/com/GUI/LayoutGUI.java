package com.GUI;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;



public class LayoutGUI  extends InputMatrix{
    //Global variable
    // Main layout
    private static BorderPane root = new BorderPane();
    // Matrix to hold old value and restore it later
    private static double[][] preMatrix = new double[row][col];
    private static int preRow, preCol;
    // Text field to hold the number of Generator and Destination
    private static TextField numDes = new TextField();
    private static TextField numGen = new TextField();
    private static boolean turnOnCSS;

    private static void saveTextToFile(String content, File file) {
        try {
            PrintWriter writer;
            writer = new PrintWriter(file);
            writer.println(content);
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(LayoutGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static VBox topLayout(){
        Stage window = new Stage();
        /*Layout contains: Menu Bar, VBox(Text1 + Text2)
         *Section: Top
         *Use in: Border Pane
         */
        //File Loader
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"));

        ////////////////////////// MenuBar Section ////////////////////////////
        MenuBar menuBar = new MenuBar();

        Menu file = new Menu("File");
        Menu help = new Menu("Help");
        Menu view = new Menu("View");

        MenuItem menuOpen = new MenuItem("Open...");
        MenuItem menuSave = new MenuItem("Save");
        MenuItem menuAbout = new MenuItem("About");
        RadioMenuItem menuDefault = new RadioMenuItem("Default");
        RadioMenuItem menuDark = new RadioMenuItem("Dark Theme");
        menuDefault.setSelected(true);

        SeparatorMenuItem separator = new SeparatorMenuItem();
        SeparatorMenuItem separator2 = new SeparatorMenuItem();
        ToggleGroup group = new ToggleGroup();
        menuDefault.setToggleGroup(group);
        menuDark.setToggleGroup(group);

        file.getItems().add(menuOpen);
        file.getItems().add(separator);
        file.getItems().add(menuSave);
        help.getItems().add(menuAbout);
        view.getItems().addAll(menuDefault,separator2,menuDark);

        ////Set action for each component in the menu bar

        ///Open action
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
                Logger.getLogger(LayoutGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
            if(PopupBox.previewBox(textArea)){
                String text = textArea.getText();
                String[] str1 = text.split("\n");
                String[] str0 = str1[0].split(" ");
                int[][] matrix = new int[row][col];
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
        ///about button action
        menuAbout.setOnAction(e ->{
            PopupBox.aboutBox();
        });
        ///save button action
        menuSave.setOnAction(e -> {
            //Set extension filter for text files
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Text files (*.txt)", "*.txt");
            fileChooser.getExtensionFilters().add(extFilter);

            //Show save file dialog
            File file1 = fileChooser.showSaveDialog(window);

            if (file1 != null) {
                saveTextToFile(InputMatrix.matrixToText(InputMatrix.getMatrixValue(), row, col), file1);
            }
        });
        menuDefault.setOnAction(e -> {
            turnOnCSS = false;
        });
        menuDark.setOnAction(e -> {
            turnOnCSS = true;
        });


        ///Gathering components of menu bar
        menuBar.getMenus().addAll(file,view,help);

        //////////////// End of MenuBar section /////////////////////////

        ////Text Section
        Text text1, text2;
        text1 = new Text(10, 50, "MINIMIZE ELECTRICITY TRANSMISSION COST");
        text1.setId("textColor1");
        text2 = new Text(10, 50, "USING ANT COLONY OPTIMIZATION(ACO)");
        text2.setId("textColor1");
        text1.setFont(new Font(28));
        text2.setFont(new Font(24));

        VBox top = new VBox(10);

        //Adding components for the Top section
        top.getChildren().addAll(menuBar, text1, text2);
        top.setAlignment(Pos.TOP_CENTER);
        top.setPadding( new Insets(0, 0, 10, 0));
        return top;
    }

    private static VBox botLayout(Stage stage) {

        /*Main Layout contains:
         *Section: Bottom
         *Use in: Border Pane
         */
        VBox bottom = new VBox(10);
        //VBox"Bottom" = VBox"bottom1" + HBox"bottom2"

        ///Bottom 1 Section
        VBox tab1 = new VBox(10);
        tab1.setPadding( new Insets(10, 10, 10, 10));

        // Structure of the Bottom 1:
        // VBox"bottom1" = HBox"LabelnButton1"(Label"des" + textField"numDes") + HBox"LabelnButton2"(Label"gen" + textField"numGen")
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
            root.setRight(rightLayout());
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
        bottom2.setPadding( new Insets(0, 10, 10, 10));
        Button createButton, clearButton, closeButton;
        createButton = new Button("Create");
        clearButton = new Button("Clear");
        closeButton = new Button("Close");
        createButton.setPrefSize(80, 30);
        clearButton.setPrefSize(80, 30);
        closeButton.setPrefSize(80, 30);

        //Set Button event
        closeButton.setOnAction(e ->{
            PopupBox.confirmBox(stage);
        });

        clearButton.setOnAction(e ->{
            InputMatrix.clearMatrixValue();
        });
        createButton.setOnAction(e ->{
            GraphGen.displayGraphWindow();
        });

        //TabPane wrap all bottom1 components
        TabPane botTabPane = new TabPane();
        botTabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        Tab resizeTab = new Tab("Edit Size");
        Tab settingGen = new Tab("Set Generator");
        resizeTab.setContent(tab1);

        //Adding all components of the bottom layout
        bottom2.getChildren().addAll(createButton, clearButton, closeButton);
        botTabPane.getTabs().addAll(resizeTab,settingGen);
        bottom.getChildren().addAll(botTabPane, bottom2);
        return bottom;
    }

    private static VBox leftLayout(){
        /*Main Layout contains: text3, scrollPane(matrixGrid(gridPane))
         *Section: Left
         *Use in: Border Pane
         */
        VBox left = new VBox(10);
        ScrollPane wrapMatrix = new ScrollPane();
        Text text3 = new Text(10, 50, "(*) Input Electricity Cost: ");
        text3.setFont(new Font(20));
        text3.setId("textColor2");

        /*Wrap the gridPane by the ScrollPane
         * So when the size of the GridPane increase
         * The window size will not become extremely big
         */
        wrapMatrix.setContent(InputMatrix.matrixDisplay());
        wrapMatrix.setPrefSize(500,700);


        left.getChildren().addAll(text3, wrapMatrix);
        left.setPadding( new Insets(10, 10, 10, 10));
        return left;
    }

    private static VBox rightLayout(){
        /*Main Layout contains: text3, matrixGrid(gridPane)
         *Section: Right
         *Use in: Border Pane root
         */
        VBox right = new VBox(10);
        Text text3 = new Text(10, 50, "Generator State: ");
        text3.setFont(new Font(20));
        text3.setId("textColor2");

        /*Wrap the gridPane by the ScrollPane
        * So when the size of the GridPane increase
        * The window size will not become extremely big
        */
        ScrollPane wrapGenState = new ScrollPane();
        wrapGenState.setContent(InputMatrix.generatorState());
        wrapGenState.setPrefSize(200,700);

        right.getChildren().addAll(text3, wrapGenState);
        right.setPadding( new Insets(10, 10, 10, 10));
        return right;
    }

    public static Scene mainLayout(Stage stage){
        root.setTop(LayoutGUI.topLayout());
        root.setLeft(LayoutGUI.leftLayout());
        root.setRight(LayoutGUI.rightLayout());
        root.setBottom(LayoutGUI.botLayout(stage));

        Scene scene = new Scene(root, 840, 720);
        scene.getStylesheets().add("com/GUI/Styling.css");

        return scene;
    }
}

