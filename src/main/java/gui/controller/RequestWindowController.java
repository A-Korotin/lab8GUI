package gui.controller;

import dragon.Color;
import dragon.DragonCharacter;
import dragon.DragonType;
import gui.controller.context.Context;
import io.Properties;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class RequestWindowController extends Controller implements Initializable {
    @FXML
    private Button backButton;
    @FXML
    private Button submitButton;
    @FXML
    private Text dragonProperties;
    @FXML
    private TextField nameField;
    @FXML
    private Text nameError;
    @FXML
    private TextField xCoordField;
    @FXML
    private Text xCoordError;
    @FXML
    private TextField yCoordField;
    @FXML
    private Text yCoordError;
    @FXML
    private TextField ageField;
    @FXML
    private Text ageError;

    @FXML
    private Properties properties = null;

    public Properties getProperties() {
        return properties;
    }

    @Override
    protected void localize() {
        backButton.setText(Context.locale.getMsg("back"));
        submitButton.setText(Context.locale.getMsg("submit"));
        nameError.setText("");
        xCoordError.setText("");
        yCoordError.setText("");
        ageError.setText("");
        dragonProperties.setText(Context.locale.getMsg("dragon_properties"));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void validate(TextField field, Predicate<String> predicate, Text errorText, String errorBundle) {
        try {
            if (predicate.test(field.getText())) {
                errorText.setText(Context.locale.getMsg(errorBundle));
                field.setStyle("-fx-control-inner-background: #FF8752");
            } else {
                errorText.setText("");
                field.setStyle("-fx-control-inner-background: #7DFF7D");
            }
        } catch (NumberFormatException e) {
            errorText.setText(Context.locale.getMsg("number_format"));
            field.setStyle("-fx-control-inner-background: #FF8752");
        }

    }

    public void nameTyped(KeyEvent event) {
        validate(nameField, s -> s.trim().isEmpty(), nameError, "name_error");
    }

    public void xCoordTyped(KeyEvent event) {
        validate(xCoordField, s -> Float.parseFloat(s) == Float.NaN, xCoordError, null);
    }

    public void yCoordTyped(KeyEvent event) {
        validate(yCoordField, s -> Integer.parseInt(s) > 998, yCoordError, "y_coord_error");
    }

    public void ageTyped(KeyEvent event) {
        validate(ageField, s -> Long.parseLong(s) <= 0, ageError, "age_error");
    }

    public void back(ActionEvent event) throws IOException {
        properties = null;
        dragonProperties.getScene().getWindow().hide();
        //switchScene(event, "/gui/table.fxml");
    }

    public void submit(ActionEvent event) throws IOException {
        properties = new Properties();
        properties.name = nameField.getText().trim();
        properties.age = Long.parseLong(ageField.getText().trim());
        properties.xCoord = Float.parseFloat(xCoordField.getText().trim());
        properties.yCoord = Integer.parseInt(yCoordField.getText().trim());
        properties.color = Color.BLACK;
        properties.character = DragonCharacter.GOOD;
        properties.type = DragonType.FIRE;
        properties.depth = 12;
        properties.numberOfTreasures = 421;
        properties.creator_name = Context.user.login;
        dragonProperties.getScene().getWindow().hide();
    }
}
