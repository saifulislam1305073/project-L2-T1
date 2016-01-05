package tools;

import models.Ball;

import java.beans.VetoableChangeListener;

/**
 * Created by GIASAHMED on 15-Dec-15.
 */
public class MathTools {
    public static boolean TestIntersection(Ball b,Vector p){
        double r;

        r=(b.getPosition().getX()-p.getX())*(b.getPosition().getX()-p.getX())+
                (b.getPosition().getY()-p.getY())*(b.getPosition().getY()-p.getY());
        r=(double) Math.sqrt((double) r);
        System.out.println(r);
        return !(r>b.getRadius());

    }

    public static double angle(Vector a, Vector b)
    {
        double dot = Vector.dot(a, b);
        double al=1,bl=1;
        if (a.length()!=0) al=a.length();
        if (b.length()!=0) bl=b.length();
        double angl =(double) Math.acos((double)(dot/(al*bl)));
        return angl;
    }
}
