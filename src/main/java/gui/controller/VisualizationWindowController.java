package gui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class VisualizationWindowController extends Controller implements Initializable {
    @Override
    protected void localize() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void back(ActionEvent event) throws IOException {
        switchScene(event, "/gui/table.fxml");
    }
}
