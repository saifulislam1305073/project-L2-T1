package fxmain;

import javafx.fxml.FXML;
import javafx.scene.shape.Circle;
import models.Player;

import java.awt.*;

/**
 * Created by GIASAHMED on 09-Dec-15.
 */
public class PlayGameController {
    Player player1 = new Player(400, 300, 40 , .5, 20);
    @FXML
    private Circle Player1;
    /*@FXML
    public void clickAction()
    {
        Player1.setLayoutX(Player1.getLayoutX()+50);
    }*/
    @FXML
    public final void dragAction()
    {
     /*//   for (int i = 1; i < 200; i++)
        PointerInfo a = MouseInfo.getPointerInfo();
        Point b = a.getLocation();
        System.out.println("on drag");
        int x = (int) b.getX();
        int y = (int) b.getY();
        double distance = Math.sqrt((x-player1.getCenterX())*(x-player1.getCenterX()) + (y-player1.getCenterY())*(y-player1.getCenterY()));
        player1.updatePhase(distance*2, .5, .5);
        System.out.println(player1.getVelocity() + " " + player1.getAcceleration() + " " + player1.getTime() + " " + distance);*/
    }

    @FXML
    public void releaseAction()
    {/*
        player1.setCenterX(100);
        /*for (int i = 1; i < 1000000000; i++) {
            if (i % 1000 == 0)

                player1.setCenterX(Player1.getLayoutX()+1);
        }*/
    }

    /*@FXML

    public void pressAction()
    {
        //for (int i = 1; i < 200; i++)
            Player1.setLayoutX(Player1.getLayoutX()+50);
    }*/
}
