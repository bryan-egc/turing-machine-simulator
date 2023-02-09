package Gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Created by Eno on 29/06/2017.
 */
public class AboutGui {

    public AboutGui(){
        BorderPane borderPane = new BorderPane();
        Scene scene = new Scene(borderPane);
        Label version = Utility.createLabel("Version");
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setHgap(3);
        gridPane.setVgap(4);
        gridPane.add(version, 0, 0);
        gridPane.add(Utility.createLabel("1.0"), 1, 0);
        gridPane.add(Utility.createLabel("Author"), 0, 1);
        gridPane.add( Utility.createLabel("Eno Cuka"), 1, 1);
        gridPane.add(Utility.createLabel("Email"), 0, 2);
        gridPane.add(Utility.createLabel("enocuka94@gmail.com"), 1, 2);
        version.setPadding(new Insets(0, 30, 0, 0));
        borderPane.setCenter(gridPane);
        Stage stage = new Stage();
        stage.setTitle("About");
        stage.setScene(scene);
        stage.setWidth(400);
        stage.setHeight(200);
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.getIcons().addAll(new Image(Utility.IMAGE_PATH + "icon.png"));
        stage.showAndWait();

    }
}
