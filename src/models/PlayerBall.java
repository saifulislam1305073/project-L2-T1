package models;

import client.ClientMain;
import fxmain.Main;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import tools.MathTools;
import tools.Vector;

/**
 * Created by GIASAHMED on 08-Dec-15.
 */
public class PlayerBall extends Ball {
    private static Main main;
    private static ClientMain cMain;
    private Line line;
    private boolean isAction = false;

    //private static final String PLAYER_IMAGE_FILE="player2.png";
    //private static final String BALL_IMAGE_FILE = "Ball.png";
    public PlayerBall(Vector position, Vector speed, float radius, float mass, Group root,String PLAYER_IMAGE_FILE,boolean control) {
        super(position, speed, radius, mass, PLAYER_IMAGE_FILE);
        //super(position, speed, radius, mass, BALL_IMAGE_FILE);
        setSpeed(new Vector(0,0));



        if(control) {
            this.getImageView().setOnMouseReleased(event -> {
                if (isAction) {
                    double dx = getPosition().getX() - (double) line.getEndX();
                    double dy = getPosition().getY() - (double) line.getEndY();
                    //addSpeed(dx,dy);
                    setSpeed(new Vector(3*dx, 3*dy));
                    root.getChildren().remove(line);
                    if(cMain!=null){
                        cMain.notifyServer(this, 5*dx+" "+5*dy);
                    }
                }
                isAction = false;

            });
            this.getImageView().setOnDragDetected(event -> {
                Vector m = new Vector((double) event.getX(), (double) event.getY());
                line = new Line();
                line.setStartX(m.x);
                line.setStartY(m.y);
                line.setEndX(m.x);
                line.setEndY(m.y);
                root.getChildren().add(line);
                isAction = true;
                return;


                //actionPlayer=null;
                //isAction=false;
            });
            this.getImageView().setOnMouseDragged(event -> {
                if (isAction) {
                    line.setStartX(getPosition().x);
                    line.setStartY(getPosition().y);
                    line.setEndX(event.getX());
                    line.setEndY(event.getY());

                }
            });
        }
    }

    public void setIsAction(boolean isAction) {
        this.isAction = isAction;
    }


    public static void setcMain(ClientMain cMain) {
        PlayerBall.cMain = cMain;
    }

    public static void setMain(Main main) {
        PlayerBall.main = main;
    }
}
