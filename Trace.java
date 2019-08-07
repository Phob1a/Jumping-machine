import javafx.scene.control.Label;
import javafx.scene.shape.Shape;

import java.util.ArrayList;

public class Trace {

    private int id;
    private int fatherid;
    private String text;
    private String type;
    private Shape shape;
    private Label label;
    private Label idLabel;
    ArrayList<Point> linkpoints;


    public Trace(String type,String text){
        this.type=type;
        this.text=text;
        this.linkpoints=new ArrayList<>();
        this.idLabel=new Label();
    }


    public Label getIdLabel() {
        return idLabel;
    }

    public void setIdLabel(Label idLabel) {
        this.idLabel = idLabel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFatherid() {
        return fatherid;
    }

    public void setFatherid(int fatherid) {
        this.fatherid = fatherid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Shape getShape() {
        return shape;
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }
}