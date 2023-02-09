package Gui;

import Logic.Inputs;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
//import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import np.com.ngopal.control.AutoFillTextBox;
import org.controlsfx.control.textfield.TextFields;
import sample.Main;

import java.io.*;
import java.util.ArrayList;
import java.util.Optional;

/**
 * Created by Eno on 06/06/2017.
 */
public class Utility {

    private static final String STANDARD_BUTTON_STYLE = "-fx-background-color: transparent;-fx-border-color: transparent;-fx-text-fill:black;-fx-font-size: 16;";
    private static final String HOVERED_BUTTON_STYLE = "-fx-background-color: transparent;-fx-border-color: transparent;-fx-text-fill:deepskyblue;-fx-font-size: 16;";
    public static final String IMAGE_PATH = "file:images/";

    public static TextField createTextField(String text, double minWidth) {
        TextField textField = TextFields.createClearableTextField();
        textField.setText(text);
        textField.setPrefWidth(minWidth);
        return textField;
    }

    public static AutoFillTextBox<StateModel> createTextFieldWithFiniteStatesSuggestion(ObservableList list) {
        AutoFillTextBox<StateModel> autoFillTextBox = new AutoFillTextBox<StateModel>(list);
        autoFillTextBox.setFilterMode(true);
        autoFillTextBox.setListLimit(100);
        autoFillTextBox.setFilterStyle(AutoFillTextBox.FilterStyle.CONTAINS);
        autoFillTextBox.getTextField().setPrefWidth(200);
        return autoFillTextBox;
    }

    public static Label createLabel(String text) {
        Label label = new Label(text);
        label.setFont(Font.font(16));
        return label;
    }

    public static HBox connectTwoNodes(Node node1, Node node2) {
        HBox hBox = new HBox(node1, node2);
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(20);
        return hBox;
    }

    public static String transformArrayListInString(ArrayList arrayList) {
        String text = "";
        for (Object s : arrayList) {
            text += s.toString();
        }
        return text;
    }

    public static ArrayList<String> getArrayListWithUniqueElement(ArrayList<String> arrayList) {
        ArrayList<String> arrayListWithUniqueElement = new ArrayList<>();
        for (String s : arrayList) {
            if (arrayListWithUniqueElement.indexOf(s) < 0) {
                arrayListWithUniqueElement.add(s);
            }
        }
        return arrayListWithUniqueElement;
    }

    public static ArrayList<String> transformStringInArrayList(String s) {
        ArrayList arrayList = new ArrayList();
        for (char c : s.toCharArray()) {
            arrayList.add(c + "");
        }
        return arrayList;
    }

    public static String clearTextFromWhiteSpace(String text) {
        return text.replaceAll("\\s", "");
    }

    public static void waringForAction(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static boolean getConfirmation(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("confirmation");
        alert.setHeaderText(header);
        alert.setContentText(content);

        ButtonType buttonTypeOne = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType buttonTypeCancel = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeCancel);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeOne) {
            return true;
        } else {
            return false;
        }
    }

    public static void informForSuccessfulAction(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static Button createButton(String text) {
        Button button = new Button(text);
        button.setTooltip(getToolTip(text));
        changeBackgroundOnHoverUsingBinding(button);
        return button;
    }

    public static Tooltip getToolTip(String text) {
        Tooltip tooltip = new Tooltip(text);
        tooltip.setFont(Font.font(16));
        return tooltip;
    }


    private static void changeBackgroundOnHoverUsingBinding(Node node) {
        node.styleProperty().bind(
                Bindings.when(node.hoverProperty()).then(
                        new SimpleStringProperty(HOVERED_BUTTON_STYLE)
                ).otherwise(
                        new SimpleStringProperty(STANDARD_BUTTON_STYLE)
                )
        );
    }

    public static ImageView getImageView(String imageName) {
        //return new ImageView(new Image(Main.class.getResourceAsStream(IMAGE_PATH+imageName)));
        return new ImageView(new Image(IMAGE_PATH + imageName));
    }

    public static void saveOnFile(Inputs inputs, File file) throws IOException {

        FileOutputStream outputStream = null;
        ObjectOutputStream objectOutputStream = null;

        try {

            outputStream = new FileOutputStream(file);
            objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(inputs);
            System.out.println(file.getParent());
            System.out.println(file.getName());
            System.out.println(getCorrectFileName(file.getName()));
        } catch (Exception ex) {
            ex.printStackTrace();
            throw  ex;
        } finally {

            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (objectOutputStream != null) {
                try {
                    objectOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public static Inputs readInputsFromFile(File file) throws IOException, ClassNotFoundException {

       Inputs inputs = null;

        FileInputStream fin = null;
        ObjectInputStream ois = null;

        try {

            fin = new FileInputStream(file);
            ois = new ObjectInputStream(fin);
            inputs = (Inputs) ois.readObject();

        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        } finally {

            if (fin != null) {
                try {
                    fin.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        return inputs;

    }

    private static String getCorrectFileName(String name){
        String text = "";
        if(name.contains(".")){
            text += name.substring(0,name.indexOf("."));
        }
        return text;
    }
}
