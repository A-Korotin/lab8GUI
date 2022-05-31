package gui.controller;

import gui.controller.context.Context;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

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

    private<T> StageAndController<T> loadSubStage(ActionEvent event, String sceneFile) throws IOException {
        Node source = (Node) event.getSource();
        return loadSubStage(source, sceneFile);
    }

    private<T> StageAndController<T> loadSubStage(Node source, String sceneFile) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Context.class.getResource(sceneFile));
        Stage subStage = new Stage();
        Stage parentStage = (Stage)(source).getScene().getWindow();
        subStage.initOwner(parentStage);
        subStage.initModality(Modality.WINDOW_MODAL);
        Scene scene = new Scene(loader.load());
        subStage.setScene(scene);
        subStage.setResizable(false);
        subStage.setAlwaysOnTop(true);
        subStage.setTitle("lab8");
        if (Context.locale != null) {
            Controller controller = loader.getController();
            controller.localize();
        }
        StageAndController<T> stageAndController = new StageAndController<>();
        stageAndController.stage = subStage;
        stageAndController.controller = loader.getController();

        return stageAndController;
    }

    public final<T> T openSubStage(ActionEvent event, String sceneFile) throws IOException {
        StageAndController<T> stageAndController = loadSubStage(event, sceneFile);
        Stage subStage = stageAndController.stage;
        T controller = stageAndController.controller;
        subStage.showAndWait();
        return controller;
    }

    public final<T> T openSubStage(ActionEvent event, String sceneFile, Consumer<T> additional)  throws IOException{
        StageAndController<T> stageAndController = loadSubStage(event, sceneFile);
        Stage subStage = stageAndController.stage;
        T controller = stageAndController.controller;
        additional.accept(controller);
        subStage.showAndWait();
        return controller;
    }

    public final<T> T openSubStage(Node source, String sceneFile, Consumer<T> additional) throws IOException {
        StageAndController<T> stageAndController = loadSubStage(source, sceneFile);
        Stage subStage = stageAndController.stage;
        T controller = stageAndController.controller;
        additional.accept(controller);
        subStage.showAndWait();
        return controller;
    }

    public void handleErrorWindow(String button, Node node) {
        switch (button) {
            case "exit", "close" -> node.getScene().getWindow().hide();
            case "wait" -> {
            }
        }
    }

    public final void fadeIn(Node node, Duration duration) {
        FadeTransition transition = new FadeTransition(duration);
        transition.setNode(node);
        transition.setFromValue(0.1);
        transition.setToValue(1);
        transition.play();
    }



    private static class StageAndController<T> {
        public Stage stage;
        public T controller;
    }
}
