package GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static javafx.application.Application.launch;

public class main extends Application {
@Override
public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("GUI.fxml"));
        primaryStage.setTitle("Jumping machine builder");
        Scene scene =new Scene(root, 800, 400);
        //primaryStage.initStyle(StageStyle.TRANSPARENT);
        //primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
        }




public static void main(String[] args) {
        launch(args);
        }
}
