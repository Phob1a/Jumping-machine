import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class JumpingMachine{
   HashMap<String,String>binding;
   ArrayList<Trace> tree;
   //Stack<String>st;

    public JumpingMachine() {
        this.binding = new HashMap<>();
        this.tree=new ArrayList<>();
    }

    public void addPrint(String text, double x, double y){
       Trace printUni=new Trace("P","P",50,50,text);
       tree.add(printUni);
   }

   public void draw(){

   }

    public static void main(String[] args) {
       JumpingMachine jm=new JumpingMachine();
       jm.addPrint("hello",50,50);
       jm.addPrint("bye",50,100);

    }

}
