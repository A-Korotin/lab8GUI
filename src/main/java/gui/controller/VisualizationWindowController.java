package gui.controller;

import dragon.Dragon;
import gui.animation.Sprite;
import gui.controller.context.Context;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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
    @FXML
    private ImageView imageView;

    private AnimationTimer animationTimer = null;

    private Dragon element;
    private int elementHash;

    private List<Image> flySprite;
    private List<Image> walkSprite;
    private List<Image> standSprite;
    private List<Image> swimSprite;


    public void setElement(Dragon dragon) {
        elementHash = dragon.getCreatorName().hashCode();
        this.element = dragon;
    }

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
        try {
            flySprite = loadSprite("src/main/resources/sprites/dragon/fly");
            standSprite = loadSprite("src/main/resources/sprites/dragon/idle");
            walkSprite = loadSprite("src/main/resources/sprites/dragon/walk");
            swimSprite = loadSprite("src/main/resources/sprites/dragon/swim");
        }
        catch (IOException ignored) {}
    }

    public void paint() {
        standClicked(null);
    }

    public void back(ActionEvent event) throws IOException {
        imageView.getScene().getWindow().hide();
    }

    private void runAnimation(List<Image> sprite) {
        if (animationTimer != null)
            animationTimer.stop();
        animationTimer = new Sprite(sprite, imageView, elementHash);
        animationTimer.start();
    }

    public void flyClicked(ActionEvent event) {
        runAnimation(flySprite);
    }
    public void standClicked(ActionEvent event){
        runAnimation(standSprite);
    }
    public void walkClicked(ActionEvent event) {
        runAnimation(walkSprite);
    }
    public void swimClicked(ActionEvent event) {
        runAnimation(swimSprite);
    }

    private List<Image> loadSprite(String folderPath) throws IOException{

        List<Image> out = new ArrayList<>();
        File folder = new File(folderPath);

        for (File file: folder.listFiles()) {
            try(FileInputStream fileInputStream = new FileInputStream(file)) {
                out.add(new Image(fileInputStream));
            }
        }

        return out;
    }

}
