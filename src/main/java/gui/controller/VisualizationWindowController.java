package gui.controller;

import gui.controller.context.Context;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class VisualizationWindowController extends Controller implements Initializable {
    @FXML
    private Button backButton;
    @FXML
    private Button flyButton;
    @FXML
    private Button walkButton;
    @FXML
    private Button standButton;
    @FXML
    private Button swimButton;

    @Override
    protected void localize() {
        backButton.setText(Context.locale.getMsg("back"));
        flyButton.setText(Context.locale.getMsg("fly"));
        walkButton.setText(Context.locale.getMsg("walk"));
        standButton.setText(Context.locale.getMsg("stand"));
        swimButton.setText(Context.locale.getMsg("swim"));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void back(ActionEvent event) throws IOException {
        switchScene(event, "/gui/table.fxml");
    }
}
