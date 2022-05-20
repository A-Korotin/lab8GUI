package gui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SignInWindowController extends Controller implements Initializable {
    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button signINButton;

    @FXML
    private Button signUpButton;

    @FXML
    private ChoiceBox<String> languageBox;

    @FXML
    private Text welcomeText;

    @FXML
    private Text signInText;


    private final String[] languages = {"Русский", "Slovenščina", "Svenska", "Español (Ecuador)"};

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        languageBox.getItems().addAll(languages);
        languageBox.setValue("Language");
    }

    public void signUpButtonClicked(ActionEvent event) throws IOException {
        switchScene(event, "/gui/signUpWindow.fxml");
    }

    public void signInButtonClicked(ActionEvent event) throws IOException{

    }
}
