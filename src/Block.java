import javafx.scene.control.Label;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

import java.util.ArrayList;

public class Block {

    private int id;
    private int fatherid;
    private String text;
    private String type;
    private Shape shape;
    private Label label;
    private Label idLabel;
    ArrayList<Point> linkpoints;


    public Block(String type, String text){
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

    public void addPgon() {
        double len=this.label.getText().length()*5.0;
        double x=this.linkpoints.get(0).getX();
        double y=this.linkpoints.get(0).getY();
        Polygon p=new Polygon();
        p.getPoints().addAll(new Double[]{
                x,y,
                x+10,y-10,
                x+len+40.0,y-10,
                x+len+40.0,y+10,
                x+10,y+10
        });
        this.setShape(p);
        if(this.linkpoints.size()==1){
            this.linkpoints.add(new Point(x+(len+40)*0.5,y,this.getId()));
        }
    }
}