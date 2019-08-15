import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class RunController {

    ArrayList<JKTrace>JKTree;
    int startID;
    int cycle;
    int step;
    int jkID;
    int oldJumpID;
    int jumpID;
    int pressedID=-1;
    String value;
    Point point=new Point(50.0,50.0);



    ArrayList<JumpTrace> jumpTree=new ArrayList<>();
    HashMap<String,Integer> jumpingPoint=new HashMap<>();
    HashMap<String,Integer> hgonTable=new HashMap<>();
    Stack<String> stack=new Stack<>();

    @FXML
    private Pane runRoot;

    @FXML
    private Label showStep;

    @FXML
    private Label showStack;

    @FXML
    private Button nextBtn;




    @FXML
    private ListView<String>bindView=new ListView<>();

    public void fetch(){
        showCycle("fetch");
        addTrace();
        step=1;
    }


    public void decode(){
        showCycle("decode");
        String str=jumpTree.get(jumpID).getText();
        for(String s:str.split(" ")){
            if(s.equals("pt")){
                int pid=jumpingPoint.size();
                String npt="pt"+Integer.toString(pid);
                jumpingPoint.put(npt,jumpID);
                str=str.replaceAll("pt",npt);
                value=npt;
                jumpTree.get(jumpID).setText(str);
                jumpTree.get(jumpID).getLabel().setText(str);
            }
        }
        step=2;

    }

    public void execute() {
        showCycle("execute");

        String code = jumpTree.get(jumpID).getText();

        if (code.equals("to")) {
            hgonTable.put("hgon"+jumpID,jumpID);
            stack.push("hgon"+jumpID);
            updateStackLabel();
            Point endPoint=new Point(point.getX(),point.getY()+30);
            Point startPoint=jumpTree.get(jumpID).linkpoints.get(0);
            Link jumpLink=buildLink(startPoint,endPoint);
            Link oldLink=findNextLink(0);
            addTagAndBindings(oldLink,jumpLink);
            int newJkID=findNextJkTrace(oldLink);
            JKTree.get(newJkID).setFatherid(jkID);
            jkID=newJkID;
        }
        else if (code.charAt(code.length() - 1) == '\'') {

            String value=code.substring(0,code.length()-1);
            stack.push(value);
            updateStackLabel();
            Point endPoint=new Point(point.getX(),point.getY()+50);
            Point startPoint=jumpTree.get(jumpID).linkpoints.get(0);
            Link jumpLink=buildLink(startPoint,endPoint);
            Link oldLink=findNextLink(0);
            addTagAndBindings(oldLink,jumpLink);
            int newJkID=findNextJkTrace(oldLink);
            JKTree.get(newJkID).setFatherid(jkID);
            jkID=newJkID;
        }
        else if (code.substring(0,1).equals("λ")) {

            value=stack.pop();
            updateStackLabel();
            Point endPoint=new Point(point.getX(),point.getY()+50);
            Point startPoint=jumpTree.get(jumpID).linkpoints.get(0);
            Link jumpLink=buildLink(startPoint,endPoint);
            Link oldLink=findNextLink(0);
            addTagAndBindings(oldLink, jumpLink);
            setBinding(jumpLink);
            int newJkID=findNextJkTrace(oldLink);
            JKTree.get(newJkID).setFatherid(jkID);
            jkID=newJkID;
        }
        else if (code.substring(0, 5).equals("print")) {

            Point endPoint=new Point(point.getX(),point.getY()+50);
            Point startPoint=jumpTree.get(jumpID).linkpoints.get(0);
            Link jumpLink=buildLink(startPoint,endPoint);
            Link oldLink=findNextLink(0);
            int newJkID=findNextJkTrace(oldLink);
            addTagAndBindings(oldLink,jumpLink);
            JKTree.get(newJkID).setFatherid(jkID);
            jkID=newJkID;
        }
        else if(code.substring(0,2).equals("pm")){
            value=code.split(" ")[1];
            value=jumpTree.get(jumpID).bindTable.get(value);
            Point endPoint=new Point(point.getX(),point.getY()+50);
            Point startPoint=jumpTree.get(jumpID).linkpoints.get(0);
            Link jumpLink=buildLink(startPoint,endPoint);
            patternMatch(jumpLink);

        }
        else if (code.substring(0, 3).equals("let")) {
            value=code.split(" ")[1];
            Point endPoint=new Point(point.getX(),point.getY()+50);
            Point startPoint=jumpTree.get(jumpID).linkpoints.get(0);
            Link jumpLink=buildLink(startPoint,endPoint);
            Link oldLink=findNextLink(0);
            addTagAndBindings(oldLink,jumpLink);
            setBinding(jumpLink);
            int newJkID=findNextJkTrace(oldLink);
            JKTree.get(newJkID).setFatherid(jkID);
            jkID=newJkID;
            if(JKTree.get(jkID).getType().equals("Ellipse")){
               // binding();
            }
        }
        else if (code.substring(0, 5).equals("force")) {
            String key=code.split(" ")[1];
            if(jumpTree.get(jumpID).bindTable.containsKey(key)){
                value=jumpTree.get(jumpID).bindTable.get(key);
                String x=value.substring(0,2);
                if(x.equals("pt")){
                    JKTree.get(jkID).getShape().setStroke(Color.BLACK);
                    jumpTree.get(jumpID).getShape().setStroke(Color.BLACK);
                    int newid=jumpingPoint.get(value);
                    jkID=jumpTree.get(newid).getTeacherID();
                    Point startPoint=jumpTree.get(newid).linkpoints.get(1);
                    jumpID=newid;
                    Point endPoint=new Point(startPoint.getX()+80,startPoint.getY()+50);
                    Link jumpLink=buildLink(startPoint,endPoint);
                    Link oldLink=findNextLink(1);
                    addTagAndBindings(oldLink,jumpLink);
                    int newJkID=findNextJkTrace(oldLink);
                    JKTree.get(newJkID).setFatherid(jkID);
                    jkID=newJkID;
                }
            }
        }
        else if (code.substring(0, 6).equals("return")) {
            value=code.split(" ")[1];
            calReturnValue();
            if(!stack.empty()){
                String hgon=stack.pop();
                int newid=hgonTable.get(hgon);
                updateStackLabel();
                JKTree.get(jkID).getShape().setStroke(Color.BLACK);
                jumpTree.get(jumpID).getShape().setStroke(Color.BLACK);
                jumpID=newid;
                jkID=jumpTree.get(newid).getTeacherID();
                Point startPoint=jumpTree.get(newid).linkpoints.get(1);
                Point endPoint=new Point(startPoint.getX()+30,startPoint.getY()+50);
                Link jumpLink=buildLink(startPoint,endPoint);
                Link oldLink=findNextLink(1);
                addTagAndBindings(oldLink,jumpLink);
                setBinding(jumpLink);
                int newJkID=findNextJkTrace(oldLink);
                JKTree.get(newJkID).setFatherid(jkID);
                jkID=newJkID;
            }
            else{
//                if(jumpTree.get(jumpID).bindTable.containsKey(value)) {
//                    value = jumpTree.get(jumpID).bindTable.get(value);
//                }
                MsgBoxController.display(value);
                nextBtn.setDisable(true);
            }

        }

        step=0;
        cycle++;
    }

//    private int findNextJkTrace() {
//        if(!branchExist()){
//            return findNextJkTrace(0);
//        }
//        Point p=JKTree.get(jkID).linkpoints.get(0);
//            for(Link link:p.links){
//                if(link.getTag().getText().equals(value)){
//                    return(link.getTraceID1()==jkID?link.getTraceID2():link.getTraceID1());
//                }
//            }
//       return -1;
//    }

    public void calReturnValue(){
        JumpTrace trace=jumpTree.get(jumpID);
        if(value.charAt(0)!='('){
            if(trace.bindTable.containsKey(value)){
                value=trace.bindTable.get(value);
            }

        }
        else{
            String tmp=value.substring(1,value.length()-1);
            String[] val=tmp.split(",");
            if(trace.bindTable.containsKey(val[0])){
                val[0]=trace.bindTable.get(val[0]);
            }
            if(trace.bindTable.containsKey(val[1])){
                val[1]=trace.bindTable.get(val[1]);
            }
            value="("+val[0]+","+val[1]+")";
        }
    }

    public void patternMatch(Link link){

        JKTrace trace=JKTree.get(jkID);
        Link oldLink;
        String[] val=stringHandle();
        if(existBranch(0)){
            value=val[0];
            oldLink=findNextLink(0);
            addTagAndBindings(oldLink,link);
            value=val[1];
            setBinding(link);
        }
        else{
            oldLink=findNextLink(0);
            addTagAndBindings(oldLink,link);
            setBinding(link);
        }
        int newJkID=findNextJkTrace(oldLink);
        JKTree.get(newJkID).setFatherid(jkID);
        jkID=newJkID;
    }

    public String[] stringHandle(){
        String[] str=new String[2];
        String val=value.substring(1,value.length()-1);
        int stIndex=0;
        if(val.charAt(stIndex)!='('){
            for(int i=1;i<val.length();i++){
                if(val.charAt(i)==','){
                    str[0]=val.substring(0,i);
                    str[1]=val.substring(i+1);
                    return str;
                }
            }
        }
        else{
            int cnt=1;
            for(int i=1;i<val.length();i++){
                if(val.charAt(i)=='('){
                    cnt++;
                }
                else if(val.charAt(i)==')'){
                    cnt--;
                    if(cnt==0){
                        str[0]=val.substring(0,i+1);
                        str[1]=val.substring(i+2);
                        return str;
                    }
                }
            }
        }
        return str;
    }

    private void findBranch() {
       value=value.substring(1,value.length()-1);
       String tag="";
       for(int i=0;i<value.length();i++){
           if(value.charAt(i)==','){
               tag=value.substring(0,i);
               value=value.substring(i+1);
               break;
           }
       }
       Point p=JKTree.get(jkID).linkpoints.get(0);
       int nextJkID=0;
       for(Link link:p.links){
           if(tag.equals(link.getTag().getText())){
               nextJkID=(link.getTraceID1()==jkID?link.getTraceID2():link.getTraceID1());
               break;
           }
       }
       JKTree.get(nextJkID).setFatherid(jkID);
       jkID=nextJkID;
    }






    public void showCycle(String str){
        showStep.setText("Cycle "+cycle+": "+str);
    }

    public void addTrace() {
        Label label=new Label(JKTree.get(jkID).getText());
        JumpTrace trace=new JumpTrace(JKTree.get(jkID).getType(),JKTree.get(jkID).getText());
        jumpTree.add(trace);
        oldJumpID=jumpID;
        jumpID=jumpTree.size()-1;
        label.setLayoutX(point.getX() + 15);
        label.setLayoutY(point.getY() - 5);
        trace.setId(jumpID);
        trace.setLinkpoints(point);
        trace.setLabel(label);
        if(JKTree.get(jkID).getType().equals("Pgon")){
           trace.addPgon();
        }
        else if(JKTree.get(jkID).getType().equals("Hgon")){
           trace.setShape(drawHgon());
        }

        Shape shape=trace.getShape();
        shape.setFill(Color.WHITE);
        shape.setStroke(Color.RED);
        JKTree.get(jkID).getShape().setStroke(Color.RED);
        shape.setId(Integer.toString(jumpID));
        trace.setShape(shape);
        trace.setTeacherID(jkID);
        addDragFunction(shape,label);
        runRoot.getChildren().add(shape);
        runRoot.getChildren().add(label);

        JKTree.get(jkID).getShape().setStroke(Color.RED);
        jumpTree.get(jumpID).getShape().setStroke(Color.RED);
        JKTree.get(jkID).setStuID(jumpID);
        if(trace.getId()!=startID){
            trace.setFatherid(oldJumpID);
            trace.updateBindTable(jumpTree.get(oldJumpID));
            trace.updateLink(jumpTree.get(oldJumpID));
        }

        shape.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                int id=Integer.parseInt(shape.getId());
                if(pressedID!=-1){
                    jumpTree.get(pressedID).getShape().setFill(Color.WHITE);
                    //jumpTree.get(pressedID).getShape().setStroke(Color.BLACK);
                    jumpTree.get(pressedID).getLabel().setTextFill(Color.BLACK);
                    int pressedJKID=jumpTree.get(pressedID).getTeacherID();
                    JKTree.get(pressedJKID).getShape().setFill(Color.WHITE);
                    //JKTree.get(pressedJKID).getShape().setStroke(Color.BLACK);
                    JKTree.get(pressedJKID).getLabel().setTextFill(Color.BLACK);
                }
                if(id==pressedID){
                    pressedID=-1;
                    ObservableList<String> bindList = FXCollections.observableArrayList();
                    bindView.setItems(bindList);
                }
                else {
                    pressedID=id;
                    jumpTree.get(pressedID).getShape().setFill(Color.BLACK);
                    //jumpTree.get(pressedID).getShape().setStroke(Color.BLACK);
                    jumpTree.get(pressedID).getLabel().setTextFill(Color.WHITE);
                    int pressedJKID=jumpTree.get(pressedID).getTeacherID();
                    JKTree.get(pressedJKID).getShape().setFill(Color.BLACK);
                    //JKTree.get(pressedJKID).getShape().setStroke(Color.BLACK);
                    JKTree.get(pressedJKID).getLabel().setTextFill(Color.WHITE);
                    ObservableList<String> bindList = FXCollections.observableArrayList();
                    for (String key : jumpTree.get(id).bindTable.keySet()) {
                        bindList.add(key + "->" + jumpTree.get(id).bindTable.get(key));
                    }
                    bindView.setItems(bindList);
                }
            }
        });

        //runRoot.getChildren().add(shape);
        //runRoot.getChildren().add(label);

    }

