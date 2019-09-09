public class CodeBlock extends Block {

    private int stuID;

    public CodeBlock(String type, String text){

        super(type,text);
    }

    public int getStuID() {
        return stuID;
    }

    public void setStuID(int stuID) {
        this.stuID = stuID;
    }
}
