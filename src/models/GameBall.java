package models;

import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.paint.*;
import javafx.scene.shape.Line;
import tools.Vector;

import java.awt.*;

/**
 * Created by GIASAHMED on 08-Dec-15.
 */
public class GameBall extends Ball{
    private static final String BALL_IMAGE_FILE="ball.png";

    public GameBall(Vector position, Vector speed, float radius, double mass, Group root) {
        super(position, speed, radius, mass,BALL_IMAGE_FILE );
        setSpeed(new Vector(0,0));

    }
}
