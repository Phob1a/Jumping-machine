import javafx.scene.shape.Circle;

import java.util.ArrayList;

public class Point {

    private int pointID; //Point id;
    private double x;    //axis of the point
    private double y;
    Circle pt;    //The javaFX object displayed on Canvas

    private int traceID; //To record the polygon ID that the point is placed
    ArrayList<Link>links; //The links started or end on the point are stored in the array list

    public Point(double x, double y, int traceID) {
        this.x = x;
        this.y = y;
        this.traceID = traceID;
        this.links=new ArrayList<>();
        this.pt=new Circle(x,y,3.0);
    }

    public Point(){

    }

    public Point(double x,double y){
        this.x=x;
        this.y=y;
    }

    public int getPointID() {
        return pointID;
    }

    public void setPointID(int pointID) {
        this.pointID = pointID;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

//    public Circle getPoint() {
//        return point;
//    }
//
//    public void setPoint(Circle point) {
//        this.point = point;
//    }

    public int getTraceID() {
        return traceID;
    }

    public void setTraceID(int traceID) {
        this.traceID = traceID;
    }


}