//    public Shape drawPgon(){
//        double x=point.getX();
//        double y=point.getY();
//        Polygon p=new Polygon();
//        p.getPoints().addAll(new Double[]{
//                x, y,
//                x + 10, y - 10,
//                x + 100, y - 10,
//                x + 100, y + 10,
//                x + 10, y + 10
//        });
//        p.setFill(Color.WHITE);
//        p.setStroke(Color.RED);
//        JKTree.get(jkID).getShape().setStroke(Color.RED);
//        return p;
//    }

    public Link buildLink(Point a,Point b){
        Line line=new Line(a.getX(),a.getY(),b.getX(),b.getY());
        Link link=new Link(line,a,b);
        link.setSettled(false);
        line.setStroke(Color.BLACK);
        runRoot.getChildren().add(line);
        a.links.add(link);
        point.setX(b.getX());
        point.setY(b.getY());
        return link;
    }

    public void addTagAndBindings(Link oldLink,Link newLink){
        double x=(newLink.getP1().getX()+newLink.getP2().getX())*0.5;
        double y=(newLink.getP1().getY()+newLink.getP2().getY())*0.5;
        if(!oldLink.getTag().getText().isEmpty()){
            newLink.setTag(new Label(oldLink.getTag().getText()));
            newLink.getTag().setLayoutX(x);
            newLink.getTag().setLayoutY(y);
            runRoot.getChildren().add(newLink.getTag());
        }
        for(Binding binding:oldLink.bindings){
            Ellipse e=new Ellipse(0,0,50,10);
            e.setFill(Color.WHITE);
            e.setStroke(Color.BLACK);
            Label l=new Label(binding.getBindKey().getText());

            Binding newBinding=new Binding(e,l);
            newLink.bindings.add(newBinding);
            runRoot.getChildren().add(e);
            runRoot.getChildren().add(l);
        }
        JKTree.get(jkID).getShape().setStroke(Color.BLACK);
        jumpTree.get(jumpID).getShape().setStroke(Color.BLACK);
        newLink.getLine().setStroke(Color.RED);
        oldLink.getLine().setStroke(Color.RED);
        newLink.setCorrespondJKLink(oldLink);
        newLink.locateBindings();
    }

    public Shape drawHgon(){
        double x=point.getX();
        double y=point.getY();
        Polygon p=new Polygon();
        p.getPoints().addAll(new Double[]{
                x, y,
                x + 10, y - 10,
                x + 70, y - 10,
                x+80,y,
                x + 70, y + 10,
                x + 10, y + 10
        });
        return p;
    }

    public Shape drawEllipse(){
        double x=point.getX();
        double y=point.getY();
        Ellipse e=new Ellipse(x,y+10,50,10);
        e.setFill(Color.WHITE);
        e.setStroke(Color.BLACK);
        return e;
    }


    public void codeExecute(MouseEvent event){
        switch (step){
            case 0:{
                fetch();
                break;
            }
            case 1:{
                decode();
                break;
            }
            default:{
                execute();
            }
        }

    }

