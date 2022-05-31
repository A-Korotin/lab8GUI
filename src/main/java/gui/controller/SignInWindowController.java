package gui.controller;

import exceptions.ServerUnreachableException;
import gui.controller.context.Context;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import locale.Locale;
import net.auth.ServerAuthenticator;
import net.auth.User;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SignInWindowController extends Controller implements Initializable {
    @FXML
    private AnchorPane rootPane;

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

    @FXML
    private Text signUpQuestion;

    @FXML
    private Text errorText;

    @FXML
    private ImageView duck;

    private static final String HOST = "localhost";
    private static final int PORT = 4444;

    private final String[] languages = {"default", "Русский", "Slovenščina", "Svenska", "Español (Ecuador)"};

    private ServerAuthenticator authenticator;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            authenticator = new ServerAuthenticator(HOST, PORT);
        } catch (IOException ignored) {
        }
        languageBox.getItems().addAll(languages);
        languageBox.setValue("Language");
        languageBox.setOnAction(this::reloadLocale);
        if (!Context.locale.getName().equals("default"))
            languageBox.setValue(Context.locale.getName());
        errorText.setText("");
    }

    public void signUpButtonClicked(ActionEvent event) throws IOException {
        switchScene(event, "/gui/signUpWindow.fxml");
    }

    @Override
    protected void localize() {
        signInText.setText(Context.locale.getMsg("sign_in"));
        welcomeText.setText(Context.locale.getMsg("welcome_back"));
        signUpQuestion.setText(Context.locale.getMsg("dont_have_account"));
        signINButton.setText(Context.locale.getMsg("sign_in"));
        signUpButton.setText(Context.locale.getMsg("sign_up"));
        loginField.setPromptText(Context.locale.getMsg("login"));
        passwordField.setPromptText(Context.locale.getMsg("password"));
        errorText.setText("");
    }

    public void signInButtonClicked(ActionEvent event) throws IOException {
        String log = loginField.getText().trim();
        String pass = passwordField.getText().trim();
        if (log.isEmpty() || pass.isEmpty()) {
            errorText.setText(Context.locale.getMsg("empty_login_or_password"));
            return;
        }
        Runnable task = () -> {
            User user = new User(log, pass);
            boolean validUser;
            try {
                validUser = authenticator.isValidUser(user);
            } catch (ServerUnreachableException e) {
                ErrorWindowController controller =
                        null;
                try {
                    controller = openSubStage(event, "/gui/errorWindow.fxml");
                } catch (IOException ignored) {}
                handleErrorWindow(controller.getButtonPressed(), rootPane);
                return;
            }

            if (!validUser)
                errorText.setText(Context.locale.getMsg("invalid_login_or_password"));
            else {
                Context.user = user;
                try {
                    switchScene(event, "/gui/table.fxml");
                } catch (IOException ignored) {}
            }
        };
        Platform.runLater(task);
    }

    public void reloadLocale(ActionEvent event) {
        String lang = languageBox.getValue();
        System.out.println(languageBox.getValue());
        Context.locale = new Locale(lang);
        localize();
    }

    public void duckClicked() {
        System.out.println("duck");
    }

    public void duckEntered() {
        ColorAdjust bright = new ColorAdjust();
        bright.setBrightness(0.5);
        duck.setEffect(bright);
    }

    public void duckExited() {
        ColorAdjust bright = new ColorAdjust();
        bright.setBrightness(0);
        duck.setEffect(bright);
    }
}
