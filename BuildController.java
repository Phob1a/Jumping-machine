import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class BuildController {


    String tag;
    private boolean pressed;
    private boolean selected=false;
    private boolean deletePressed;
    double fy=900.0;
    public int id;
    int startID=-1;
    Point st,ed;

    int stId,edId;


    ArrayList<Point> points=new ArrayList<>();

    String drawType;

    public ArrayList<JKTrace>JKTree=new ArrayList<>();


    @FXML
    private Polygon pgonBtn;

    @FXML
    private Ellipse ellipseBtn;

    @FXML
    private Polygon hgonBtn;

    @FXML
    private Button linkBtn;

    @FXML
    private Button valueBtn;

    @FXML
    private Pane root;

    @FXML
    private Pane canvas;

    @FXML
    private TextField inputField;

    @FXML
    private Button conBtn;

    @FXML
    private Button runBtn;

    @FXML
    private Pane runroot;


    @FXML
    private TextField tagInput=new TextField();


    @FXML
    private Button tagBtn=new Button("ok");

    public void setPgon(MouseEvent event){
        drawType="Pgon";
    }

    public void setHgon(MouseEvent event){
        drawType="Hgon";
    }

    public void setEllipse(MouseEvent event){
        drawType="Ellipse";
    }

    public void addDragFunction(Shape shape, Label l){
        final ObjectProperty<Point2D> mousePosition = new SimpleObjectProperty<>();

        shape.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mousePosition.set(new Point2D(event.getSceneX(), event.getSceneY()));
            }
        });

        shape.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                double deltaX = event.getSceneX() - mousePosition.get().getX();
                double deltaY = event.getSceneY() - mousePosition.get().getY();
                shape.setLayoutX(shape.getLayoutX() + deltaX);
                shape.setLayoutY(shape.getLayoutY() + deltaY);
                l.setLayoutX(l.getLayoutX() + deltaX);
                l.setLayoutY(l.getLayoutY() + deltaY);
                checkStart();
                int id=Integer.parseInt(shape.getId());
                for(Point p:JKTree.get(id).linkpoints){
                    for(Link link:p.links){
                        if(link.getTraceID1()==id){
                            link.getLine().setStartX(link.getLine().getStartX()+deltaX);
                            link.getLine().setStartY(link.getLine().getStartY()+deltaY);
                        }
                        else{
                            link.getLine().setEndX(link.getLine().getEndX()+deltaX);
                            link.getLine().setEndY(link.getLine().getEndY()+deltaY);

                        }
                    }
                    p.setX(p.getX()+deltaX);
                    p.setY(p.getY()+deltaY);
                }
                mousePosition.set(new Point2D(event.getSceneX(), event.getSceneY()));
            }
        });

        shape.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(!deletePressed) return;
                int id=Integer.parseInt(shape.getId());
                Label label=JKTree.get(id).getLabel();
                root.getChildren().remove(shape);
                root.getChildren().remove(label);
                //JKTree.remove(JKTree.get(id));
                JKTree.remove(id);
                checkStart();
                deletePressed=false;
            }
        });
    }

    public void deleteFrame(MouseEvent event){
        deletePressed=true;
    }




    public void getInput(MouseEvent event){

        String text=inputField.getText();
        tag=text;
        pressed=true;
        if(drawType=="Pgon"){
            drawPgon();
        }
        else if(drawType=="Hgon"){
            drawHgon();
        }
        else if(drawType=="Ellipse"){
            drawEllipse();
        }
    }

    public void drawPgon() {
            root.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (!pressed) return;
                    double x = event.getX();
                    double y = event.getY();
                    JKTrace trace=new JKTrace("Pgon",tag);
                    JKTree.add(trace);
                    int id=JKTree.size()-1;
                    trace.setId(id);
                    Polygon p = new Polygon();
                    p.getPoints().addAll(new Double[]{
                            x, y,
                            x + 10, y - 10,
                            x + 100, y - 10,
                            x + 100, y + 10,
                            x + 10, y + 10
                    });
                    p.setFill(Color.WHITE);
                    p.setStroke(Color.BLACK);
                    p.setId(Integer.toString(id));
                    Label l = new Label(tag);
                    l.setLayoutX(x + 15);
                    l.setLayoutY(y - 5);
                    trace.setShape(p);
                    trace.setLabel(l);
                    trace.linkpoints.add(new Point(x,y,id));
                    trace.linkpoints.add(new Point(x+55,y,id));
                    addDragFunction(p,l);
                    root.getChildren().add(p);
                    root.getChildren().add(l);
                    checkStart();
                    pressed = false;
                }
            });
    }

    public void drawHgon(){
        root.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (!pressed) return;
                double x = event.getX();
                double y = event.getY();
                JKTrace trace=new JKTrace("Hgon",tag);
                JKTree.add(trace);
                int id=JKTree.size()-1;
                trace.setId(id);
                Polygon p = new Polygon();
                p.getPoints().addAll(new Double[]{
                        x, y,
                        x + 10, y - 10,
                        x + 100, y - 10,
                        x + 110, y,
                        x + 100, y + 10,
                        x + 10, y + 10
                });
                p.setFill(Color.WHITE);
                p.setStroke(Color.BLACK);
                p.setId(Integer.toString(id));
                Label l = new Label(tag);
                l.setLayoutX(x + 15);
                l.setLayoutY(y - 5);
                trace.setShape(p);
                trace.setLabel(l);
                trace.linkpoints.add(new Point(x,y,id));
                trace.linkpoints.add(new Point(x+110,y,id));
                addDragFunction(p,l);
                root.getChildren().add(p);
                root.getChildren().add(l);
                checkStart();
                pressed = false;
            }
        });
    }

    public void drawEllipse(){
        root.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (!pressed) return;
                double x = event.getX();
                double y = event.getY();
                JKTrace trace=new JKTrace("Ellipse",tag);
                JKTree.add(trace);
                int id=JKTree.size()-1;
                trace.setId(id);
                Ellipse e=new Ellipse(x,y,50,10);
                e.setFill(Color.WHITE);
                e.setStroke(Color.BLACK);
                e.setId(Integer.toString(id));
                Label l = new Label(tag);
                l.setLayoutX(x + 15);
                l.setLayoutY(y - 5);
                trace.setShape(e);
                trace.setLabel(l);
                trace.linkpoints.add(new Point(x,y-10,id));
                trace.linkpoints.add(new Point(x,y+10,id));
                addDragFunction(e,l);

                root.getChildren().add(e);
                root.getChildren().add(l);
                checkStart();
                pressed = false;
            }
        });
    }


    public void drawLine(){
        st=new Point(0,0);
        ed=new Point(0,0);
        root.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (st.getX() != 0 && ed.getX() != 0) return;
                Point h = new Point(event.getX(), event.getY());
                for(int i=0;i<JKTree.size();i++){
                    for(Point p:JKTree.get(i).linkpoints){
                        if(calDis(h,p)<15.0){
                            if(st.getX()==0){
                                //st.setX(p.getX());
                                //st.setY(p.getY());
                                st=p;
                                stId=p.getTraceID();
                                break;
                            }
                            else{
                                //ed.setX(p.getX());
                                //ed.setY(p.getY());
                                edId=p.getTraceID();
                                ed=p;
                                Line l=new Line(st.getX(),st.getY(),ed.getX(),ed.getY());
                                l.setStroke(Color.BLACK);
                                root.getChildren().add(l);
                                Link link=new Link(l,st,ed);
                                root.getChildren().add(link.getTag());
                                st.links.add(link);
                                ed.links.add(link);
                                l.setOnMouseClicked(new EventHandler<MouseEvent>() {
                                    @Override
                                    public void handle(MouseEvent event) {
                                        l.setStroke(Color.RED);
                                        double x=event.getX();
                                        double y=event.getY();
                                        tagInput.setLayoutX(x+5);
                                        tagInput.setLayoutY(y+5);
                                        tagBtn.setLayoutX(x+5);
                                        tagBtn.setLayoutY(y+30);
                                        tagInput.setVisible(true);
                                        tagInput.setDisable(false);
                                        tagBtn.setVisible(true);
                                        tagBtn.setDisable(false);
                                        tagBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
                                            @Override
                                            public void handle(MouseEvent event) {

                                                double nx=(link.getP1().getX()+link.getP2().getX())*0.5;
                                                double ny=(link.getP1().getY()+link.getP2().getY())*0.5;
                                                link.getTag().setText(tagInput.getText());
                                                link.getTag().setLayoutX(nx);
                                                link.getTag().setLayoutY(ny);
                                                tagBtn.setVisible(false);
                                                tagBtn.setDisable(true);
                                                tagInput.setVisible(false);
                                                tagInput.setDisable(true);
                                                l.setStroke(Color.BLACK);
                                            }
                                        });
                                        Label tagLabel=new Label();

                                    }
                                });
                                return;
                            }
                        }
                    }
                }
            }

        });
    }

    public double calDis(Point a, Point b) {
        return Math.sqrt((a.getX()-b.getX())*(a.getX()-b.getX())+(a.getY()-b.getY())*(a.getY()-b.getY()));
    }

    public void runBtnPressed(MouseEvent event){
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(RunController.class.getResource("codeRunner.fxml"));
            //JumpingMachine jumpingMachine=new JumpingMachine(JKtree);
            Parent runRoot = loader.load();
            RunController controller = loader.getController();
            controller.JKTree=JKTree;
            controller.startID=startID;
            controller.jkID=startID;
            // controller.setTitle(title);
            // controller.setContext(content);
            Scene scene = new Scene(runRoot);
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void checkStart(){

        if(startID!=-1){
            JKTree.get(startID).getShape().setStroke(Color.BLACK);
            fy=900.0;
        }
      for(JKTrace trace:JKTree){
          if(trace.linkpoints.get(0).getY()<fy){
              fy=trace.linkpoints.get(0).getY();
              startID=trace.getId();
          }
      }
     JKTree.get(startID).getShape().setStroke(Color.BLUE);
    }

    public void addTagToLine(MouseEvent mouseEvent) {
        //link.setTag(new Label(tagInput.getText()));
    }
}
