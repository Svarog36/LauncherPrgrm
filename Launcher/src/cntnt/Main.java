package cntnt;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static String folderWithShortcuts, propertiesLocation = "./Properties";


    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("surface.fxml"));

        primaryStage.setTitle("Shortcut Browser");
        primaryStage.setScene(new Scene(root));
        primaryStage.setWidth(600);
        primaryStage.setHeight(400);

        primaryStage.show();


        folderWithShortcuts = FileIO.loadProperties();
    }


    public static void main(String[] args) {
        launch(args);
    }



}