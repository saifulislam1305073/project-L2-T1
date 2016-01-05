package models;

import javafx.scene.shape.Circle;

/**
 * Created by GIASAHMED on 08-Dec-15.
 */
public class Player {
    double centerX;
    double centerY;
    double radius;
    double mass;
    double velocity, acceleration, time;

    public Player(double centerX, double centerY, double radius, double acceleration, double mass) {
        this.mass = mass;
        this.velocity = 0;
        this.acceleration = acceleration;
        this.time = 0;
    }



    /*public double getCenterX() {
        return centerX;
    }

    public double getCenterY() {
        return centerY;
    }*/

    public double getVelocity() {
        return velocity;
    }

    public double getAcceleration() {
        return acceleration;
    }

    public double getTime() {
        return time;
    }

    public void updatePosition(double centerX, double centerY, double radius)
    {
        this.centerX = centerX;
        this.centerY = centerY;
        this.radius = radius;
    }
    public void updatePhase(double velocity, double time, double acceleration)
    {
        this.velocity = velocity;
        this.time = time;
        this.acceleration = acceleration;
    }
}
