package gui.animation;

import javafx.animation.AnimationTimer;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;

import javafx.scene.image.ImageView;
import java.util.List;

public class Sprite extends AnimationTimer {
    private final List<Image> images;
    private final ImageView imageView;
    private int currentImage = -1;
    private long prevFrame = System.currentTimeMillis();
    private static final double FPS = 10.0;
    private static final int FRAME_TIME = (int) (Math.ceil(1/FPS  * 1000));

    public Sprite(List<Image> images, ImageView imageView, int colorNumber) {
        this.images = images;
        this.imageView = imageView;


        double h = colorNumber / (double) Integer.MAX_VALUE; // random number between -1.0 and 1.0
        double s = 0.0;
        double b = 0.0;
        imageView.setEffect(new ColorAdjust(h, s, b, 0.0));
    }

    @Override
    public void handle(long now) {
        long curFrame  = System.currentTimeMillis();
        if (curFrame - prevFrame >= FRAME_TIME) {
            currentImage = ++currentImage % images.size();
            Image image = images.get(currentImage);
            imageView.setImage(image);
            prevFrame = curFrame;
        }

    }
}
