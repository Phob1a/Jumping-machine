package GUI;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.InputEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;

import java.util.ArrayList;

import static GUI.Point.calDis;


public class UIController {

String tag;
private boolean pressed;
private boolean selected=false;
double fx;
double fy;
public int id;
Point st,ed;
ArrayList<Point> points=new ArrayList<>();

String drawType;

ArrayList<Trace>tree;

//class Trace{
//    private String text;
//    private Trace father;
//    ArrayList<Trace>son;
//    private double[] linkpoint;
//
//    public Trace(double []p,String text){
//        this.linkpoint=p;
//        this.text=text;
//        this.son=new ArrayList<>();
//    }
//}

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

    public void drawEllipse() {
        root.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (!pressed) return;
                double x = event.getX();
                double y = event.getY();
                Ellipse e = new Ellipse(x,y,50,10);
                points.add(new Point(x, y-10));
                points.add(new Point(x, y+10));
                e.setFill(Color.WHITE);
                e.setStroke(Color.BLACK);
                Label l = new Label(tag);
                l.setLayoutX(x -25);
                l.setLayoutY(y - 5);
                final ObjectProperty<Point2D> mousePosition = new SimpleObjectProperty<>();

                e.setOnMousePressed(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        mousePosition.set(new Point2D(event.getSceneX(), event.getSceneY()));
                    }
                });

                e.setOnMouseDragged(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        double deltaX = event.getSceneX() - mousePosition.get().getX();
                        double deltaY = event.getSceneY() - mousePosition.get().getY();
                        e.setLayoutX(e.getLayoutX() + deltaX);
                        e.setLayoutY(e.getLayoutY() + deltaY);
                        l.setLayoutX(l.getLayoutX() + deltaX);
                        l.setLayoutY(l.getLayoutY() + deltaY);
                        mousePosition.set(new Point2D(event.getSceneX(), event.getSceneY()));
                    }
                });
                root.getChildren().add(e);
                root.getChildren().add(l);
                pressed = false;
            }
        });
    }

    public void drawHgon() {
        root.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (!pressed) return;
                double x = event.getX();
                double y = event.getY();
                Polygon p = new Polygon();
                p.getPoints().addAll(new Double[]{
                        x, y,
                        x + 10, y - 10,
                        x + 100, y - 10,
                        x + 110, y,
                        x + 100, y + 10,
                        x + 10, y + 10
                });
                points.add(new Point(x, y));
                points.add(new Point(x + 110, y));
                p.setFill(Color.WHITE);
                p.setStroke(Color.BLACK);
                Label l = new Label(tag);
                l.setLayoutX(x + 15);
                l.setLayoutY(y - 5);
                final ObjectProperty<Point2D> mousePosition = new SimpleObjectProperty<>();

                p.setOnMousePressed(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        mousePosition.set(new Point2D(event.getSceneX(), event.getSceneY()));
                    }
                });

                p.setOnMouseDragged(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        double deltaX = event.getSceneX() - mousePosition.get().getX();
                        double deltaY = event.getSceneY() - mousePosition.get().getY();
                        p.setLayoutX(p.getLayoutX() + deltaX);
                        p.setLayoutY(p.getLayoutY() + deltaY);
                        l.setLayoutX(l.getLayoutX() + deltaX);
                        l.setLayoutY(l.getLayoutY() + deltaY);
                        mousePosition.set(new Point2D(event.getSceneX(), event.getSceneY()));
                    }
                });
                root.getChildren().add(p);
                root.getChildren().add(l);
                pressed = false;
            }
        });
    }



    public void drawLine(MouseEvent mouseEvent) {
      st=new Point(0,0);
      ed=new Point(0,0);
      root.setOnMouseClicked(new EventHandler<MouseEvent>() {
          @Override
          public void handle(MouseEvent event) {
              if(st.getX()!=0&&ed.getX()!=0) return;
              Point h=new Point(event.getX(),event.getY());
              for(Point x:points){
                  if(calDis(x,h)<100.0){
                      if(st.getX()==0.0){
                          st.setX(x.getX());
                          st.setY(x.getY());
                          break;
                      }
                      else{
                          ed.setX(x.getX());
                          ed.setY(x.getY());
                          Line l=new Line(st.getX(),st.getY(),ed.getX(),ed.getY());
                          l.setStroke(Color.BLACK);
                          root.getChildren().add(l);
                          return;
                      }
                  }
              }
          }
      });
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
      //drawPic(text);
  }

  public void setPgon(MouseEvent event){
        drawType="Pgon";
  }

  public void setHgon(MouseEvent event){
        drawType="Hgon";
  }

  public void setEllipse(MouseEvent event){
        drawType="Ellipse";
  }
  public void drawPgon(){
      root.setOnMouseClicked(new EventHandler<MouseEvent>() {
          @Override
          public void handle(MouseEvent event) {
              if(!pressed) return;
              double x = event.getX();
              double y = event.getY();
              Polygon p = new Polygon();
              p.getPoints().addAll(new Double[]{
                      x, y,
                      x + 10, y - 10,
                      x + 110, y - 10,
                      x + 110, y + 10,
                      x + 10, y + 10
              });
              points.add(new Point(x, y));
              p.setFill(Color.WHITE);
              p.setStroke(Color.BLACK);
              Label l = new Label(tag);
              l.setLayoutX(x + 15);
              l.setLayoutY(y - 5);
              final ObjectProperty<Point2D> mousePosition = new SimpleObjectProperty<>();

              p.setOnMousePressed(new EventHandler<MouseEvent>() {
                  @Override
                  public void handle(MouseEvent event) {
                      mousePosition.set(new Point2D(event.getSceneX(), event.getSceneY()));
                  }
              });

              p.setOnMouseDragged(new EventHandler<MouseEvent>() {
                  @Override
                  public void handle(MouseEvent event) {
                      double deltaX = event.getSceneX() - mousePosition.get().getX();
                      double deltaY = event.getSceneY() - mousePosition.get().getY();
                      p.setLayoutX(p.getLayoutX() + deltaX);
                      p.setLayoutY(p.getLayoutY() + deltaY);
                      l.setLayoutX(l.getLayoutX() + deltaX);
                      l.setLayoutY(l.getLayoutY() + deltaY);
                      mousePosition.set(new Point2D(event.getSceneX(), event.getSceneY()));
                  }
              });
              root.getChildren().add(p);
              root.getChildren().add(l);
              pressed = false;
          }
      });

}

          public void drawFrame() {
              root.setOnMouseClicked(new EventHandler<MouseEvent>() {
                  @Override
                  public void handle(MouseEvent event) {
                      if (!pressed) return;
                      double x = event.getX();
                      double y = event.getY();
                      Polygon p = new Polygon();
                      p.getPoints().addAll(new Double[]{
                              x, y,
                              x + 10, y - 10,
                              x + 110, y - 10,
                              x + 110, y + 10,
                              x + 10, y + 10
                      });
                      points.add(new Point(x, y));
                      p.setFill(Color.WHITE);
                      p.setStroke(Color.BLACK);
                      p.setOnMouseClicked(new EventHandler<MouseEvent>() {
                          @Override
                          public void handle(MouseEvent event) {
                              if (!selected) {
                                  selected = true;
                                  fx = p.getLayoutX();
                                  fy = p.getLayoutY();
                              } else {
                                  selected = false;
                                  Line l = new Line(fx, fy, p.getLayoutX(), p.getLayoutY());
                                  l.setStroke(Color.BLACK);
                                  root.getChildren().add(l);
                              }
                          }
                      });
                      Label l = new Label(tag);
                      l.setLayoutX(x + 15);
                      l.setLayoutY(y - 5);
                      final ObjectProperty<Point2D> mousePosition = new SimpleObjectProperty<>();

                      p.setOnMousePressed(new EventHandler<MouseEvent>() {
                          @Override
                          public void handle(MouseEvent event) {
                              mousePosition.set(new Point2D(event.getSceneX(), event.getSceneY()));
                          }
                      });

                      p.setOnMouseDragged(new EventHandler<MouseEvent>() {
                          @Override
                          public void handle(MouseEvent event) {
                              double deltaX = event.getSceneX() - mousePosition.get().getX();
                              double deltaY = event.getSceneY() - mousePosition.get().getY();
                              p.setLayoutX(p.getLayoutX() + deltaX);
                              p.setLayoutY(p.getLayoutY() + deltaY);
                              l.setLayoutX(l.getLayoutX() + deltaX);
                              l.setLayoutY(l.getLayoutY() + deltaY);
                              mousePosition.set(new Point2D(event.getSceneX(), event.getSceneY()));
                          }
                      });
                      root.getChildren().add(p);
                      root.getChildren().add(l);
                      pressed = false;
                  }
              });

          }

}

