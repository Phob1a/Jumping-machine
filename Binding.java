import javafx.scene.control.Label;
import javafx.scene.shape.Ellipse;

public class Binding {


    private Ellipse ellipse;
    private Label bindKey;

    public Binding(Ellipse ellipse,Label label){
        this.ellipse=ellipse;
        this.bindKey=label;
    }

    public Ellipse getEllipse() {
        return ellipse;
    }

    public void setEllipse(Ellipse ellipse) {
        this.ellipse = ellipse;
    }

    public Label getBindKey() {
        return bindKey;
    }

    public void setBindKey(Label bindKey) {
        this.bindKey = bindKey;
    }
}