//    public void buildJumpTree(){
//        if(jkID==startID){
//            addTrace();
//
//        }
//
//        while(true){
//
//
//            nextjkID=findNextJkTrace();
//        }
//    }

//

    public void setBinding(Link jumpLink){
        if(jumpLink.bindings.size()==0) return;
        if(jumpLink.bindings.size()==1){
            JumpTrace trace=jumpTree.get(jumpID);
            if(trace.bindTable.containsKey(value)){
                value=trace.bindTable.get(value);
            }
            String key=jumpLink.bindings.get(0).getBindKey().getText();
            jumpLink.bindings.get(0).getBindKey().setText(key+"->"+value);
            jumpLink.bindings.get(0).setEllipseRX();
            trace.bindTable.put(key,value);
        }
        else{
            String[] val=stringHandle();
            String key1=jumpLink.bindings.get(0).getBindKey().getText();
            String key2=jumpLink.bindings.get(1).getBindKey().getText();
            jumpLink.bindings.get(0).getBindKey().setText(key1+"->"+val[0]);
            jumpLink.bindings.get(1).getBindKey().setText(key2+"->"+val[1]);
            jumpLink.bindings.get(0).setEllipseRX();
            jumpLink.bindings.get(1).setEllipseRX();
            jumpTree.get(jumpID).bindTable.put(key1,val[0]);
            jumpTree.get(jumpID).bindTable.put(key2,val[1]);
        }
    }

    public Link findNextLink(int pointId){
        Trace trace=JKTree.get(jkID);
        if(!existBranch(pointId)) {
            if (jkID == startID) {
                Link link = trace.linkpoints.get(pointId).links.get(0);
                return link;
            } else {
                for (Link link : trace.linkpoints.get(pointId).links) {
                    if ((link.getTraceID1() == trace.getId() && link.getTraceID2() != trace.getFatherid()) || (link.getTraceID2() == trace.getId() && link.getTraceID1() != trace.getFatherid())) {
                        return link;
                    }
                }
            }
        }
        else{
            for(Link link:trace.linkpoints.get(pointId).links){
                if(link.getTag().getText().equals(value)){
                    return link;
                }
            }
        }
        Link link=trace.linkpoints.get(pointId).links.get(0);
        return link;
    }

    public boolean existBranch(int pid){
        JKTrace trace=JKTree.get(jkID);
        if(jkID==startID){
            if(trace.linkpoints.get(pid).links.size()<=1) return false;
            else return true;
        }
        else{
            if(trace.linkpoints.get(pid).links.size()<=2) return false;
            else return true;
        }
    }

    public int findNextJkTrace(Link link){
                  return link.getTraceID1()==jkID?link.getTraceID2():link.getTraceID1();
    }



    private void updateStackLabel() {
        String str="stack:  ";
        for(int i=stack.size()-1;i>=0;i--){
            str+=stack.get(i);
            str+="::";
        }
        str+="nil";
        showStack.setText(str);
    }

