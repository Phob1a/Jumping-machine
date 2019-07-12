import GUI.Point;

/**
 * thunk
 * force
 * return
 * let be
 * to
 *
 */


public class Trace {

    private String type;
    private String shape;
    private String text;
    private Point linkPoint;


    /**
     * For pentagon: (x,y)-->(x+10,y-10)-->(x+110,y-10)-->(x+110,y+10)-->(x+10,y+10)
     * For hexagon:  (x,y)-->(x+10,y-10)-->(x+110,y-10)-->(x+120,y)-->(x+110,y+10)-->(x+10,y+10)
     * For           (x,y)-->(x+50,y,50,20)
     */
    public Trace(String type, String shape,double x,double y,String text) {
        this.type = type;
        this.shape = shape;
        this.text=text;
        this.linkPoint=new Point(x,y);
    }
}
