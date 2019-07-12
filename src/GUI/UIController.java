package GUI;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.InputEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;

import java.util.ArrayList;


public class UIController {

String tag;
private boolean pressed;
private boolean selected;
double fx;
double fy;

ArrayList<Trace>tree;

class Trace{
    private String text;
    private Trace father;
    ArrayList<Trace>son;
    private double[] linkpoint;

    public Trace(double []p,String text){
        this.linkpoint=p;
        this.text=text;
        this.son=new ArrayList<>();
    }
}

  @FXML
    private Button printBtn;

  @FXML
    private Button forceBtn;

  @FXML
    private Button returnBtn;

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


  class Graph{
        int id;
        Polygon p;
        Label l;
  }


  public void addPrintTrace(MouseEvent event){
      inputField.setVisible(true);
      conBtn.setVisible(true);

//      Polygon p=new Polygon();
//      p.getPoints().addAll(new Double[]{
//              330.0,130.0,
//              340.0,120.0,
//              400.0,120.0,
//              400.0,140.0,
//              340.0,140.0});
//      p.setFill(null);
//      p.setStroke(Color.BLACK);
//      root.getChildren().add(p);
  }

  public void drawShape(String context){

  }

  public void getInput(MouseEvent event){
      String text=inputField.getText();
      tag=text;
      pressed=true;
      drawFrame();
      //drawPic(text);
  }


  public void drawFrame(){
      root.setOnMouseClicked(new EventHandler<MouseEvent>() {
          @Override
          public void handle(MouseEvent event) {
              if(!pressed) return;
              double x = event.getX();
              double y = event.getY();
              Polygon p=new Polygon();
              p.getPoints().addAll(new Double[]{
                      x, y,
                      x + 10, y - 10,
                      x + 110, y - 10,
                      x + 110, y + 10,
                      x + 10, y + 10
              });

              p.setFill(null);
              p.setStroke(Color.BLACK);
              p.setOnMouseClicked(new EventHandler<MouseEvent>() {
                  @Override
                  public void handle(MouseEvent event) {
                      if(!selected){
                          selected=true;
                          fx=p.getLayoutX();
                          fy=p.getLayoutY();
                      }
                      else{
                          selected=false;
                          Line l=new Line(fx,fy,p.getLayoutX(),p.getLayoutY());
                          l.setStroke(Color.BLACK);
                          root.getChildren().add(l);
                      }
                  }
              });
              Label l=new Label(tag);
              l.setLayoutX(x+15);
              l.setLayoutY(y-5);
              root.getChildren().add(p);
              root.getChildren().add(l);
              pressed=false;
          }
      });

  }
}


