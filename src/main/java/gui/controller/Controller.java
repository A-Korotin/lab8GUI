package gui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class Controller {


    public void switchScene(ActionEvent event, String sceneFile) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL url = Controller.class.getResource(sceneFile);
        System.out.println(url);
        loader.setLocation(url);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(loader.load());
        stage.setResizable(false);
        stage.setTitle("Lab8");
        stage.setScene(scene);
        stage.show();
    }
}
