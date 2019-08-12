import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

import java.util.HashMap;

public class JumpTrace extends Trace {

    private int teacherID;
    Polygon executeLabel;
    HashMap<String,String>bindTable;

    public Shape getExecuteLabel() {
        return executeLabel;
    }

    public void setExecuteLabel(Polygon executeLabel) {
        this.executeLabel = executeLabel;
    }

    public JumpTrace(String type, String text) {
        super(type, text);
        this.bindTable=new HashMap<>();
        this.executeLabel=new Polygon();
    }

    public void updateBindTable(JumpTrace t){
        for(String key: t.bindTable.keySet()){
            String val=t.bindTable.get(key);
            this.bindTable.put(key,val);
        }
    }


    public int getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(int teacherID) {
        this.teacherID = teacherID;
    }

    public void setLinkpoints(Point point){
        double x=point.getX();
        double y=point.getY();
        if(this.getType().equals("Pgon")){
            this.linkpoints.add(new Point(x,y,this.getId()));
            //this.linkpoints.add(new Point(x+55,y,this.getId()));
        }
        else if(this.getType().equals("Hgon")){
            this.linkpoints.add(new Point(x,y,this.getId()));
            this.linkpoints.add(new Point(x+80,y,this.getId()));
        }

    }


    public void updateLink(JumpTrace trace) {


        for(Point p:trace.linkpoints){
            for(Link link:p.links){
                if(link.getLine().getStroke().equals(Color.RED)){
                    link.setP2(this.linkpoints.get(0));
                    link.setTraceID2(this.getId());
                    link.getLine().setStroke(Color.BLACK);
                    link.getCorrespondJKLink().getLine().setStroke(Color.BLACK);
                    //link.setSettled(true);
                    this.linkpoints.get(0).links.add(link);
                    return;
                }
            }
        }
    }

}