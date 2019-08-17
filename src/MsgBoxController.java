import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class MsgBoxController {


    @FXML
    private Text context=new Text();
    @FXML
    private AnchorPane root;

    public void setContext(String context){
        this.context.setText(context);
    }

    public static void display(String content){
        try {
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            FXMLLoader loader = new FXMLLoader(MsgBoxController.class.getResource("MsgBox.fxml"));
            Parent msgRoot = loader.load();
            MsgBoxController controller = loader.getController();
            //controller.setTitle(title);
            controller.setContext(content);
            Scene scene = new Scene(msgRoot);
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
