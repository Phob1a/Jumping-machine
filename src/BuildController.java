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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;

public class BuildController {


    String tag;
    private boolean pressed;
    private boolean selected=false;
    private boolean deletePressed;
    double fy=900.0;
    public int id;
    int startID=-1;
    Point st=new Point(-1.0,-1.0),ed=new Point(-1.0,-1.0);

    int stId,edId;


    ArrayList<Point> points=new ArrayList<>();

    String drawType;

    public ArrayList<JKTrace>JKTree=new ArrayList<>();


    @FXML
    private Rectangle forceBtn;

    @FXML
    private Rectangle returnBtn;

    @FXML
    private Rectangle letbeBtn;

    @FXML
    private Rectangle pmBtn;

    @FXML
    private Rectangle pushBtn;

    @FXML
    private Rectangle popBtn;

    @FXML
    private Rectangle toBtn;

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
    private Rectangle runBtn;

    @FXML
    private Pane runroot;

    @FXML
    private TextField tagInput;





    public void forcePressed(){
        presetTrace("Pgon","force x");
    }

    public void returnPressed(){
        presetTrace("Pgon","return x");
    }

    public void letbePressed(){
        presetTrace("Pgon","let x be");
    }

    public void pushPressed(){
        presetTrace("Pgon","#tag'");
    }

    public void popPressed(){
        presetTrace("Pgon","λ");
    }

    public void pmPressed(){
        presetTrace("Pgon","pm x as");
    }

    public void toPressed(){
        presetTrace("Hgon","to");
    }

    public void printPressed(){
        presetTrace("Pgon","print \"\"");
    }

