package gui.controller;

import gui.controller.context.Context;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.util.Duration;
import locales.Locale;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SignUpWindowController extends Controller implements Initializable {
    @FXML
    private Text welcomeText;
    @FXML
    private Text signUpText;
    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField repeatPasswordField;
    @FXML
    private Button signUpButton;
    @FXML
    private Button backButton;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void backButtonClicked(ActionEvent event) throws IOException {
        switchScene(event, "/gui/signInWindow.fxml");
    }

    public void signUpButtonClicked(ActionEvent event) throws IOException {

    }

    @Override
    protected void localize() {
        welcomeText.setText(Context.locale.getMsg("welcome"));
        signUpText.setText(Context.locale.getMsg("sign_up"));
        backButton.setText(Context.locale.getMsg("back"));
        signUpButton.setText(Context.locale.getMsg("sign_up"));
        loginField.setPromptText(Context.locale.getMsg("login"));
        passwordField.setPromptText(Context.locale.getMsg("password"));
        repeatPasswordField.setPromptText(Context.locale.getMsg("repeat_password"));
    }
}
