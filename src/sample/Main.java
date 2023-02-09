package sample;

import Gui.*;
import Logic.Controller;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.Scene;


public class Main extends Application {
    @Override
    public void start(Stage stage) {

        Scene scene = new Scene(new GuiManager(Controller.getInstance(), stage));
        stage.setScene(scene);
        stage.setWidth(800);
        stage.setHeight(650);
        stage.getIcons().addAll(new Image(Utility.IMAGE_PATH + "icon.png"));
        stage.setMaximized(true);
        stage.setTitle("Turing Machine Simulator");

        stage.show();
    }


    public static void main(String[] args) {
        Application.launch(args);
    }
}
