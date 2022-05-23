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
import javafx.scene.control.ChoiceBox;
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
    private ChoiceBox<String> colorChoiceBox;
    @FXML
    private ChoiceBox<String> characterChoiceBox;
    @FXML
    private ChoiceBox<String> typeChoiceBox;
    @FXML
    private TextField depthField;
    @FXML
    private Text depthError;
    @FXML
    private TextField nTreasuresField;
    @FXML
    private Text nTreasuresError;
    @FXML
    private Text submitError;

    private final String[] characters = {"CUNNING", "GOOD", "CHAOTIC", "CHAOTIC_EVIL", "FICKLE", "null"};
    private final String[] types = {"UNDERGROUND", "AIR", "FIRE"};
    private final String[] colors = {"BLACK", "BLUE", "WHITE", "BROWN", "null"};

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
        depthError.setText("");
        nTreasuresError.setText("");
        submitError.setText("");
        dragonProperties.setText(Context.locale.getMsg("dragon_properties"));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        characterChoiceBox.setValue("character");
        typeChoiceBox.setValue("type");
        colorChoiceBox.setValue("color");
        characterChoiceBox.getItems().addAll(characters);
        typeChoiceBox.getItems().addAll(types);
        colorChoiceBox.getItems().addAll(colors);
    }

    public void validate(TextField field, Predicate<String> predicate, Text errorText, String errorBundle) {
        try {
            if (!predicate.test(field.getText())) {
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
        validate(nameField, s -> !s.trim().isEmpty(), nameError, "name_error");
    }

    public void xCoordTyped(KeyEvent event) {
        validate(xCoordField, s -> Float.parseFloat(s) != Float.NaN, xCoordError, null);
    }

    public void yCoordTyped(KeyEvent event) {
        validate(yCoordField, s -> Integer.parseInt(s) <= 998, yCoordError, "y_coord_error");
    }

    public void ageTyped(KeyEvent event) {
        validate(ageField, s -> Long.parseLong(s) > 0, ageError, "age_error");
    }

    public void depthTyped(KeyEvent event) {
        validate(depthField, s -> Double.parseDouble(s) != Double.NaN, depthError, null);
    }

    public void nTreasuresTyped(KeyEvent event) {
        validate(nTreasuresField, s -> s.equalsIgnoreCase("null") || Integer.parseInt(s) > 0, nTreasuresError, "n_treasures_error");
    }

    private boolean validFields() {
        try {
            if(nameField.getText().trim().isEmpty())
                return false;

            Float.parseFloat(xCoordField.getText().trim());

            if(Integer.parseInt(yCoordField.getText().trim()) > 998)
                return false;

            if (Long.parseLong(ageField.getText().trim()) <= 0)
                return false;

            if (characterChoiceBox.getValue().equals("character"))
                return false;

            if (typeChoiceBox.getValue().equals("type"))
                return false;

            if (colorChoiceBox.getValue().equals("color"))
                return false;

            Double.parseDouble(depthField.getText().trim());

            String nTreasures = nTreasuresField.getText().trim();
            if (!nTreasures.equals("null") && Integer.parseInt(nTreasures) <= 0)
                return false;

        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public void back(ActionEvent event) throws IOException {
        properties = null;
        dragonProperties.getScene().getWindow().hide();
        //switchScene(event, "/gui/table.fxml");
    }

    public void submit(ActionEvent event) throws IOException {
        if (!validFields()) {
            submitError.setText(Context.locale.getMsg("submit_error"));
            return;
        }
        properties = new Properties();
        properties.name = nameField.getText().trim();
        properties.age = Long.parseLong(ageField.getText().trim());
        properties.xCoord = Float.parseFloat(xCoordField.getText().trim());
        properties.yCoord = Integer.parseInt(yCoordField.getText().trim());
        properties.color = colorChoiceBox.getValue().equals("null") ? null : Color.valueOf(colorChoiceBox.getValue());
        properties.character = characterChoiceBox.getValue().equals("null") ? null : DragonCharacter.valueOf(characterChoiceBox.getValue());
        properties.type = DragonType.valueOf(typeChoiceBox.getValue());
        properties.depth = Double.parseDouble(depthField.getText().trim());
        properties.numberOfTreasures = nTreasuresField.getText().trim().equals("null") ? null : Integer.parseInt(nTreasuresField.getText().trim());
        properties.creator_name = Context.user.login;
        dragonProperties.getScene().getWindow().hide();
    }
}
