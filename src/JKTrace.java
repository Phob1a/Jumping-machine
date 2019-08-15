public class JKTrace extends Trace {

    private int stuID;

    public JKTrace(String type,String text){

        super(type,text);
    }

    public int getStuID() {
        return stuID;
    }

    public void setStuID(int stuID) {
        this.stuID = stuID;
    }
}
