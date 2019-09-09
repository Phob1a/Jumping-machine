import javafx.scene.control.Label;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;

import java.util.ArrayList;

public class Link {


    private Line line; //The JavaFX object displayed on the Canvas.

    private int traceID1; //start point trace id
    private int traceID2; //end point trace id
    //private boolean settled;
    private Point p1;  //The start point
    private Point p2;  //The end point
    private Label tag; //The tag on the edge.
    private Link correspondLink; // For the link in the trace diagram, record its teacher.

    ArrayList<Binding> bindings; //Store all the identifiers on the edge.

    public Link(Line line, Point p1, Point p2){
        this.line=line;
        this.p1=p1;
        this.p2=p2;
        this.traceID1=p1.getTraceID();
        this.traceID2=p2.getTraceID();
        //this.settled=true;
        this.tag=new Label();
        this.bindings=new ArrayList<>();
    }

    public Link getCorrespondJKLink() {
        return correspondLink;
    }

    public void setCorrespondJKLink(Link correspondJKLink) {
        this.correspondLink = correspondJKLink;
    }

    public Line getLine() {
        return line;
    }

    public void setLine(Line line) {
        this.line = line;
    }

    public int getTraceID1() {
        return traceID1;
    }

    public void setTraceID1(int traceID1) {
        this.traceID1 = traceID1;
    }

    public int getTraceID2() {
        return traceID2;
    }

    public void setTraceID2(int traceID2) {
        this.traceID2 = traceID2;
    }

    public Point getP1() {
        return p1;
    }

    public void setP1(Point p1) {
        this.p1 = p1;
    }

    public Point getP2() {
        return p2;
    }

    public void setP2(Point p2) {
        this.p2 = p2;
    }

    public Label getTag() {
        return tag;
    }

    public void setTag(Label tag) {
        this.tag = tag;
    }


//    public boolean isSettled() {
//        return settled;
//    }
//
//    public void setSettled(boolean settled) {
//        this.settled = settled;
//    }


    public void locateBindings(){
        if(!this.getTag().getText().isEmpty()){
            double x=this.getP1().getX()+(this.getP2().getX()-this.getP1().getX())*0.25;
            double y=this.getP1().getY()+(this.getP2().getY()-this.getP1().getY())*0.25;
            this.getTag().setLayoutX(x);
            this.getTag().setLayoutY(y);
        }
        if(this.bindings.size()==1){
            double x=(this.getP2().getX()+this.getP1().getX())*0.5;
            double y=(this.getP2().getY()+this.getP1().getY())*0.5;
            this.bindings.get(0).getEllipse().setCenterX(x);
            this.bindings.get(0).getEllipse().setCenterY(y);
            this.bindings.get(0).setEllipseRX();
            this.bindings.get(0).getBindKey().setLayoutY(y-5);
        }
        else if(this.bindings.size()==2){
                double x1=this.getP1().getX()+(this.getP2().getX()-this.getP1().getX())*0.5;
                double x2=this.getP1().getX()+(this.getP2().getX()-this.getP1().getX())*0.75;
                double y1=this.getP1().getY()+(this.getP2().getY()-this.getP1().getY())*0.5;
                double y2=this.getP1().getY()+(this.getP2().getY()-this.getP1().getY())*0.75;
                this.bindings.get(0).getEllipse().setCenterX(x1);
                this.bindings.get(0).getEllipse().setCenterY(y1);
                this.bindings.get(1).getEllipse().setCenterX(x2);
                this.bindings.get(1).getEllipse().setCenterY(y2);
                this.bindings.get(0).setEllipseRX();
                this.bindings.get(0).getBindKey().setLayoutY(y1-5);
                this.bindings.get(1).setEllipseRX();
                this.bindings.get(1).getBindKey().setLayoutY(y2-5);
        }
    }
}
