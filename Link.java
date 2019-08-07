import javafx.scene.control.Label;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;

public class Link {


    private Line line;

    private int traceID1;
    private int traceID2;
    private boolean settled;
    private Point p1;
    private Point p2;
    Shape executeTag;
    private Label tag;

    public Link(Line line, Point p1, Point p2){
        this.line=line;
        this.p1=p1;
        this.p2=p2;
        this.traceID1=p1.getTraceID();
        this.traceID2=p2.getTraceID();
        this.settled=true;
        this.tag=new Label();
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


    public boolean isSettled() {
        return settled;
    }

    public void setSettled(boolean settled) {
        this.settled = settled;
    }
}
