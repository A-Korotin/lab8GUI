package gui.controller;

import exceptions.ServerUnreachableException;
import gui.controller.context.Context;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import net.auth.ServerAuthenticator;
import net.auth.User;
import net.codes.EventCode;
import net.codes.ExitCode;

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
    @FXML
    private Text errorText;

    private static final String HOST = "localhost";
    private static final int PORT = 4444;

    private ServerAuthenticator authenticator;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            authenticator = new ServerAuthenticator(HOST, PORT);
        } catch (IOException ignored) {}
        errorText.setText("");
    }

    public void backButtonClicked(ActionEvent event) throws IOException {
        switchScene(event, "/gui/signInWindow.fxml");
    }

    public void signUpButtonClicked(ActionEvent event) throws IOException {
        String login = loginField.getText().trim();
        String password = passwordField.getText().trim();
        String repeatPassword = repeatPasswordField.getText().trim();

        if (login.isEmpty() || password.isEmpty()) {
            errorText.setText(Context.locale.getMsg("empty_login_or_password"));
            return;
        }

        if (!password.equals(repeatPassword)) {
            errorText.setText(Context.locale.getMsg("passwords_dont_match"));
            return;
        }

        User user = new User(login, password);
        boolean registered;
        try {
            registered = authenticator.registerUser(user);
        }catch (ServerUnreachableException e) {
            ErrorWindowController controller =
                    openSubStage(event, "/gui/errorWindow.fxml",c -> c.displayMsg(ExitCode.SERVER_ERROR, EventCode.SERVER_UNREACHABLE));
            handleErrorWindow(controller.getButtonPressed(), welcomeText);
            return;
        }

        if (!registered) {
            errorText.setText(Context.locale.getMsg("login_already_exists"));
            return;
        }

        Context.user = user;
        switchScene(event, "/gui/table.fxml");
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