    public void presetTrace(String type,String text){
        drawType=type;
        inputField.setText(text);
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
                        link.locateBindings();
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
                if(event.getButton()== MouseButton.SECONDARY);
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


    public void addTrace(KeyEvent event){
        if(event.getCode()== KeyCode.ENTER){
            if(!inputField.getText().isEmpty())
                drawTrace();
        }
    }

    public void drawLine(){
        Point a=new Point(-1.0,0);
        Point b=new Point(-1.0,0);
        for(JKTrace t:JKTree){
            for(Point p:t.linkpoints){
                if(calDis(st,p)<20.0){
                    a=p;
                }
                if(calDis(ed,p)<20.0){
                    b=p;
                }
            }
        }
        if(a.getX()==-1.0||b.getX()==-1.0) return;
        Line line=new Line(a.getX(),a.getY(),b.getX(),b.getY());
        //line.setStroke(Color.TRANSPARENT);
        Link link=new Link(line,a,b);
        addTagFunction(link);
        root.getChildren().add(line);
        a.links.add(link);
        b.links.add(link);
        st.setX(-1.0);
        st.setY(-1.0);
        ed.setX(-1.0);
        ed.setY(-1.0);
    }

    public void drawTrace(){
        String text=inputField.getText();
        int id=JKTree.size();
        JKTrace trace=new JKTrace(drawType,text);
        JKTree.add(trace);
        trace.setId(id);
        Label l = new Label(text);
        double x=0.0,y=10.0;
        trace.linkpoints.add(new Point(x,y,id));
        l.setLayoutX(x + 15);
        l.setLayoutY(y - 5);
        trace.setLabel(l);
        if(drawType.equals("Pgon")){
            trace.addPgon();
        }
        else{
            Polygon p=new Polygon();
            p.getPoints().addAll(new Double[]{
                    x, y,
                    x + 10, y - 10,
                    x + 70, y - 10,
                    x+80,y,
                    x + 70, y + 10,
                    x + 10, y + 10
            });
            trace.setShape(p);
        }
        Shape p=trace.getShape();
        p.setFill(Color.WHITE);
        p.setStroke(Color.BLACK);
        p.setId(Integer.toString(id));

        if(trace.getType().equals("Pgon")){
            //trace.linkpoints.add(new Point(x+60,y,id));
        }
        else{
            trace.linkpoints.add(new Point(x+80,y,id));
        }
        addDragFunction(p,l);
        root.getChildren().add(p);
        root.getChildren().add(l);
        inputField.clear();
        p.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getButton().equals(MouseButton.SECONDARY)) {
                int id = Integer.parseInt(p.getId());
                JKTrace trace = JKTree.get(id);
                double x = p.getLayoutX();
                double y = p.getLayoutY();
                tagInput=new TextField();
                tagInput.setLayoutX(x);
                tagInput.setLayoutY(y);
                p.setStroke(Color.RED);
                root.getChildren().add(tagInput);

                tagInput.setOnKeyPressed(new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent event) {
                        if(event.getCode().equals(KeyCode.ENTER)){
                            String text=tagInput.getText();
                            trace.setText(text);
                            trace.getLabel().setText(text);
                            p.setStroke(Color.BLACK);
                            checkStart();
                            tagInput.clear();
                            root.getChildren().remove(tagInput);
                        }
                    }
                });
            }

             }
        });
    }


    public void addTagFunction(Link link){
        Line line=link.getLine();
        line.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                line.setStroke(Color.RED);
                double x=(line.getStartX()+line.getEndX())*0.5;
                double y=(line.getStartY()+line.getEndY())*0.5;
                tagInput=new TextField();
                tagInput.setLayoutX(x + 5);
                tagInput.setLayoutY(y + 5);
                root.getChildren().add(tagInput);

                tagInput.setOnKeyPressed(new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent event) {
                        if(event.getCode()==KeyCode.ENTER){
                        String text=tagInput.getText();
                        if(text.isEmpty()){

                        }
                        else if(text.charAt(0)=='#') {
                            if(link.getTag().getText().isEmpty()) {
                                double fx=link.getP1().getX()+(link.getP2().getX()-link.getP1().getX())*0.15;
                                double fy=link.getP1().getY()+(link.getP2().getY()-link.getP1().getY())*0.15;
                                link.setTag(new Label(text));
                                link.getTag().setLayoutX(fx);
                                link.getTag().setLayoutY(fy);
                                root.getChildren().add(link.getTag());
                            }
                            else{
                                link.getTag().setText(text);
                            }
                          }
                        else{

                            Label label=new Label(text);
                            Ellipse e=new Ellipse(0,0,50,10);
                            e.setFill(Color.WHITE);
                            e.setStroke(Color.BLACK);
                            Binding binding=new Binding(e,label);
                            if(link.bindings.size()<2) {
                                link.bindings.add(binding);
                                link.locateBindings();
                                root.getChildren().add(e);
                                root.getChildren().add(label);
                            }
                        }

                            tagInput.clear();
                            root.getChildren().remove(tagInput);
                            line.setStroke(Color.BLACK);
                        }
                    }
                });
            }
        });
    }


//    public void addEllipse(Link link,String text){
//        Line line=link.getLine();
//        int bindID=link.bindings.size();
//        double x=line.getStartX()+(line.getEndX()-line.getStartX())*1.0*bindID/3;
//        double y=line.getStartY()+(line.getEndY()-line.getStartY())*1.0*bindID/3;
//        Ellipse e=new Ellipse(x,y,50.0,10.0);
//        e.setFill(Color.WHITE);
//        e.setStroke(Color.BLACK);
//
//    }


//    public void getInput(MouseEvent event){
//
//        String text=inputField.getText();
//        tag=text;
//        pressed=true;
//        if(drawType=="Pgon"){
//            drawPgon();
//        }
//        else if(drawType=="Hgon"){
//            drawHgon();
//        }
//    }

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
                    trace.linkpoints.add(new Point(x,y,id));
                    trace.addPgon();
                    Shape p=trace.getShape();
//                    Polygon p = new Polygon();
//                    p.getPoints().addAll(new Double[]{
//                            x, y,
//                            x + 10, y - 10,
//                            x + 100, y - 10,
//                            x + 100, y + 10,
//                            x + 10, y + 10
//                    });
                    p.setFill(Color.WHITE);
                    p.setStroke(Color.BLACK);
                    p.setId(Integer.toString(id));
                    Label l = new Label(tag);
                    l.setLayoutX(x + 10);
                    l.setLayoutY(y - 5);
                    trace.setShape(p);
                    trace.setLabel(l);
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

