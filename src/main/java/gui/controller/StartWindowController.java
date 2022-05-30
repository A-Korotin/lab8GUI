package gui.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class StartWindowController extends Controller implements Initializable {

    private final String[] languages = {"Русский", "Slovenščina", "Svenska", "Español (Ecuador)"};

    @Override
    protected void localize() {

    }

    @FXML
    private Text info_field;

    @FXML
    private ChoiceBox<String> language_choiceBox;

    @FXML
    private void loginButtonController() {
        System.out.println("login button clicked");
        info_field.setText("login button clicked");
    }

    @FXML
    private void registerButtonController() {
        System.out.println("register button clicked");
        info_field.setText("register button clicked");
    }

    @FXML
    private void choiceBoxController() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        language_choiceBox.setValue("language");
        language_choiceBox.getItems().addAll(languages);
    }
}