//    private void binding(){
//        addTrace();
//        String key=jumpTree.get(jumpID).getText();
//        if(jumpTree.get(jumpID).bindTable.containsKey(value)){
//            value=jumpTree.get(jumpID).bindTable.get(value);
//        }
//        else if(value.substring(0,2).equals("pt")){
//            jumpTree.get(jumpID).bindTable.put(key,value);
//        }
//        else{
//            jumpTree.get(jumpID).bindTable.put(key,value);
//        }
//        jumpTree.get(jumpID).setText(key+"->"+value);
//        jumpTree.get(jumpID).getLabel().setText(jumpTree.get(jumpID).getText());
//        Point startPoint=jumpTree.get(jumpID).linkpoints.get(1);
//        Point endPoint=new Point(startPoint.getX(),startPoint.getY()+50);
//        buildLink(startPoint,endPoint);
//        int newJkID=findNextJkTrace(1);
//        JKTree.get(newJkID).setFatherid(jkID);
//        jkID=newJkID;
//
//    }

//    public void addDragFunction(Shape shape, Label l){
//        final ObjectProperty<Point2D> mousePosition = new SimpleObjectProperty<>();
//
//        shape.setOnMousePressed(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//                mousePosition.set(new Point2D(event.getSceneX(), event.getSceneY()));
//            }
//        });
//
//        shape.setOnMouseDragged(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//                double deltaX = event.getSceneX() - mousePosition.get().getX();
//                double deltaY = event.getSceneY() - mousePosition.get().getY();
//                shape.setLayoutX(shape.getLayoutX() + deltaX);
//                shape.setLayoutY(shape.getLayoutY() + deltaY);
//                l.setLayoutX(l.getLayoutX() + deltaX);
//                l.setLayoutY(l.getLayoutY() + deltaY);
//                int id=Integer.parseInt(shape.getId());
//                for(Point p:jumpTree.get(id).linkpoints){
//                    for(Link link:p.links){
//                        if(link.getTraceID1()==id){
//                            link.getLine().setStartX(link.getLine().getStartX()+deltaX);
//                            link.getLine().setStartY(link.getLine().getStartY()+deltaY);
//                        }
//                        else{
//                            link.getLine().setEndX(link.getLine().getEndX()+deltaX);
//                            link.getLine().setEndY(link.getLine().getEndY()+deltaY);
//
//                        }
//                    }
//                    p.setX(p.getX()+deltaX);
//                    p.setY(p.getY()+deltaY);
//                }
//                mousePosition.set(new Point2D(event.getSceneX(), event.getSceneY()));
//            }
//        });
//    }

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

                moveShape(Integer.parseInt(shape.getId()),deltaX,deltaY);
                mousePosition.set(new Point2D(event.getSceneX(), event.getSceneY()));
            }
        });
    }

    public void moveShape(int id,double x,double y){
        Shape shape=jumpTree.get(id).getShape();
        shape.setLayoutX(shape.getLayoutX()+x);
        shape.setLayoutY(shape.getLayoutY()+y);
        Label label=jumpTree.get(id).getLabel();
        label.setLayoutX(label.getLayoutX()+x);
        label.setLayoutY(label.getLayoutY()+y);
        Label il=jumpTree.get(id).getIdLabel();
        Shape es=jumpTree.get(id).getExecuteLabel();
        il.setLayoutX(il.getLayoutX()+x);
        il.setLayoutY(il.getLayoutY()+y);
        es.setLayoutX(es.getLayoutX()+x);
        es.setLayoutY(es.getLayoutY()+y);
        for(Point p:jumpTree.get(id).linkpoints){
                    for(Link link:p.links){
                        if(link.getTraceID1()==id){
                            link.getLine().setStartX(link.getLine().getStartX()+x);
                            link.getLine().setStartY(link.getLine().getStartY()+y);
                            if(!link.getLine().getStroke().equals(Color.RED))
                                moveShape(link.getTraceID2(), x, y);
                            else {
                                link.getLine().setEndX(link.getLine().getEndX() + x);
                                link.getLine().setEndY(link.getLine().getEndY() + y);
                                link.getP2().setX(link.getP2().getX()+x);
                                link.getP2().setY(link.getP2().getY()+y);
                            }
                        }
                        else{

                            link.getLine().setEndX(link.getLine().getEndX()+x);
                            link.getLine().setEndY(link.getLine().getEndY()+y);

                        }
                        link.locateBindings();
                    }
                    p.setX(p.getX()+x);
                    p.setY(p.getY()+y);
                }
       if(id==jumpID){
           point.setX(point.getX()+x);
           point.setY(point.getY()+y);
       }
    }
}