//    public void drawEllipse(){
//        root.setOnMouseClicked(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//                if (!pressed) return;
//                double x = event.getX();
//                double y = event.getY();
//                JKTrace trace=new JKTrace("Ellipse",tag);
//                JKTree.add(trace);
//                int id=JKTree.size()-1;
//                trace.setId(id);
//                Ellipse e=new Ellipse(x,y,50,10);
//                e.setFill(Color.WHITE);
//                e.setStroke(Color.BLACK);
//                e.setId(Integer.toString(id));
//                Label l = new Label(tag);
//                l.setLayoutX(x + 15);
//                l.setLayoutY(y - 5);
//                trace.setShape(e);
//                trace.setLabel(l);
//                trace.linkpoints.add(new Point(x,y-10,id));
//                trace.linkpoints.add(new Point(x,y+10,id));
//                addDragFunction(e,l);
//
//                root.getChildren().add(e);
//                root.getChildren().add(l);
//                checkStart();
//                pressed = false;
//            }
//        });
//    }


//    public void drawLine(){
//        st=new Point(0,0);
//        ed=new Point(0,0);
//        root.setOnMouseClicked(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//                if (st.getX() != 0 && ed.getX() != 0) return;
//                Point h = new Point(event.getX(), event.getY());
//                for(int i=0;i<JKTree.size();i++){
//                    for(Point p:JKTree.get(i).linkpoints){
//                        if(calDis(h,p)<15.0){
//                            if(st.getX()==0){
//                                //st.setX(p.getX());
//                                //st.setY(p.getY());
//                                st=p;
//                                stId=p.getTraceID();
//                                break;
//                            }
//                            else{
//                                //ed.setX(p.getX());
//                                //ed.setY(p.getY());
//                                edId=p.getTraceID();
//                                ed=p;
//                                Line l=new Line(st.getX(),st.getY(),ed.getX(),ed.getY());
//                                l.setStroke(Color.BLACK);
//                                root.getChildren().add(l);
//                                Link link=new Link(l,st,ed);
//                                root.getChildren().add(link.getTag());
//                                st.links.add(link);
//                                ed.links.add(link);
//                                l.setOnMouseClicked(new EventHandler<MouseEvent>() {
//                                    @Override
//                                    public void handle(MouseEvent event) {
//                                        if(event.getClickCount()==2) {
//                                            l.setStroke(Color.RED);
//                                            double x = event.getX();
//                                            double y = event.getY();
//                                            tagInput.setLayoutX(x + 5);
//                                            tagInput.setLayoutY(y + 5);
//                                            tagBtn.setLayoutX(x + 5);
//                                            tagBtn.setLayoutY(y + 30);
//                                            tagInput.setVisible(true);
//                                            tagInput.setDisable(false);
//                                            tagBtn.setVisible(true);
//                                            tagBtn.setDisable(false);
//                                            tagBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
//                                                @Override
//                                                public void handle(MouseEvent event) {
//
//                                                    double nx = (link.getP1().getX() + link.getP2().getX()) * 0.5;
//                                                    double ny = (link.getP1().getY() + link.getP2().getY()) * 0.5;
//                                                    link.getTag().setText(tagInput.getText());
//                                                    link.getTag().setLayoutX(nx);
//                                                    link.getTag().setLayoutY(ny);
//                                                    tagBtn.setVisible(false);
//                                                    tagBtn.setDisable(true);
//                                                    tagInput.setVisible(false);
//                                                    tagInput.setDisable(true);
//                                                    tagInput.setText(null);
//                                                    l.setStroke(Color.BLACK);
//                                                }
//                                            });
//                                        }
//                                        Label tagLabel=new Label();
//
//                                    }
//                                });
//                                return;
//                            }
//                        }
//                    }
//                }
//            }
//
//        });
//    }

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
            resetJKTree();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void resetJKTree() {
        for(JKTrace trace:JKTree){
            trace.getLabel().setTextFill(Color.BLACK);
            trace.getShape().setStroke(Color.BLACK);
            trace.getShape().setFill(Color.WHITE);
            checkStart();
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

    public void setLinePoint(MouseEvent event) {
        if(event.getButton()==MouseButton.PRIMARY&&event.getClickCount()==2){
            if(st.getX()==-1.0&&ed.getX()==-1.0){
                st.setX(event.getX());
                st.setY(event.getY());
            }
            else if(ed.getX()==-1.0){
                ed.setX(event.getX());
                ed.setY(event.getY());
                drawLine();
            }
            else{
                st.setX(event.getX());
                st.setY(event.getY());
                ed.setX(-1.0);
                ed.setY(-1.0);
            }
        }
    }

    public void saveToFile(MouseEvent event){

        FileMsgBoxController.display("Please input the file path: ");

        String filename="test1";
        File file=new File(filename);
        if(!file.exists()){
            try{
                file.createNewFile();
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
//        tagInput=new TextField();
//        //tagInput.setText("Please input the file name:");
//        root.getChildren().add(tagInput);
//        tagInput.setOnKeyPressed(new EventHandler<KeyEvent>() {
//            @Override
//            public void handle(KeyEvent event) {
//                if(event.getCode().equals(KeyCode.ENTER)){
//                    if(!tagInput.getText().isEmpty()){
//
//
//                    }
//                }
//            }
//        });
        try{
            BufferedWriter bw=new BufferedWriter(new FileWriter(file));
            bw.write(JKTree.size()+"\n");
            for(JKTrace trace:JKTree){
                bw.write(trace.getText()+"\n"+trace.linkpoints.get(0).getX()+" "+trace.linkpoints.get(0).getY()+"\n");

            }
            for(JKTrace trace:JKTree){
                int id=trace.getId();
                for(int t=0;t<2;t++) {
                    for (Link link : trace.linkpoints.get(t).links) {
                        if ((link.getTraceID1() == id && link.getTraceID2() > id) || (link.getTraceID2() == id && link.getTraceID1() > id)) {
                            bw.write(link.getTraceID1() + " " + link.getTraceID2() + " " + link.getP1().getX() + " " + link.getP1().getY() + " " + link.getP2().getX() + " " + link.getP2().getY() + "\n");
                            if (link.getTag().getText().isEmpty()) {
                                bw.write("0\n");

                            } else {
                                bw.write("1\n");
                                bw.write(link.getTag().getText() + "\n");
                            }
                            bw.write(Integer.toString(link.bindings.size()));
                            bw.write("\n");
                            for (Binding b : link.bindings) {
                                bw.write(b.getBindKey().getText() + "\n");
                            }
                        }
                    }
                }
            }
            bw.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void readFromFile(MouseEvent event){

        JKTree.clear();
        String filename="test1";
        File file=new File(filename);

        try{
            BufferedReader br=new BufferedReader(new FileReader(file));
            int n=Integer.parseInt(br.readLine());
            for(int i=0;i<n;i++) {
                String text= br.readLine();
                int id = i;
                String[] rdLine=br.readLine().split(" ");
                double x = Double.parseDouble(rdLine[0]);
                double y = Double.parseDouble(rdLine[1]);
                String type = text.equals("to") ? "Hgon" : "Pgon";
                JKTrace trace = new JKTrace(type, text);
                trace.setId(id);
                JKTree.add(trace);
                Label l = new Label(text);
                trace.linkpoints.add(new Point(x, y, id));
                l.setLayoutX(x + 15);
                l.setLayoutY(y - 5);
                trace.setLabel(l);
                if (!type.equals("Hgon")) {
                    trace.addPgon();
                } else {
                    Polygon p = new Polygon();
                    p.getPoints().addAll(new Double[]{
                            x, y,
                            x + 10, y - 10,
                            x + 70, y - 10,
                            x + 80, y,
                            x + 70, y + 10,
                            x + 10, y + 10
                    });
                    trace.setShape(p);
                }
                Shape p = trace.getShape();
                p.setFill(Color.WHITE);
                p.setStroke(Color.BLACK);
                p.setId(Integer.toString(id));

                if (trace.getType().equals("Pgon")) {
                    //trace.linkpoints.add(new Point(x+60,y,id));
                } else {
                    trace.linkpoints.add(new Point(x + 80, y, id));
                }
                addDragFunction(p, l);
                root.getChildren().add(p);
                root.getChildren().add(l);
                inputField.clear();
                p.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        if (event.getButton().equals(MouseButton.SECONDARY)) {
                            int id = Integer.parseInt(p.getId());
                            JKTrace trace = JKTree.get(id);
                            double x = p.getLayoutX();
                            double y = p.getLayoutY();
                            tagInput = new TextField();
                            tagInput.setLayoutX(x);
                            tagInput.setLayoutY(y);
                            p.setStroke(Color.RED);
                            root.getChildren().add(tagInput);

                            tagInput.setOnKeyPressed(new EventHandler<KeyEvent>() {
                                @Override
                                public void handle(KeyEvent event) {
                                    if (event.getCode().equals(KeyCode.ENTER)) {
                                        String text = tagInput.getText();
                                        trace.setText(text);
                                        trace.getLabel().setText(text);
                                        p.setStroke(Color.BLACK);
                                        checkStart();
                                        tagInput.clear();
                                        root.getChildren().remove(tagInput);
                                    }
                                }
                            });
                        }

                    }
                });
            }
                String readString;
                String[] rdLine;
                while((readString=br.readLine())!=null){
                    rdLine=readString.split(" ");
                    int id1,id2;
                    double x1,x2,y1,y2;
                    id1=Integer.parseInt(rdLine[0]);
                    id2=Integer.parseInt(rdLine[1]);
                    Point p1=new Point();
                    Point p2=new Point();
                    x1=Double.parseDouble(rdLine[2]);
                    y1=Double.parseDouble(rdLine[3]);
                    x2=Double.parseDouble(rdLine[4]);
                    y2=Double.parseDouble(rdLine[5]);

                    for(Point point:JKTree.get(id1).linkpoints){
                        if(point.getX()==x1&&point.getY()==y1){
                            p1=point;
                            break;
                        }
                    }
                    for(Point point:JKTree.get(id2).linkpoints){
                        if(point.getX()==x2&&point.getY()==y2){
                            p2=point;
                            break;
                        }
                    }
                    Line line=new Line(p1.getX(),p1.getY(),p2.getX(),p2.getY());
                    line.setStroke(Color.BLACK);
                    Link link=new Link(line,p1,p2);
                    p1.links.add(link);
                    p2.links.add(link);
                    root.getChildren().add(line);
                    addTagFunction(link);
                    int tagNo=Integer.parseInt(br.readLine());
                    if(tagNo==1){
                        link.setTag(new Label());
                        link.getTag().setText(br.readLine());
                        double fx=link.getP1().getX()+(link.getP2().getX()-link.getP1().getX())*0.15;
                        double fy=link.getP1().getY()+(link.getP2().getY()-link.getP1().getY())*0.15;
                        link.getTag().setLayoutX(fx);
                        link.getTag().setLayoutY(fy);
                        root.getChildren().add(link.getTag());
                    }
                    int bdNo=Integer.parseInt(br.readLine());
                    for(int i=0;i<bdNo;i++){
                        String bd=br.readLine();
                        Label label=new Label(bd);
                        Ellipse e=new Ellipse(0,0,50,10);
                        e.setFill(Color.WHITE);
                        e.setStroke(Color.BLACK);
                        Binding binding=new Binding(e,label);
                        link.bindings.add(binding);
                        link.locateBindings();
                        root.getChildren().add(e);
                        root.getChildren().add(label);
                    }
                    link.locateBindings();

                }
                checkStart();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
