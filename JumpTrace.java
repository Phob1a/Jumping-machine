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
            this.linkpoints.add(new Point(x+55,y,this.getId()));
        }
        else if(this.getType().equals("Hgon")){
            this.linkpoints.add(new Point(x,y,this.getId()));
            this.linkpoints.add(new Point(x+110,y,this.getId()));
        }
        else{
            this.linkpoints.add(new Point(x,y,this.getId()));
            this.linkpoints.add(new Point(x,y+20,this.getId()));
        }

    }


    public void updateLink(JumpTrace trace) {


        for(Point p:trace.linkpoints){
            for(Link link:p.links){
                if(!link.isSettled()){
                    link.setP2(this.linkpoints.get(0));
                    link.setTraceID2(this.getId());
                    link.setSettled(true);
                    this.linkpoints.get(0).links.add(link);
                    return;
                }
            }
        }
    }

}