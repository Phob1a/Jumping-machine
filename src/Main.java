import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("GUI.fxml"));
        primaryStage.setTitle("Jumping machine builder");
        Scene scene =new Scene(root, 750, 1000);
        //primaryStage.initStyle(StageStyle.TRANSPARENT);
        //primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.setX(0);
        primaryStage.setY(0);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

}
