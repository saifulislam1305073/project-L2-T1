package models;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import tools.Vector;

/**
 * Created by GIASAHMED on 08-Dec-15.
 */
public class Ball {
    Vector position;
    Vector speed = new Vector();
    double radius;
    double mass;
    ImageView imageView;
    //static int goal = 0;
    //static int oponentGoal = 0;

    public Ball(Vector position, Vector speed, double radius, double mass, String imageFileName) {
        Image image=new Image(imageFileName);
        this.imageView=new ImageView(image);

        this.position = position;
        this.speed = speed;
        this.radius = radius;
        this.mass = mass;
        imageView.setX(position.getX()-25);
        imageView.setY(position.getY()-25);
    }

    public double getRadius() {
        return radius;
    }

    public void update(double dt){
       // System.out.println(dt);

        position.x+=speed.x*dt;
        position.y+=speed.y*dt;
        if (position.y-getRadius()<82){
            setPosition(new Vector(position.x, 82+getRadius()));
            speed.y*=-0.9;
        }
        if(position.y+getRadius()>520 ) {
            setPosition(new Vector(position.x, 520-getRadius()));
            speed.y*=-0.9;
        }
        if (position.x-getRadius()<105 && (position.y-getRadius()<227 || position.y+getRadius()>375)){
            setPosition(new Vector(105+getRadius(), position.y));
            speed.x*= -0.9;
        }
        if(position.x+getRadius()>855 && (position.y-getRadius()<227 || position.y+getRadius()>378)){
            setPosition(new Vector( 855-getRadius(), position.y));
            speed.x*=-0.9;
        }





        if(position.y-getRadius()>227 && position.y+getRadius()<375 && position.x-getRadius()>855 && position.x+getRadius()<902)
        {
            if(position.y+getRadius()>=374){
                setPosition(new Vector(position.x,position.y));
                speed.y*=-0.9;
            }
            if(position.y-getRadius()<=226){
                setPosition(new Vector(position.x,position.y));
                speed.y*=-.9;
            }
        }

        if(position.x+getRadius()>=902){
            setPosition(new Vector(902-getRadius(),position.y));
            speed.x*=-.9;
        }


        if(position.y-getRadius()>227 && position.y+getRadius()<375 && position.x-getRadius()>58 && position.x+getRadius()<105)
        {
            /*if(position.x+getRadius()>855 && position.x+getRadius()<899)
            {
                setPosition(new Vector(position.x,855-getRadius()));
            }*/
            if(position.y+getRadius()>=374) {
                setPosition(new Vector(position.x,position.y));
                speed.y*=-0.9;
            }
            if(position.y-getRadius()<=226){
                setPosition(new Vector(position.x,position.y));
                speed.y*=-.9;
            }

            //if(imageFilenAME)
        }

        if(position.x-getRadius()<=58){
            setPosition(new Vector(60+getRadius(),position.y));
            speed.x*=-.9;
        }


        //
        double l = speed.length();
        l-= 600*dt;
        if(l<0) l=0;
        setSpeed(new Vector(l*Math.cos(speed.getDirection()),l*Math.sin(speed.getDirection())));

        imageView.setY(position.getY()-getRadius());
        imageView.setX(position.getX()-getRadius());
    }




    public Vector getPosition() {
        return position;
    }

    public void setPosition(Vector position) {
        this.position = position;
        imageView.setY(position.getY()-25);
        imageView.setX(position.getX()-25);
    }

    public Vector getSpeed() {
        return speed;
    }

    public void setSpeed(Vector speed) {
        this.speed = speed;
    }


    public void setRadius(double radius) {
        this.radius = radius;
    }

    public double getMass() {
        return mass;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public ImageView getImageView() {
        return imageView;
    }
    public void addSpeed(double dx, double dy){
        speed.x+=dx;
        speed.y+=dy;
    }
}
