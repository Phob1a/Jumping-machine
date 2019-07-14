package GUI;

/**
 * link-point and jumpabout
 */

public class Point {

    private double x;

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

    private double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }


    public static double calDis(Point a,Point b){
        return Math.sqrt((a.getX()-b.getX())*(a.getX()-b.getX())+(a.getY()-b.getY())*(a.getY()-b.getY()));
    }
}
