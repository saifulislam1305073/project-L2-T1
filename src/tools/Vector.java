package tools;

import java.util.Map;

public class Vector {
    public double x;
    public double y;

    public Vector() {
        x=0;
        y=0;
    }

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public static double dot(Vector v1,Vector v2){
        return v1.x*v2.x+v1.y*v2.y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
    public static double distance(Vector v1,Vector v2){
        return Math.sqrt((v1.x-v2.x)*(v1.x-v2.x)+(v1.y-v2.y)*(v1.y-v2.y));
    }
    public void normalize(){
        double tx=x/length();
        double ty=y/length();
        x=tx;
        y=ty;
    }

    public double length()
    {
        return (double) Math.sqrt(x*x+y*y);
    }
    public String toString(){
        return "("+x+","+y+")";
    }

    public double getDirection(){
        return (Math.atan2(y,x)+2*Math.PI)%(2*Math.PI);
    }

}
