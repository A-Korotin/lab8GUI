package gui.layout;

import java.util.List;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.HBox;



public class DiagonalBox extends HBox{

    @Override
    public void layoutChildren() {
        super.layoutChildren();
        final List<Node> children = getChildren();
        double xBias = 0, yBias = 0;
        double width = getWidth();
        double height = getHeight();

        int nChildren = getChildren().size();
        double xOffset = width / nChildren;
        double yOffset = height / nChildren;
        for(Node child: children) {
            child.setLayoutY(yBias);
            child.setLayoutX(xBias);
            yBias += yOffset;
            xBias += xOffset;
        }
    }
}