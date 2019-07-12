import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class DrawDemo extends Application {


    public static void setPolygon(Polygon p,double x, double y){
        p.getPoints().addAll(new Double[]{
                x,y,
                x+10.0,y-10.0,
                x+110.0,y-10.0,
                x+110.0,y+10.0,
                x+10.0,y+10.0,
        });
    }

    @Override
    public void start(Stage stage) throws Exception {

        Polygon polygon = new Polygon();

        //Adding coordinates to the polygon
        setPolygon(polygon,50,50);
        polygon.setFill(null);
        polygon.setStroke(Color.BLACK);
        Line line=new Line(50,50,50,100);
        line.setStroke(Color.BLACK);
        Group root = new Group(polygon);
        root.getChildren().add(line);
        Text t=new Text(70,55,"print \"Test\"");
        t.setFill(Color.RED);
        root.getChildren().add(t);
        Polygon p2=new Polygon();
        setPolygon(p2,50,100);
        p2.setStroke(Color.BLACK);
        p2.setFill(null);
        root.getChildren().add(p2);
        Line l2=new Line(50,100,50,150);
        Text t2=new Text(70,105,"print \"end\"");
        t2.setFill(Color.RED);
        root.getChildren().add(t2);
        l2.setStroke(Color.BLACK);
        root.getChildren().add(l2);
        Polygon p3=new Polygon();
        setPolygon(p3,50,150);
        p3.setStroke(Color.BLACK);
        p3.setFill(null);
        root.getChildren().add(p3);
        Text t3=new Text(70,155,"force x");
        t3.setFill(Color.BLUE);
        root.getChildren().add(t3);
        Scene scene = new Scene(root, 1200,1200);
        stage.setTitle("Test");
        stage.setScene(scene);
        //scene.setFill(null);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
