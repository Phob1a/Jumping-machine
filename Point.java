import javafx.scene.shape.Circle;

import java.util.ArrayList;

public class Point {

    private int pointID;
    private double x;
    private double y;
    //private Circle point;
    private int traceID;
    ArrayList<Link>links;

    public Point(double x, double y, int traceID) {
        this.x = x;
        this.y = y;
        this.traceID = traceID;
        this.links=new ArrayList<>();
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
