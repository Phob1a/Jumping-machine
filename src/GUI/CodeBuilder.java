package GUI;

import com.sun.javafx.scene.paint.GradientUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

import java.util.ArrayList;

public class CodeBuilder {

    private int cnt;
    private Point[] points;

    public int getCnt() {
        return cnt;
    }

    ArrayList<GraphSyntax>graphTree;

    class GraphSyntax{
        private int id;
        private String text;
        Shape shape;
        private String shapeType;
        Label label;
        Point linkpoint;


        @FXML
        private Pane root;

        public GraphSyntax(String text,double x,double y){
            this.id=getCnt();
            linkpoint=new Point(x,y);
            label=new Label(text);
            label.setLayoutX(x+15);
            label.setLayoutY(y-5);
            shapeType="Pentagon";
            shape=new Polygon();
            ((Polygon) shape).getPoints().addAll(new Double[]{
                    x, y,
                    x + 10, y - 10,
                    x + 110, y - 10,
                    x + 110, y + 10,
                    x + 10, y + 10
            });

            Line[] line;
            this.text=text;
        }

        public void test(){
            GraphSyntax g=new GraphSyntax("print \"a\"",3.0,5.0);
            root.getChildren().add(g.shape);
        }
    }
}
