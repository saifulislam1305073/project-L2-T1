package models;

import javafx.scene.Group;
import javafx.scene.shape.Line;
import tools.Vector;

/**
 * Created by shawontafsir on 20-Dec-15.
 */
public class OpponentBall extends Ball{
    private Line line;
    private boolean isAction = false;

    private static final String PLAYER_IMAGE_FILE="player1.png";
    public OpponentBall(Vector position, Vector speed, float radius, float mass, Group root) {
        super(position, speed, radius, mass, PLAYER_IMAGE_FILE);
        //super(position, speed, radius, mass, BALL_IMAGE_FILE);
        setSpeed(new Vector(0,0));


        this.getImageView().setOnMouseReleased(event -> {
            if (isAction){
                double dx=getPosition().getX()-(double) line.getEndX();
                double dy=getPosition().getY()-(double) line.getEndY();
                //addSpeed(dx,dy);
                setSpeed(new Vector(dx, dy));
                root.getChildren().remove(line);
            }
            isAction = false;

        });
        this.getImageView().setOnDragDetected(event -> {
            Vector m=new Vector((double) event.getX(),(double) event.getY());
            //for(PlayerBall p:players){
            // if(MathTools.TestIntersection(p,m)){
            //actionPlayer=p;
            line=new Line();
            line.setStartX(m.x);
            line.setStartY(m.y);
            line.setEndX(m.x);
            line.setEndY(m.y);
            root.getChildren().add(line);
            isAction=true;
            return;


            //actionPlayer=null;
            //isAction=false;
        });
        this.getImageView().setOnMouseDragged(event -> {
            if(isAction) {
                line.setStartX(getPosition().x);
                line.setStartY(getPosition().y);
                line.setEndX(event.getX());
                line.setEndY(event.getY());

            }
        });
    }
}
