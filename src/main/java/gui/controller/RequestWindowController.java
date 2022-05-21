package gui.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import locales.Locale;


import java.net.URL;
import java.util.ResourceBundle;

public class RequestWindowController extends Controller implements Initializable {
    @FXML
    private TextField nameField;

    @FXML
    private Text nameError;

    @Override
    protected void localize() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nameError.setText("");
    }


    public void nameTyped(KeyEvent event) {
        String name = nameField.getText().trim();
        if (name.isEmpty())
            nameError.setText("name is empty!");
        else
            nameError.setText("");

    }
}
