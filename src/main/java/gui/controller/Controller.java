package gui.controller;

import gui.controller.context.Context;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;
import locales.Locale;

import java.io.IOException;
import java.net.URL;
import java.util.function.Consumer;

public abstract class Controller {

    protected abstract void localize();

    public final void switchScene(ActionEvent event, String sceneFile) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL url = Controller.class.getResource(sceneFile);
        loader.setLocation(url);

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();

        Scene scene = new Scene(loader.load());
        Controller controller = loader.getController();
        if (Context.locale != null)
            controller.localize();
        stage.setResizable(false);

        stage.setTitle("Lab8");
        stage.setScene(scene);
        Node mainAnchorRoot = scene.getRoot().getChildrenUnmodifiable().get(0);
        fadeIn(mainAnchorRoot, Duration.millis(700));
    }

    public final<T> T openNewWindow(String sceneFile) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Context.class.getResource(sceneFile));
        Stage stage = new Stage();
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);

        stage.setTitle("lab8");
        if (Context.locale != null) {
            Controller controller = loader.getController();
            controller.localize();
        }
        stage.showAndWait();
        return loader.getController();
    }
    public final void fadeIn(Node node, Duration duration) {
        FadeTransition transition = new FadeTransition(duration);
        transition.setNode(node);
        transition.setFromValue(0.1);
        transition.setToValue(1);
        transition.play();
    }

}
