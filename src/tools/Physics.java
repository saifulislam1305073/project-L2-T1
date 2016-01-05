package tools;

import models.Ball;

/**
 * Created by GIASAHMED on 08-Dec-15.
 */
public class Physics {
    public static boolean collision(Ball b1,Ball b2){
        double distance=Vector.distance(b1.getPosition(),b2.getPosition());
        return distance<=(b1.getRadius()+b2.getRadius());
    }

    public static void collideResponse(Ball b1,Ball b2,double dt){

        double ax=b2.getPosition().x-b1.getPosition().x;
        double ay=b2.getPosition().y-b1.getPosition().y;

        double ang = (Math.atan2(ay,ax)+2*Math.PI)%(2*Math.PI);

        System.out.println("I'm here");

        b2.setPosition(new Vector(b1.getPosition().x + b1.getRadius()*Math.cos(ang) + b2.getRadius()*Math.cos(ang)+ 2*Math.cos(ang),
                b1.getPosition().y+b1.getRadius()*Math.sin(ang)+b2.getRadius()*Math.sin(ang) + 2*Math.sin(ang)));
        b1.setPosition(new Vector(b2.getPosition().x + b1.getRadius()*Math.cos(ang+Math.PI) + b2.getRadius()*Math.cos(ang+Math.PI)+ 3*Math.cos(ang+Math.PI),
                b2.getPosition().y+b1.getRadius()*Math.sin(ang+Math.PI)+b2.getRadius()*Math.sin(ang+Math.PI) + 3*Math.sin(ang+Math.PI)));



        double a1, a2;
        if(b1.getSpeed().getDirection()>ang) a1 = ang+Math.PI/2;
        else a1 = ang-Math.PI/2;
        if(b2.getSpeed().getDirection()>((ang+Math.PI)%(2*Math.PI))) a2 = ang+Math.PI+Math.PI/2;
        else a2 = ang+Math.PI-Math.PI/2;


        double b1x = b2.getSpeed().length()*Math.cos(ang+Math.PI-b2.getSpeed().getDirection())*Math.cos(ang+Math.PI)
                +b1.getSpeed().length()*Math.cos(b1.getSpeed().getDirection()-a1)*Math.cos(a1);
        double b1y = b2.getSpeed().length()*Math.cos(ang+Math.PI-b2.getSpeed().getDirection())*Math.sin(ang+Math.PI)
                +b1.getSpeed().length()*Math.cos(b1.getSpeed().getDirection()-a1)*Math.sin(a1);

        double b2x = b1.getSpeed().length()*Math.cos(ang-b1.getSpeed().getDirection())*Math.cos(ang)
                +b2.getSpeed().length()*Math.cos(b2.getSpeed().getDirection()-a2)*Math.cos(a2);
        double b2y = b1.getSpeed().length()*Math.cos(ang-b1.getSpeed().getDirection())*Math.sin(ang)
                +b2.getSpeed().length()*Math.cos(b2.getSpeed().getDirection()-a2)*Math.sin(a2);

        b1.setSpeed(new Vector(b1x*0.9, b1y*0.9));
        b2.setSpeed(new Vector(b2x*0.9, b2y*0.9));


    }

}
